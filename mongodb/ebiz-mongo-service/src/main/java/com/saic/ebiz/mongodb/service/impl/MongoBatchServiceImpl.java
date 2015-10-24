/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: MongoBatchServiceImpl.java
 * Author:   v_hudong
 * Date:     2014年7月14日 上午11:26:27
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.saic.ebiz.mongodb.collection.BatchDocument;
import com.saic.ebiz.mongodb.service.api.IMongoBatchService;
import com.saic.ebiz.mongodb.service.api.IMongoHtmlService;
import com.saic.ebiz.mongodb.utils.MongoConstants;
import com.saic.ebiz.mongodb.utils.MongoEbizException;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MongoBatchServiceImpl implements IMongoBatchService {
    /** logger对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoBatchServiceImpl.class);

    /** mongo数据操作template */
    @Autowired
    private MongoTemplate mongoTemplate;

    /** 静态html生成service */
    @Autowired
    private IMongoHtmlService mongoHtmlService;

    /*
     * (non-Javadoc)
     * @see com.saic.ebiz.mongodb.service.api.IBatchService#buildHtmlBatch()
     */
    @Override
    public boolean buildHtmlBatch() throws MongoEbizException {
        LOGGER.info("MongoBatchServiceImpl start");
        Query query = new Query(Criteria.where(MongoConstants.STATUS).is(MongoConstants.BatchStatus.INITIAL.ordinal()));
        query.with(new Sort(new Order(Direction.ASC, "createTime")));
        Update update = new Update();
        update.set(MongoConstants.STATUS, MongoConstants.BatchStatus.PROCESSING.ordinal());
        List<BatchDocument> batchDocuments = mongoTemplate.find(query, BatchDocument.class);
        mongoTemplate.findAndModify(query, update, BatchDocument.class);
        if (batchDocuments != null && batchDocuments.size() > 0) {
            for (BatchDocument batchDocument : batchDocuments) {
                Query queryUpdate = new Query(Criteria.where(MongoConstants.PRIMARY_KEY_ID).is(batchDocument.getId()));
                String objectType = batchDocument.getObjectType();
                try {
                    if (MongoConstants.ObjectType.BATCHID.toString().equals(objectType)) {
                        mongoHtmlService.buildHtmlByMdseBatchId(new Long(batchDocument.getObject()));
                    } else if (MongoConstants.ObjectType.FTLID.toString().equals(objectType)) {
                        mongoHtmlService.refreshMdseHtmlByTemplateNo(batchDocument.getObject());
                    } else if (MongoConstants.ObjectType.MDSEID.toString().equals(objectType)) {
                        mongoHtmlService.buildHtmlByMdseId(new Long(batchDocument.getObject()));
                    }
                    update.set(MongoConstants.STATUS, MongoConstants.BatchStatus.SUCCESS.ordinal());
                } catch (Exception e) {
                    LOGGER.error("批处理失败:batchDocumentId:{} + error:{}", batchDocument.getId(), e.getMessage());
                    update.set(MongoConstants.STATUS, MongoConstants.BatchStatus.FAILED.ordinal());
                } finally {
                    mongoTemplate.updateFirst(queryUpdate, update, BatchDocument.class);
                }
            }
        }
        return false;
    }

}
