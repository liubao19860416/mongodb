/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: MongoMdseServiceImpl.java
 * Author:   v_hudong
 * Date:     2014年7月9日 下午12:56:52
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
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

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.saic.ebiz.mongodb.basebean.MdseCmbItem;
import com.saic.ebiz.mongodb.basebean.MdseInfo;
import com.saic.ebiz.mongodb.basebean.MdseSkuChnPrice;
import com.saic.ebiz.mongodb.collection.BatchDocument;
import com.saic.ebiz.mongodb.collection.CatalogDocument;
import com.saic.ebiz.mongodb.collection.CombinedMdseDocument;
import com.saic.ebiz.mongodb.collection.MdseDocument;
import com.saic.ebiz.mongodb.service.api.IMongoMdseService;
import com.saic.ebiz.mongodb.utils.MongoConstants;
import com.saic.ebiz.mongodb.utils.MongoEbizException;
import com.saic.ebiz.mongodb.vo.mdse.CatalogMdseVO;
import com.saic.ebiz.mongodb.vo.mdse.CombinedMdseVO;
import com.saic.ebiz.mongodb.vo.mdse.MdseBatchPackageVO;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MongoMdseServiceImpl implements IMongoMdseService {

    /** 日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoMdseServiceImpl.class);
    /** mongo数据操作template */
    @Autowired
    private MongoTemplate mongoTemplate;

    static {
        BigDecimalConverter bd = new BigDecimalConverter(0);
        ConvertUtils.register(bd, java.math.BigDecimal.class);
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
    }

    /*
     * (non-Javadoc)
     * @see com.ebiz.mongodb.service.api.IMongoMdseService#removeCatalogCollection()
     */
    @Override
    public boolean removeCatalogCollection() throws MongoEbizException {
        try {
            LOGGER.debug("call drop collection command for catalog and catalogtemp");
            mongoTemplate.dropCollection(MongoConstants.COLLECTION_CATALOG);
            mongoTemplate.dropCollection(MongoConstants.COLLECTION_CATALOG_TEMP);
            LOGGER.debug("drop collection command for catalog and catalogtemp finished");
            return true;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ebiz.mongodb.service.api.IMongoMdseService#removeMdseCollection()
     */
    @Override
    public boolean removeMdseCollection() throws MongoEbizException {
        try {
            LOGGER.debug("call drop collection command for mdse and mdsetemp");
            mongoTemplate.dropCollection(MongoConstants.COLLECTION_MDSE);
            mongoTemplate.dropCollection(MongoConstants.COLLECTION_MDSE_TEMP);
            LOGGER.debug("drop collection command for mdse and mdsetemp finished");
            return true;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ebiz.mongodb.service.api.IMongoMdseService#mergeMdseDocument(com.ebiz.mongodb.vo.mdse.MdseBatchPackageVO)
     */
    @Override
    public boolean mergeMdseDocument(MdseBatchPackageVO mdseBatchPackageVO) throws MongoEbizException {
        if (mdseBatchPackageVO == null || mdseBatchPackageVO.getBatchId() == null) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, "parameter can't be null!");
        }
        try {
            // 组装MdseDocument对象
            List<MdseDocument> mdseDocs = this.establishMdseDocument(mdseBatchPackageVO);
            Map<Long, MdseSkuChnPrice> skuPriceMap = new HashMap<Long,MdseSkuChnPrice>();
            if (mdseBatchPackageVO.getSkuPrices() != null && mdseBatchPackageVO.getSkuPrices().size() > 0) {
                for(MdseSkuChnPrice mdseSkuChnPrice:mdseBatchPackageVO.getSkuPrices()){
                    skuPriceMap.put(mdseSkuChnPrice.getSkuChnId(), mdseSkuChnPrice);
                }
            }
            // insert or update
            for (MdseDocument document : mdseDocs) {
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and(MongoConstants.BATCH_ID).is(document.getBatchId());
                criteria.and(MongoConstants.CHANNEL_TYPE).is(document.getChannelType());
                query.addCriteria(criteria);
                mongoTemplate.remove(query, MdseDocument.class);
                mongoTemplate.save(document);
            }
            
            if(mdseDocs!=null && mdseDocs.size()>0){
                Long batchId = mdseDocs.get(0).getBatchId();
                List<CombinedMdseDocument> combinedMdseDocuments = getCombinedDocsByBatchId(batchId);
                if(combinedMdseDocuments!=null){
                    for (CombinedMdseDocument combinedMdseDocument : combinedMdseDocuments) {
                        List<MdseCmbItem> itemList = combinedMdseDocument.getItemList();
                        if(itemList!=null){
                            for(MdseCmbItem mdseCmbItem:itemList){
                                if(mdseCmbItem.getBatchId()!=null && mdseCmbItem.getBatchId().equals(batchId)){
                                    MdseSkuChnPrice skuChnPrice = skuPriceMap.get(mdseCmbItem.getSkuChnId());
                                    if(skuChnPrice!=null){
                                        mdseCmbItem.setOriginalCashPrice(skuChnPrice.getCashPrice());
                                        mdseCmbItem.setOriginalPointPrice(skuChnPrice.getPointPrice());
                                    }
                                }
                            }
                        }
                        mongoTemplate.save(combinedMdseDocument);
                    }
                }
            }
            
            if (mdseDocs != null && mdseDocs.size() > 0) {
                saveBatchDocument(MongoConstants.ObjectType.BATCHID.toString(), mdseBatchPackageVO.getBatchId()
                        .toString());// 保存批次信息
            }
            return true;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ebiz.mongodb.service.api.IMongoMdseService#mergeCatalogDocument(com.ebiz.mongodb.vo.mdse.CatalogMdseVO)
     */
    @Override
    public boolean mergeCatalogDocument(Long catalogId, List<CatalogMdseVO> catalogMdseVOList)
            throws MongoEbizException {
        if (catalogId == null) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, "parameter can't be null!");
        }
        try {
            // 将数据更新至临时表
            if (this.updateTempCatalogDocument(catalogId, catalogMdseVOList)) {
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and(MongoConstants.CATALOG_ID).is(catalogId);
                query.addCriteria(criteria);
                List<CatalogDocument> cataList = mongoTemplate.find(query, CatalogDocument.class,
                        MongoConstants.COLLECTION_CATALOG_TEMP);
                mongoTemplate.remove(query, CatalogDocument.class, MongoConstants.COLLECTION_CATALOG);
                if(cataList!=null && cataList.size()>0)
                    mongoTemplate.insertAll(cataList);
            }
            return true;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ebiz.mongodb.service.api.IMongoMdseService#removeAllCollectionsByBatchId(java.lang.Long)
     */
    @Override
    public boolean removeAllCollectionsByBatchId(Long batchId) throws MongoEbizException {
        try {
            LOGGER.debug("*** removeAllCollectionsByBatchId start *** param = batchId:{} ", batchId);
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and(MongoConstants.BATCH_ID).is(batchId);
            query.addCriteria(criteria);
            // 删除商品视图数据
            mongoTemplate.remove(query, MdseDocument.class);
            // 删除销售目录数据
            mongoTemplate.remove(query, CatalogDocument.class);
            LOGGER.debug("*** removeAllCollectionsByBatchId end *** param = batchId:{} ", batchId);
            return true;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ebiz.mongodb.service.api.IMongoMdseService#updateAllStatusByBatchId(java.lang.Long, java.lang.String)
     */
    @Override
    public boolean updateAllStatusByBatchId(Long batchId, String status) throws MongoEbizException {
        try {
            LOGGER.debug("*** updateAllStatusByBatchId start *** param = batchId:{} and status:{}", batchId, status);
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and(MongoConstants.BATCH_ID).is(batchId);
            query.addCriteria(criteria);
            Update update = new Update();
            update.set(MongoConstants.STATUS, status);
            // 更新商品视图状态
            mongoTemplate.updateMulti(query, update, MdseDocument.class);
            // 更新销售目录视图状态
            mongoTemplate.updateMulti(query, update, CatalogDocument.class);
            LOGGER.debug("*** updateAllStatusByBatchId end *** param = batchId:{} and status:{}", batchId, status);
            return true;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ebiz.mongodb.service.api.IMongoMdseService#getMdseDocumentBybatchId(java.lang.Long, java.lang.String)
     */
    @Override
    public MdseDocument getMdseDocumentBybatchId(Long batchId, Long skuChnId, Integer channelType)
            throws MongoEbizException {
        try {
            List<MdseDocument> mdseDocumentList = this.getMdseDocumentExCombinedBybatchId(batchId, skuChnId,
                    channelType,MongoConstants.MDSE_STATUS_SHELFED);
            if (mdseDocumentList != null && mdseDocumentList.size() > 0) {
                MdseDocument mdseDocument = mdseDocumentList.get(0);
                // 设置组合商品
                if (skuChnId != null) {
                    mdseDocument
                            .setCombinedDocuments(this.getCombinedDocsByMainBatchId(batchId, skuChnId, channelType));
                } else {
                    List<MdseSkuChnPrice> skuPrices = mdseDocument.getSkuPrices();
                    List<CombinedMdseDocument> combinedMdseDocuments = new ArrayList<CombinedMdseDocument>();
                    for (MdseSkuChnPrice mdseSkuChnPrice : skuPrices) {
                        combinedMdseDocuments.addAll(this.getCombinedDocsByMainBatchId(batchId,
                                mdseSkuChnPrice.getSkuId(), mdseDocument.getChannelType()));
                    }
                    mdseDocument.setCombinedDocuments(combinedMdseDocuments);
                }
                return mdseDocument;
            }
            return null;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 〈获取商品信息-不包含组合商品〉
     * 
     * @param batchId 批次ID
     * @param skuChnId 规格渠道ID
     * @param channelType 渠道类别
     * @return
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private List<MdseDocument> getMdseDocumentExCombinedBybatchId(Long batchId, Long skuChnId, Integer channelType,Integer status)
            throws MongoEbizException {
        try {
            LOGGER.debug("*** getMdseDocumentBybatchId *** param = batchId:{} and channelType:{}", batchId, channelType);
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and(MongoConstants.BATCH_ID).is(batchId);
            if (channelType != null) {
                criteria.and(MongoConstants.CHANNEL_TYPE).is(channelType);
            }
            if(status!=null)
            criteria.and(MongoConstants.STATUS).is(status);
            query.addCriteria(criteria);
            List<MdseDocument> mdseDocumentList = mongoTemplate.find(query, MdseDocument.class);
            for (MdseDocument mdseDocument : mdseDocumentList) {
                // 过滤skuChnId
                List<MdseSkuChnPrice> skuPrices = new ArrayList<MdseSkuChnPrice>();
                if (skuChnId != null && mdseDocument != null && !mdseDocument.getSkuPrices().isEmpty()) {
                    for (MdseSkuChnPrice chnPrice : mdseDocument.getSkuPrices()) {
                        if (chnPrice.getSkuChnId().equals(skuChnId)) {
                            skuPrices.add(chnPrice);
                            break;
                        }
                    }
                    if (skuPrices.size() < 1) {
                        throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, "未找到对应的规格渠道ID:"
                                + skuChnId);
                    }
                    mdseDocument.setSkuPrices(skuPrices);
                }
            }
            return mdseDocumentList;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ebiz.mongodb.service.api.IMongoMdseService#getCatalogDocsByCatalogId(java.lang.Long)
     */
    @Override
    public List<CatalogDocument> getCatalogDocsByCatalogId(Long catalogId) throws MongoEbizException {
        try {
            LOGGER.debug("*** getCatalogDocsByCatalogId start *** param = catalogId:{} and channelType:{}", catalogId);
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and(MongoConstants.CATALOG_ID).is(catalogId);
            query.addCriteria(criteria);
            return mongoTemplate.find(query, CatalogDocument.class);
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ebiz.mongodb.service.api.IMongoMdseService#getCatalogDocsByParams(java.lang.String, java.util.Map)
     */
    @Override
    public List<CatalogDocument> getCatalogDocsByParams(Long catalogId, Map<String, Object> paraMap)
            throws MongoEbizException {
        try {
            LOGGER.debug("*** getCatalogDocsByParams start *** param = catalogId:{} and paraMap:{}", catalogId,
                    paraMap.toString());
            Query query = new Query();
            Criteria criteria = new Criteria();
            List<Criteria> subCriteriaList = new ArrayList<Criteria>();
            criteria.and(MongoConstants.CATALOG_ID).is(catalogId);
            if (!paraMap.isEmpty()) {
                boolean subFlag = false;
                for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
                    String key = entry.getKey();
                    if (key.startsWith("P")) { // 扩展属性
                        Criteria subCriteria = new Criteria();
                        Criteria elemMatchCriteria = new Criteria();
                        elemMatchCriteria.and("attrDefNo").is(key);
                        elemMatchCriteria.and("value").is(entry.getValue());
                        subCriteriaList.add(subCriteria.and(MongoConstants.ATTRIBUTES).elemMatch(elemMatchCriteria));
                        subFlag = true;
                    } else {
                        criteria.and(key).is(paraMap.get(key));
                    }
                }
                if (subFlag && subCriteriaList.size() > 0) {
                    int s = subCriteriaList.size();
                    Criteria[] criterias = (Criteria[]) subCriteriaList.toArray(new Criteria[s]);
                    criteria.andOperator(criterias);
                }
            }
            query.addCriteria(criteria);
            return mongoTemplate.find(query, CatalogDocument.class);
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 根据接口对象组装document对象
     * 
     * @param mdseBatchPackageVO
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private List<MdseDocument> establishMdseDocument(MdseBatchPackageVO mdseBatchPackageVO) throws MongoEbizException {
        try {
            MdseDocument mdseDocument = new MdseDocument();
            // 通用属性复制
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            BeanUtils.copyProperties(mdseDocument, mdseBatchPackageVO);
            // 渠道切分
            Map<Integer, List<MdseSkuChnPrice>> skuPriceMap = new HashMap<Integer, List<MdseSkuChnPrice>>();
            if (mdseDocument.getSkuPrices() != null && mdseDocument.getSkuPrices().size() > 0) {
                for (MdseSkuChnPrice skuPrice : mdseDocument.getSkuPrices()) {
                    if (skuPriceMap.get(skuPrice.getChnType()) != null) {
                        skuPriceMap.get(skuPrice.getChnType()).add(skuPrice);
                    } else {
                        List<MdseSkuChnPrice> tempList = new ArrayList<MdseSkuChnPrice>();
                        tempList.add(skuPrice);
                        skuPriceMap.put(skuPrice.getChnType(), tempList);
                    }
                }
            }
            // 重新组装
            List<MdseDocument> documents = new ArrayList<MdseDocument>();
            for (Map.Entry<Integer, List<MdseSkuChnPrice>> entry : skuPriceMap.entrySet()) {
                MdseDocument mdsedoc = (MdseDocument) BeanUtils.cloneBean(mdseDocument);
                mdsedoc.setChannelType(entry.getKey());
                mdsedoc.setSkuPrices(entry.getValue());
                documents.add(mdsedoc);
            }
            return documents;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }

    }

    /**
     * 
     * 功能描述: <br>
     * 根据接口对象组装catalog document对象
     * 
     * @param catalogMdseVO
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private CatalogDocument establishCatalogDocument(CatalogMdseVO catalogMdseVO) throws MongoEbizException {
        try {
            CatalogDocument catalogDocument = new CatalogDocument();
            // 查询商品数据
            List<MdseDocument> mdseDocumentList = this.getMdseDocumentExCombinedBybatchId(catalogMdseVO.getBatchId(),
                    null, catalogMdseVO.getChannelType(),MongoConstants.MDSE_STATUS_SHELFED);
            if (mdseDocumentList == null || mdseDocumentList.size() != 1) {
                LOGGER.error("为查询到对应的商品信息--- catalogId:{} + batchId:{} ", catalogMdseVO.getCatalogId(),
                        catalogMdseVO.getBatchId());
                throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, "为查询到对应的商品信息");
            }
            MdseDocument mdseDocument = mdseDocumentList.get(0);
            // 过滤规格
            List<MdseSkuChnPrice> skuPrices = new ArrayList<MdseSkuChnPrice>();
            for (Long skuChnId : catalogMdseVO.getSkuList()) {
                boolean existsFlag = false;
                for (MdseSkuChnPrice mdseSkuChnPrice : mdseDocument.getSkuPrices()) {
                    if (skuChnId.equals(mdseSkuChnPrice.getSkuId())) {
                        skuPrices.add(mdseSkuChnPrice);
                        existsFlag = true;
                        break;
                    }
                }
                if (!existsFlag) {
                    LOGGER.error("商品信息中未找到对应的规格信息--- catalogId:{} + batchId:{} + skuChnId:{}",
                            catalogMdseVO.getCatalogId(), catalogMdseVO.getBatchId(), skuChnId);
                }
            }
            mdseDocument.setSkuPrices(skuPrices);
            // 组装销售目录对象
            // 通用属性复制
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            BeanUtils.copyProperties(catalogDocument, mdseDocument);
            //
            catalogDocument.setCatalogId(catalogMdseVO.getCatalogId());
            catalogDocument.setId(null);
            return catalogDocument;
        } catch (Exception e) {
            LOGGER.error("组装销售目录商品信息错--- catalogId:{} + batchId:{} + error:{}", catalogMdseVO.getCatalogId(),
                    catalogMdseVO.getBatchId(), e.getMessage());
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }

    }

    @Override
    public boolean mergeCombinedMdseDocument(CombinedMdseVO combinedMdseVO) throws MongoEbizException {
        if (combinedMdseVO == null || combinedMdseVO.getBatchId() == null || combinedMdseVO.getItemList() == null) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, "parameter can't be null!");
        }
        try {
            // 组装MdseDocument对象
            CombinedMdseDocument combinedMdseDocument = this.establishCombinedMdseDocument(combinedMdseVO);
            // insert or update
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and(MongoConstants.BATCH_ID).is(combinedMdseDocument.getBatchId());
            query.addCriteria(criteria);
            mongoTemplate.remove(query, CombinedMdseDocument.class);
            mongoTemplate.save(combinedMdseDocument);
            return true;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 根据接口对象组装catalog document对象
     * 
     * @param catalogMdseVO
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private CombinedMdseDocument establishCombinedMdseDocument(CombinedMdseVO combinedMdseVO) throws MongoEbizException {
        try {
            CombinedMdseDocument combinedMdseDocument = new CombinedMdseDocument();
            // 通用属性复制
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            BeanUtils.copyProperties(combinedMdseDocument, combinedMdseVO);
            // 设置主商品批次和规格ID
            for (MdseCmbItem cmbItem : combinedMdseVO.getItemList()) {
                if (cmbItem.getIsMainMdse() == 1) {
                    combinedMdseDocument.setMainBatchId(cmbItem.getBatchId());
                    combinedMdseDocument.setMainSkuChnId(cmbItem.getSkuChnId());
                }
            }
            return combinedMdseDocument;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }

    }

    @Override
    public List<CombinedMdseDocument> getCombinedDocsByMainBatchId(Long mainBatchId, Long mainSkuChnId,
            Integer channelType) throws MongoEbizException {
        try {
            LOGGER.debug(
                    "*** getCombinedDocsByMainBatchId start *** param = mainBatchId:{} and mainSkuChnId:{} and channelType:{}",
                    mainBatchId, mainSkuChnId, channelType);
            Query query = new Query();
            Criteria criteria = new Criteria();
            if (mainBatchId != null) {
                criteria.and(MongoConstants.MAIN_BATCH_ID).is(mainBatchId);
            }
            if (mainSkuChnId != null) {
                criteria.and(MongoConstants.MAIN_SKUCHN_ID).is(mainSkuChnId);
            }
            criteria.and(MongoConstants.CHANNEL_TYPE).is(channelType);
            query.addCriteria(criteria);
            return mongoTemplate.find(query, CombinedMdseDocument.class);
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    @Override
    public boolean removeCombinedDocsByComBatchId(Long batchId) throws MongoEbizException {
        try {
            LOGGER.debug("*** removeCombinedDocsByComBatchId start *** param = batchId:{} ", batchId);
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and(MongoConstants.BATCH_ID).is(batchId);
            query.addCriteria(criteria);
            // 删除组合商品视图数据
            mongoTemplate.remove(query, CombinedMdseDocument.class);
            LOGGER.debug("*** removeCombinedDocsByComBatchId end *** param = batchId:{} ", batchId);
            return true;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
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
    private boolean saveBatchDocument(String objectType, String objectValue) throws MongoEbizException {
        if (objectType == null || objectValue == null) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, "parameter can't be null!");
        }
        Query query = new Query(Criteria.where(MongoConstants.BATCH_OBJECT).is(objectValue)
                .and(MongoConstants.BATCH_OBJECTTYPE).is(objectType).and(MongoConstants.STATUS)
                .is(MongoConstants.BatchStatus.INITIAL.ordinal()));
        BatchDocument batchDocuments = mongoTemplate.findOne(query, BatchDocument.class);
        if (batchDocuments != null) {
            return true;
        }
        try {
            BatchDocument batchDocument = new BatchDocument();
            batchDocument.setBatchType(MongoConstants.BatchType.HTML.toString());
            batchDocument.setBusinessType(MongoConstants.BusinessType.MDSEHTML.toString());
            batchDocument.setStatus(MongoConstants.BatchStatus.INITIAL.ordinal());
            batchDocument.setCreateTime(new Date());
            batchDocument.setUpdateTime(new Date());
            batchDocument.setObjectType(objectType);
            batchDocument.setObject(objectValue);
            mongoTemplate.save(batchDocument);
            return true;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 〈更新临时表数据〉
     * 
     * @param catalogId 销售目录ID
     * @param catalogMdseVOList 销售目录商品关系
     * @return
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private boolean updateTempCatalogDocument(Long catalogId, List<CatalogMdseVO> catalogMdseVOList)
            throws MongoEbizException {
        if (catalogId == null) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, "parameter can't be null!");
        }
        try {
            // 清除临时表数据
            if (mongoTemplate.collectionExists(MongoConstants.COLLECTION_CATALOG_TEMP)) {
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and(MongoConstants.CATALOG_ID).is(catalogId);
                query.addCriteria(criteria);
                mongoTemplate.remove(query, CatalogDocument.class, MongoConstants.COLLECTION_CATALOG_TEMP);
            }
            // 将数据更新至临时表
            if(catalogMdseVOList!=null && catalogMdseVOList.size()>0){
	            for (CatalogMdseVO catalogMdseVO : catalogMdseVOList) {
	                // 组装MdseDocument对象
	                CatalogDocument catalogDocument = this.establishCatalogDocument(catalogMdseVO);
	                // insert
	                mongoTemplate.insert(catalogDocument, MongoConstants.COLLECTION_CATALOG_TEMP);
	            }
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("组装销售目录商品信息临时表错误--- catalogId:{}  + error:{}", catalogId, e.getMessage());
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    @Override
    public boolean updateMdseInfo(MdseInfo mdseInfo) throws MongoEbizException {
        if (mdseInfo == null || mdseInfo.getMdseId() == null) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, "parameter can't be null!");
        }
        try {
            // 组装查询条件
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and(MongoConstants.MDSE_ID).is(mdseInfo.getMdseId());
            query.addCriteria(criteria);
            List<MdseDocument> mdseDocuments = mongoTemplate.find(query, MdseDocument.class);
            List<CatalogDocument> catalogDocuments = mongoTemplate.find(query, CatalogDocument.class);
            List<CombinedMdseDocument> combinedMdseDocuments = getCombinedDocsByMainMdseId(mdseInfo.getMdseId());
            
            if (mdseDocuments != null) {
                for (MdseDocument mdseDocument : mdseDocuments) {
                    BeanUtils.copyProperties(mdseDocument, mdseInfo);
                }
                mongoTemplate.remove(query, MdseDocument.class);
                mongoTemplate.insertAll(mdseDocuments);
            }
            if (catalogDocuments != null) {
                for (CatalogDocument catalogDocument : catalogDocuments) {
                    BeanUtils.copyProperties(catalogDocument, mdseInfo);
                }
                mongoTemplate.remove(query, CatalogDocument.class);
                mongoTemplate.insertAll(catalogDocuments);
            }
            
            if(combinedMdseDocuments!=null){
                for (CombinedMdseDocument combinedMdseDocument : combinedMdseDocuments) {
                    List<MdseCmbItem> itemList = combinedMdseDocument.getItemList();
                    if(itemList!=null){
                        for(MdseCmbItem mdseCmbItem:itemList){
                            if(mdseCmbItem.getMdseId()!=null && mdseCmbItem.getMdseId().equals(mdseInfo.getMdseId())){
                                mdseCmbItem.setMainImage(mdseInfo.getMainImage());
                                mdseCmbItem.setMdseName(mdseInfo.getMdseName());
                                mdseCmbItem.setMarketPrice(mdseInfo.getMarketPrice());
                            }
                        }
                    }
                    mongoTemplate.save(combinedMdseDocument);
                }
               
            }
            
            // 生成商品快照批处理任务
            saveBatchDocument(MongoConstants.ObjectType.MDSEID.toString(), mdseInfo.getMdseId().toString());
            return true;
        } catch (Exception e) {
            LOGGER.error("商品信息更新出错--- mdseId:{}  + error:{}", mdseInfo.getMdseId(), e.getMessage());
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    @Override
    public List<MdseDocument> getMdseDocumentBybatchIdForHTML(Long batchId) throws MongoEbizException {
        try {
            List<MdseDocument> mdseDocumentList = this.getMdseDocumentExCombinedBybatchId(batchId, null, null,null);
            return mdseDocumentList;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    @Override
    public List<MdseDocument> getMdseDocumentByMdseIdForHTML(Long mdseId) throws MongoEbizException {
        try {
            List<MdseDocument> mdseDocumentList = this.getMdseDocumentsByMdseId(mdseId);
            if (mdseDocumentList != null && mdseDocumentList.size() > 0) {
                MdseDocument mdseDocument = mdseDocumentList.get(0);
                // 设置组合商品
                List<MdseSkuChnPrice> skuPrices = mdseDocument.getSkuPrices();
                List<CombinedMdseDocument> combinedMdseDocuments = new ArrayList<CombinedMdseDocument>();
                for (MdseSkuChnPrice mdseSkuChnPrice : skuPrices) {
                    combinedMdseDocuments.addAll(this.getCombinedDocsByMainBatchId(mdseDocument.getBatchId(),
                            mdseSkuChnPrice.getSkuId(), mdseDocument.getChannelType()));
                }
                mdseDocument.setCombinedDocuments(combinedMdseDocuments);
            }
            return mdseDocumentList;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 〈根据商品ID查询商品信息〉
     * 
     * @param mdseId
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private List<MdseDocument> getMdseDocumentsByMdseId(Long mdseId) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and(MongoConstants.MDSE_ID).is(mdseId);
        query.addCriteria(criteria);
        return mongoTemplate.find(query, MdseDocument.class);
    }

    @Override
    public List<MdseDocument> getMdseDocumentByTemplateNoForHTML(String templateNo) throws MongoEbizException {
        try {
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and(MongoConstants.TEMPLATE_NO).is(templateNo);
            query.addCriteria(criteria);
            List<MdseDocument> mdseDocumentList = mongoTemplate.find(query, MdseDocument.class);
            return mdseDocumentList;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    @Override
    public List<MdseDocument> queryMdseDocumentListByProviderAndChn(Long providerId, Integer channelType)
            throws MongoEbizException {
        try {
            LOGGER.debug("*** queryMdseDocumentListByProviderAndChn *** param = providerId:{} and channelType:{}",
                    providerId, channelType);
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and(MongoConstants.PROVIDER_ID).is(providerId);
            criteria.and(MongoConstants.CHANNEL_TYPE).is(channelType);
            criteria.and(MongoConstants.STATUS).is(MongoConstants.MDSE_STATUS_SHELFED);
            query.addCriteria(criteria);
            List<MdseDocument> mdseDocumentList = mongoTemplate.find(query, MdseDocument.class);
            return mdseDocumentList;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    @Override
    public boolean removeCatalogDocumentByCatalogId(Long catalogId) throws MongoEbizException {
        try {
            LOGGER.debug("*** removeCatalogDocumentByCatalogId *** param = catalogId:{}", catalogId);
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and(MongoConstants.CATALOG_ID).is(catalogId);
            query.addCriteria(criteria);
            mongoTemplate.remove(query, CatalogDocument.class);
            return true;
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }

    }

    @Override
    public List<CombinedMdseDocument> getCombinedMdseDocumentByBatchId(Long batchId, Long mainSkuChnId)
            throws MongoEbizException {
        try {
            LOGGER.debug("*** getCombinedMdseDocumentByBatchId start *** param = batchId:{} and mainSkuChnId:{}",
                    batchId, mainSkuChnId);
            Query query = new Query();
            Criteria criteria = new Criteria();
            if (batchId != null) {
                criteria.and(MongoConstants.BATCH_ID).is(batchId);
            }
            if (mainSkuChnId != null) {
                criteria.and(MongoConstants.MAIN_SKUCHN_ID).is(mainSkuChnId);
            }
            query.addCriteria(criteria);
            return mongoTemplate.find(query, CombinedMdseDocument.class);
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }
    
    private List<CombinedMdseDocument> getCombinedDocsByMainMdseId(Long mdseId) throws MongoEbizException {
        try {
            LOGGER.debug("*** getCombinedDocsByMainMdseId start *** param = mdseId:{}",mdseId);
            Query query = new Query();
            Criteria criteria = new Criteria();
            if (mdseId != null) {
                criteria.and("itemList.mdseId").is(mdseId);
            }
            query.addCriteria(criteria);
            return mongoTemplate.find(query, CombinedMdseDocument.class);
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }
    
    private List<CombinedMdseDocument> getCombinedDocsByBatchId(Long batchId) throws MongoEbizException {
        try {
            LOGGER.debug("*** getCombinedDocsByBatchId start *** param = batchId:{}",batchId);
            Query query = new Query();
            Criteria criteria = new Criteria();
            if (batchId != null) {
                criteria.and("itemList.batchId").is(batchId);
            }
            query.addCriteria(criteria);
            return mongoTemplate.find(query, CombinedMdseDocument.class);
        } catch (Exception e) {
            throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
        }
    }

    @Override
	public PaginationResult<List<CatalogDocument>> getCatalogDocsWithPaginationByParams(List<Long> catalogIds, Map<String, Object> paraMap,
			Pagination pagination, Map<String, String> paraOrder) throws MongoEbizException {
		try {
			LOGGER.info("********************** getCatalogDocsWithPaginationByParams start  param = catalogIds:{} and paraMap:{} and pagination:{} and paraOrder:{}",catalogIds != null ? catalogIds.toString() : "", paraMap != null ? paraMap.toString() : "",pagination!=null?pagination.toString():"",paraOrder != null ? paraOrder.toString() : "");
			if (catalogIds == null)
				return new PaginationResult<List<CatalogDocument>>(new ArrayList<CatalogDocument>(), pagination);

			Map<String, Direction> orders = new HashMap<String, Direction>();
			orders.put(MongoConstants.ASC_KEY, Direction.ASC);
			orders.put(MongoConstants.DESC_KEY, Direction.DESC);
			Query query = new Query();
			Criteria criteria = new Criteria();
			List<Criteria> subCriteriaList = new ArrayList<Criteria>();
			criteria.and(MongoConstants.CATALOG_ID).in(catalogIds);
			if (!paraMap.isEmpty()) {
				boolean subFlag = false;
				for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
					String key = entry.getKey();
					if (key.startsWith("P")) { // 扩展属性
						Criteria subCriteria = new Criteria();
						Criteria elemMatchCriteria = new Criteria();
						elemMatchCriteria.and("attrDefNo").is(key);
						elemMatchCriteria.and("value").is(entry.getValue());
						subCriteriaList.add(subCriteria.and(MongoConstants.ATTRIBUTES).elemMatch(elemMatchCriteria));
						subFlag = true;
					} else {
						criteria.and(key).is(paraMap.get(key));
					}
				}
				if (subFlag && subCriteriaList.size() > 0) {
					int s = subCriteriaList.size();
					Criteria[] criterias = (Criteria[]) subCriteriaList.toArray(new Criteria[s]);
					criteria.andOperator(criterias);
				}
			}
			query.addCriteria(criteria);
			if(pagination!=null)
				pagination.setTotalRows((int) mongoTemplate.count(query, CatalogDocument.class));
			if (!paraOrder.isEmpty()) {
				List<Order> orderList = new ArrayList<Order>();
				for (Map.Entry<String, String> entry : paraOrder.entrySet()) {
					orderList.add(new Order(orders.get(entry.getValue()), entry.getKey()));
				}
				query.with(new Sort(orderList));
			}
			if(pagination!=null)
			  query.skip(pagination.getFirstRowIndex()).limit(pagination.getPagesize());
			List<CatalogDocument> catalogDocuments = mongoTemplate.find(query, CatalogDocument.class);
			LOGGER.info("**********************getCatalogDocsWithPaginationByParams result size:{}",catalogDocuments!=null?catalogDocuments.size():0);
			return new PaginationResult<List<CatalogDocument>>(catalogDocuments, pagination);
		} catch (Exception e) {
			throw new MongoEbizException(MongoEbizException.MONGO_DEFAULT_ERROR_CODE, e);
		}
	}

}
