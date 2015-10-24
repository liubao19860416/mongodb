/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: MongoHtmlService.java
 * Author:   v_hudong
 * Date:     2014年7月14日 上午11:25:42
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.ibm.framework.web.freemarker.MultiDomUrl;
import com.meidusa.fastjson.JSONObject;
import com.meidusa.toolkit.common.util.StringUtil;
import com.meidusa.venus.annotations.Param;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.saic.ebiz.mediastorage.exception.MediaStorageFailException;
import com.saic.ebiz.mediastorage.service.MediaStorageService;
import com.saic.ebiz.mongodb.basebean.MdseExtAttribute;
import com.saic.ebiz.mongodb.basebean.MdseImage;
import com.saic.ebiz.mongodb.basebean.MdseSkuChnPrice;
import com.saic.ebiz.mongodb.collection.MdseDocument;
import com.saic.ebiz.mongodb.collection.SnapshotDocument;
import com.saic.ebiz.mongodb.common.StringTemplateLoader;
import com.saic.ebiz.mongodb.service.api.IMongoHtmlService;
import com.saic.ebiz.mongodb.service.api.IMongoMdseService;
import com.saic.ebiz.mongodb.utils.MongoConstants;
import com.saic.ebiz.mongodb.utils.MongoEbizException;
import com.saic.ebiz.mongodb.vo.snapshot.SnapShotVO;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 〈一句话功能简述〉<br>
 * 〈静态页面生成服务〉
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MongoHtmlServiceImpl implements IMongoHtmlService {
    /** 日志记录对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoHtmlServiceImpl.class);
    /** mongo数据操作template */
    @Autowired
    private MongoTemplate mongoTemplate;

    /** 流媒体存储service */
    @Autowired
    private MediaStorageService mediaStorageService;
    /** 商品信息存储service */
    @Autowired
    private IMongoMdseService mongoMdseService;

    @Autowired
    private MultiDomUrl multiDomUrl;

    /*
     * (non-Javadoc)
     * @see com.saic.ebiz.mongodb.service.api.IHtmlService#buildHtml(java.lang.String , java.lang.String,
     * java.lang.String, java.lang.Object)
     */
    @Override
    public String buildHtml(String ftlBase64String, Object data) throws MongoEbizException {
        String fileName = "";
        StringWriter out = new StringWriter();
        try {
            // 初使化FreeMarker配置
            Configuration config = new Configuration();
            // 设置要解析的模板所在的目录，并加载模板文件

            config.setTemplateLoader(new StringTemplateLoader(ftlBase64String));
            // 设置包装器，并将对象包装为数据模型
            config.setObjectWrapper(new DefaultObjectWrapper());

            // 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
            // 否则会出现乱码
            Template template = config.getTemplate("");
            template.setEncoding("UTF-8");
            template.process(data, out);
            ByteArrayInputStream in = new ByteArrayInputStream(out.toString().getBytes("UTF-8"));
            fileName = mediaStorageService.storageFile(in);
            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        } catch (TemplateException e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        } catch (MediaStorageFailException e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("模板读取流关闭失败");
                }
            }
        }
        return fileName;
    }

    /*
     * (non-Javadoc)
     * @see com.saic.ebiz.mongodb.service.api.IHtmlService#refreshMdseHtmlByTemplateNo (java.lang.String)
     */
    @Override
    public boolean refreshMdseHtmlByTemplateNo(String templateNo) throws MongoEbizException {
        try {
            String ftlBase64String = loadFtlContentByTemplateNo(templateNo);
            List<MdseDocument> mdseDocuments = mongoMdseService.getMdseDocumentByTemplateNoForHTML(templateNo);
            buildCommonMdseHtml(mdseDocuments, ftlBase64String);
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.saic.ebiz.mongodb.service.api.IHtmlService#buildHtmlByMdseBatchId (java.lang.Long)
     */
    @Override
    public boolean buildHtmlByMdseBatchId(Long batchId) throws MongoEbizException {
        try {
            List<MdseDocument> mdseDocuments = mongoMdseService.getMdseDocumentBybatchIdForHTML(batchId);
            if (mdseDocuments != null && mdseDocuments.size() > 0) {
                String ftlBase64String = loadFtlContentByTemplateNo(mdseDocuments.get(0).getDisplayTpltNo());
                buildCommonMdseHtml(mdseDocuments, ftlBase64String);
            }
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
        return false;
    }

    public boolean buildHtmlByMdseId(@Param(name = "mdseId") Long mdseId) throws MongoEbizException {
        try {
            List<MdseDocument> mdseDocuments = mongoMdseService.getMdseDocumentByMdseIdForHTML(mdseId);
            if (mdseDocuments != null && mdseDocuments.size() > 0) {
                String ftlBase64String = loadFtlContentByTemplateNo(mdseDocuments.get(0).getDisplayTpltNo());
                buildCommonMdseHtml(mdseDocuments, ftlBase64String);
            }
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
        return false;
    }

    private GridFS locateGridFsByFileEntity(String collectionName) throws MongoEbizException {
        return new GridFS(mongoTemplate.getDb(), collectionName);
    }

    /**
     * 
     * 
     * 功能描述: <br>
     * 根据摸版编码获取摸班文件内容
     * 
     * @param templateNo
     * @return String
     * @throws MongoEbizException
     */
    private String loadFtlContentByTemplateNo(String templateNo) throws MongoEbizException {
        String content = "";
        try {
            GridFS gridFs = locateGridFsByFileEntity(MongoConstants.COLLECTION_FILETYPE_MAP
                    .get(MongoConstants.FILE_TYPE_FTL));
            DBObject query = new BasicDBObject("contentType", templateNo);
            GridFSDBFile gridFSDBFile = gridFs.findOne(query);
            content = IOUtils.toString(gridFSDBFile.getInputStream(), "UTF-8");
        } catch (Exception e) {
            LOGGER.error("*** load ftl file failed templateNo:{}", templateNo);
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
        return content;
    }

    private String readFile(String path) {
        LOGGER.info("MdseController.readFile()读取富文本start");
        String result = "";
        if (!StringUtils.isEmpty(path)) {
            try {
                byte[] fileByte = mediaStorageService.readFile(path);
                result = new String(fileByte, "UTF-8");
            } catch (MediaStorageFailException e) {
                LOGGER.debug("富文本读取异常:", e);
                result = "<span style='color:red'>读取富文本内容异常,请稍候重试</span>";
            } catch (UnsupportedEncodingException e) {
                LOGGER.debug("富文本编码异常:", e);
            }
        }
        return result;

    }

    /**
     * 
     * 
     * 功能描述: <br>
     * 根据商品信息生成html文件
     * 
     * @param mdseDocuments ,ftlBase64String
     * @return
     * @throws MongoEbizException
     */
    private void buildCommonMdseHtml(List<MdseDocument> mdseDocuments, String ftlBase64String) throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        int failNum = 0;
        for (MdseDocument mdseDocument : mdseDocuments) {
            
            List<MdseExtAttribute> mdseExtAttributeList = mdseDocument.getAttributes();
            if(mdseExtAttributeList!=null){
                for(java.util.Iterator<MdseExtAttribute> iter = mdseExtAttributeList.iterator();iter.hasNext();){
                    MdseExtAttribute mdseExtAttribute = iter.next();
                    if(StringUtil.isEmpty(mdseExtAttribute.getDisplayName())){
                        iter.remove();//删除自定义属性名为空的
                    }
                }
            }

            data.put("mdseDetail", mdseDocument);
            mdseDocument.setRichDetail(readFile(mdseDocument.getRichDetail()));
            List<MdseImage> images = mdseDocument.getImages();
            if (images != null) {
                for (MdseImage image : images) {
                    List<String> fullImages = new ArrayList<String>();
                    fullImages.add(image.getSourceFullImage());
                    String fullImage = (String) multiDomUrl.exec(fullImages);// 动态生成图片所在的服务器及绝对地址
                    image.setSourceFullImage(fullImage);
                }
            }
            List<MdseSkuChnPrice> skuPrices = mdseDocument.getSkuPrices();
            if (skuPrices != null && skuPrices.size() > 0) {
                for (MdseSkuChnPrice mdseSkuChnPrice : skuPrices) {
                    try {
                        List<MdseSkuChnPrice> tempMdseSkuChnPriceList = new ArrayList<MdseSkuChnPrice>();
                        tempMdseSkuChnPriceList.add(mdseSkuChnPrice);
                        mdseDocument.setSkuPrices(tempMdseSkuChnPriceList);
                        data.put("mdseDetailJson", JSONObject.toJSONString(mdseDocument));
                        String snapshotId = buildHtml(ftlBase64String, data);// filename组成为(渠道_商品id_商品版本_批次id_规格价格id)//按规格价格分html,生成该html只能包含一个渠道价格
                        // 快照信息保存
                        this.mergeSnapShotDocument(mdseDocument.getMdseId(), mdseSkuChnPrice.getSkuId(),
                                mdseSkuChnPrice.getSkuChnId(), mdseDocument.getBatchId(), mdseDocument.getVersion(),
                                snapshotId);
                    } catch (Exception e) {
                        failNum += 1;
                        LOGGER.error("该商品生成快照失败-- mdseId:{}  + channelType:{} +SkuChnId:{}", mdseDocument.getMdseId(),
                                mdseDocument.getChannelType(), mdseSkuChnPrice.getSkuChnId());
                    }
                }
            }
        }
        if (failNum > 0){
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, "生成快照失败");
        }
    }

    @Override
    public List<SnapShotVO> getSnapShotList(List<SnapShotVO> snapShotList) throws MongoEbizException {
        try {
            LOGGER.debug("*** getSnapShotList *** param = snapshotList:{}", snapShotList);
            if (snapShotList == null || snapShotList.size() < 1) {
                throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, "查询参数为空！");
            }
            for (SnapShotVO snapShotVO : snapShotList) {
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and(MongoConstants.SKUCHN_ID).is(snapShotVO.getSkuChnId());
                criteria.and(MongoConstants.MDSE_VERSION).is(snapShotVO.getMdseVersion().toString());
                query.addCriteria(criteria);
                SnapshotDocument snapshotDocument = mongoTemplate.findOne(query, SnapshotDocument.class);
                if (snapshotDocument != null) {
                    snapShotVO.setSnapshotId(snapshotDocument.getSnapshotId());
                }
            }
            return snapShotList;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 〈保存快照信息〉
     * 
     * @param mdseId 商品ID
     * @param skuId skuID 可空
     * @param skuChnId 可空
     * @param batchId 批次ID
     * @param version 商品版本
     * @param snapshotId 快照ID
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private boolean mergeSnapShotDocument(Long mdseId, Long skuId, Long skuChnId, Long batchId, String version,
            String snapshotId) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and(MongoConstants.SKUCHN_ID).is(skuChnId);
        criteria.and(MongoConstants.MDSE_VERSION).is(version);
        query.addCriteria(criteria);
        SnapshotDocument snapshot = mongoTemplate.findOne(query, SnapshotDocument.class);
        if (snapshot == null) {
            SnapshotDocument snapshotDocument = new SnapshotDocument();
            snapshotDocument.setBatchId(batchId);
            snapshotDocument.setMdseId(mdseId);
            snapshotDocument.setSkuChnId(skuChnId);
            snapshotDocument.setSkuId(skuId);
            snapshotDocument.setSnapshotId(snapshotId);
            snapshotDocument.setVersion(version);
            mongoTemplate.save(snapshotDocument);
        } else {
            Update update = new Update().set(MongoConstants.SNAPSHOT_ID, snapshotId);
            mongoTemplate.updateFirst(query, update, SnapshotDocument.class);
        }

        return false;
    }

}
