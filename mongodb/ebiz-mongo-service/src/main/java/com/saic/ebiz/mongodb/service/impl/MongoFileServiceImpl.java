/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: MongoFileServiceImpl.java
 * Author:   v_hudong
 * Date:     2014年7月9日 下午12:57:24
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.service.impl;

import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.meidusa.toolkit.common.util.Base64;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.saic.ebiz.mongodb.collection.BatchDocument;
import com.saic.ebiz.mongodb.collection.LockDocument;
import com.saic.ebiz.mongodb.service.api.IMongoFileService;
import com.saic.ebiz.mongodb.utils.MD5Util;
import com.saic.ebiz.mongodb.utils.MongoConstants;
import com.saic.ebiz.mongodb.utils.MongoEbizException;
import com.saic.ebiz.mongodb.vo.ftl.MongoFileStream;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MongoFileServiceImpl implements IMongoFileService {
    /** 日志记录对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoFileServiceImpl.class);
    /** mongo数据操作template */
    @Autowired
    private MongoTemplate mongoTemplate;
    /** 锁对象 */
    private static final Object SYSOBJECT = new Object();

    /*
     * (non-Javadoc)
     * @see com.ebiz.mongodb.service.api.IMongoFileService#saveFile(com.ebiz.mongodb.vo.ftl.MongoFileStream)
     */
    @Override
    public boolean saveFile(MongoFileStream fileMetadata) throws MongoEbizException {
        // 文件写入锁定
        if (this.isNotLockedForFTL(fileMetadata)) {
            try {
                LOGGER.debug("*** saveFile start *** param = fileMetadata:{} ", fileMetadata.toString());
                GridFS gridFs = locateGridFsByFileEntity(MongoConstants.COLLECTION_FILETYPE_MAP.get(fileMetadata
                        .getFileType()));

                String md5ForBase64Str = MD5Util.getMD5ForBase64Str(fileMetadata.getBase64String());
                DBObject query = new BasicDBObject("contentType", fileMetadata.getBusinessNo());
                GridFSDBFile gridFSDBFile = gridFs.findOne(query);
                if (gridFSDBFile != null) {
                    if (gridFSDBFile.getId().equals(md5ForBase64Str)) {
                        return true;
                    } else {
                        gridFs.remove(query);
                    }
                }
                GridFSInputFile gridFSInputFile = gridFs.createFile(Base64.decode(new String(fileMetadata
                        .getBase64String().getBytes("UTF-8"), "UTF-8")));
                gridFSInputFile.setFilename(fileMetadata.getFileName());
                gridFSInputFile.setContentType(fileMetadata.getBusinessNo());
                gridFSInputFile.setId(md5ForBase64Str);
                gridFSInputFile.save();
                saveBatchDocument(fileMetadata.getBusinessNo());// 保存批次信息
                LOGGER.debug("*** saveFile end *** param = fileMetadata:{} ", fileMetadata.toString());
                return true;
            } catch (Exception e) {
                throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
            } finally {
                this.releaseLock(fileMetadata);
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.ebiz.mongodb.service.api.IMongoFileService#fetchFTLByBusinessNo(java.lang.String)
     */
    @Override
    public String fetchFTLByBusinessNo(String businessNo) throws MongoEbizException {
        try {
            LOGGER.debug("*** fetchFTLByBusinessNo start *** param = businessNo:{} ", businessNo);
            String content = "";
            GridFS gridFs = locateGridFsByFileEntity(MongoConstants.COLLECTION_FILETYPE_MAP
                    .get(MongoConstants.FILE_TYPE_FTL));
            DBObject query = new BasicDBObject("contentType", businessNo);
            GridFSDBFile gridFSDBFile = gridFs.findOne(query);
            if (gridFSDBFile != null) {
                content = IOUtils.toString(gridFSDBFile.getInputStream(), Charset.forName("UTF-8"));
                return Base64.encode(content.getBytes("UTF-8"));
            }
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.ebiz.mongodb.service.api.IMongoFileService#removeFTLMetaByBusinessNo(java.lang.String)
     */
    @Override
    public boolean removeFTLMetaByBusinessNo(String businessNo) throws MongoEbizException {
        try {
            LOGGER.debug("*** removeFTLMetaByBusinessNo start *** param = businessNo:{} ", businessNo);
            GridFS gridFs = locateGridFsByFileEntity(MongoConstants.COLLECTION_FILETYPE_MAP
                    .get(MongoConstants.FILE_TYPE_FTL));
            DBObject query = new BasicDBObject("contentType", businessNo);
            gridFs.remove(query);
            LOGGER.debug("*** removeFTLMetaByBusinessNo start *** param = businessNo:{} ", businessNo);
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
        return true;
    }

    private GridFS locateGridFsByFileEntity(String collectionName) throws MongoEbizException {
        return new GridFS(mongoTemplate.getDb(), collectionName);
    }

    /**
     * 
     * 功能描述: <br>
     * 保存批次信息
     * 
     * @param batchDocument
     * @return
     * @throws MongoEbizException
     */
    private boolean saveBatchDocument(String fileId) throws MongoEbizException {
        if (fileId == null) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, new MongoEbizException());
        }
        Query query = new Query(Criteria.where(MongoConstants.BATCH_OBJECT).is(fileId)
                .and(MongoConstants.BATCH_OBJECTTYPE).is(MongoConstants.ObjectType.FTLID.toString())
                .and(MongoConstants.STATUS).is(MongoConstants.BatchStatus.INITIAL.ordinal()));
        BatchDocument batchDocuments = mongoTemplate.findOne(query, BatchDocument.class);
        if (batchDocuments != null) {
            return true;
        }
        try {
            BatchDocument batchDocument = new BatchDocument();
            batchDocument.setBatchType(MongoConstants.BatchType.HTML.toString());
            batchDocument.setBusinessType(MongoConstants.BusinessType.FTLHTML.toString());
            batchDocument.setStatus(MongoConstants.BatchStatus.INITIAL.ordinal());
            batchDocument.setCreateTime(new Date());
            batchDocument.setUpdateTime(new Date());
            batchDocument.setObjectType(MongoConstants.ObjectType.FTLID.toString());
            batchDocument.setObject(fileId);
            mongoTemplate.save(batchDocument);
            return true;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 判断ftl文件是否已经存在锁库信息
     * 
     * @param businessNo
     * @return
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private boolean isNotLockedForFTL(MongoFileStream fileMetadata) {
        LOGGER.debug("*** isNotLockedForFTL  *** param = fileMetadata:{} ", fileMetadata.toString());
        synchronized (SYSOBJECT) {
            Query query = new Query(Criteria.where(LockDocument.FIELD_BUSINESSNO).is(fileMetadata.getBusinessNo()));
            LockDocument one = mongoTemplate.findOne(query, LockDocument.class);
            if (one != null) {
                return false;
            } else {
                one = new LockDocument();
                one.setBusinessNo(fileMetadata.getBusinessNo());
                one.setFileName(fileMetadata.getFileName());
                one.setFileType(fileMetadata.getFileType());
                mongoTemplate.save(one);
            }
            return true;
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 释放锁定信息
     * 
     * @param fileMetadata
     * @return
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private boolean releaseLock(MongoFileStream fileMetadata) {
        LOGGER.debug("*** releaseLock  *** param = fileMetadata:{} ", fileMetadata.toString());
        synchronized (SYSOBJECT) {
            try {
                Query query = new Query(Criteria.where(LockDocument.FIELD_BUSINESSNO).is(fileMetadata.getBusinessNo()));
                mongoTemplate.remove(query, LockDocument.class);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

}