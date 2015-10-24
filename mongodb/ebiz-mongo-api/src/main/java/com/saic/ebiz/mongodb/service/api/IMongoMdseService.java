/*
 * Copyright (C), 2013-2014, 上海汽车集团有限公司
 * FileName: IMongoMdseService.java
 * Author:   v_hudong
 * Date:     2014年6月19日 下午14:51:00
 * Description: 提供MongoDB商品信息查询、修改等功能
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.service.api;

import java.util.List;
import java.util.Map;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.meidusa.venus.annotations.Endpoint;
import com.meidusa.venus.annotations.Param;
import com.meidusa.venus.annotations.Service;
import com.saic.ebiz.mongodb.basebean.MdseInfo;
import com.saic.ebiz.mongodb.collection.CatalogDocument;
import com.saic.ebiz.mongodb.collection.CombinedMdseDocument;
import com.saic.ebiz.mongodb.collection.MdseDocument;
import com.saic.ebiz.mongodb.utils.MongoEbizException;
import com.saic.ebiz.mongodb.vo.mdse.CatalogMdseVO;
import com.saic.ebiz.mongodb.vo.mdse.CombinedMdseVO;
import com.saic.ebiz.mongodb.vo.mdse.MdseBatchPackageVO;

/**
 * 
 * 〈车享汇商品静态化转储服务〉<br>
 * mongodb商品服务：针对销售目录商品列表、商品详细信息的转储、更新、删除、查询
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service(name = "MongoMdseService", version = 1, description = "mongo商品信息")
public interface IMongoMdseService {

    /**
     * 
     * 功能描述: <br>
     * 〈清除销售目录商品列表信息〉
     * 
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "removeCatalogCollection", async = false)
    boolean removeCatalogCollection() throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据销售目录ID删除售目录商品列表信息〉
     * 
     * @param catalogId 销售目录ID
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "removeCatalogDocumentByCatalogId", async = false)
    boolean removeCatalogDocumentByCatalogId(@Param(name = "catalogId") Long catalogId) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈清除商品详细信息〉
     * 
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "removeMdseCollection", async = false)
    boolean removeMdseCollection() throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈更新商品详细信息〉 根据商品销售渠道，拆分为批次、渠道类别纬度的商品详细信息
     * 
     * @param {@link MdseBatchPackageVO} 车享汇商品详细信息对象
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "mergeMdseDocument", async = false)
    boolean mergeMdseDocument(@Param(name = "mdseBatchPackageVO") MdseBatchPackageVO mdseBatchPackageVO)
            throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈更新整个销售目录商品信息〉 记录销售目录下，商品某个批次下，挂在这个销售节点上的所有规格信息
     * 
     * @param catalogId 销售目录ID
     * @param List<{@link CatalogMdseVO}>
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "mergeCatalogDocument", async = false)
    boolean mergeCatalogDocument(@Param(name = "catalogId") Long catalogId,
            @Param(name = "catalogMdseVOList") List<CatalogMdseVO> catalogMdseVOList) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据批次ID删除销售目录商品信息、商品详细信息中对应的商品信息〉
     * 
     * @param batchId 批次ID
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "removeAllCollectionsByBatchId", async = false)
    boolean removeAllCollectionsByBatchId(@Param(name = "batchId") Long batchId) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据批次ID更新销售目录商品信息、商品详细信息中对应的商品信息的状态〉
     * 
     * @param batchId 批次ID
     * @param status 最新状态
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "updateStatusByBatchId", async = false)
    boolean updateAllStatusByBatchId(@Param(name = "batchId") Long batchId, @Param(name = "status") String status)
            throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据批次ID、渠道类别查询商品批次在这个渠道的详细信息（包含在该渠道销售的所有规格信息以及组合子项信息）〉
     * 
     * @param batchId 批次ID
     * @param skuChnId 规格渠道ID 可空
     * @param channelType 渠道类别
     * @return {@link MdseDocument}
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "getMdseDocumentBybatchId", async = false)
    MdseDocument getMdseDocumentBybatchId(@Param(name = "batchId") Long batchId,
            @Param(name = "skuChnId") Long skuChnId, @Param(name = "channelType") Integer channelType)
            throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据销售目录ID查询销售目录商品信息列表〉
     * 
     * @param catalogId 销售目录ID
     * @return {@link CatalogDocument} {@link List}
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "getCatalogDocsByChannelId", async = false)
    List<CatalogDocument> getCatalogDocsByCatalogId(@Param(name = "catalogId") Long catalogId)
            throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据销售目录ID和查选条件查询某一个销售目录下的符合查选条件的商品列表〉 注意事项：<br>
     * 1. 使用未建立索引的查询字段会引起性能下降; <br>
     * 2. 使用BigDecimal字段查询时，需要进行DecimalFormat转换。<br>
     * 例子：Map<String, Object> map = new HashMap<String, Object>(); <br>
     * DecimalFormat df = new DecimalFormat("0.00"); <br>
     * String data = df.format(700); <br>
     * map.put(MdseChnPriceMgVO.SETTLEMENT_PRICE, data); <br>
     * 3. 对于根据扩展属性的查询条件，Map中的key存在属性编码，value存放属性值
     * 
     * @param catalogId 销售目录ID
     * @param paraMap 参数：
     * @return
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "getCatalogDocsByParams", async = false)
    List<CatalogDocument> getCatalogDocsByParams(@Param(name = "catalogId") Long catalogId,
            @Param(name = "paraMap") Map<String, Object> paraMap) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈更新组合商品信息〉 根据商品销售渠道纬度，保存组合商品信息
     * 
     * @param {@link CombinedMdseVO} 车享汇组合商品详细信息对象
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "mergeCombinedMdseDocument", async = false)
    boolean mergeCombinedMdseDocument(@Param(name = "combinedMdseVO") CombinedMdseVO combinedMdseVO)
            throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据主商品批次ID和渠道类别查询组合商品信息〉
     * 
     * @param mainBatchId 主商品批次ID
     * @param mainSkuChnId 主商品规格ID 可空
     * @param channelType 渠道类别
     * @return {@link CombinedMdseDocument} {@link List}
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "getCombinedDocsByMainBatchId", async = false)
    List<CombinedMdseDocument> getCombinedDocsByMainBatchId(@Param(name = "mainBatchId") Long mainBatchId,
            @Param(name = "mainSkuChnId") Long mainSkuChnId, @Param(name = "channelType") Integer channelType)
            throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据组合商品批次ID删除该组合商品信息〉
     * 
     * @param batchId 组合商品批次ID
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "removeCombinedDocsByComBatchId", async = false)
    boolean removeCombinedDocsByComBatchId(@Param(name = "batchId") Long batchId) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈更新商品基本信息信息〉 需要更新销售目录和商品信息document
     * 
     * @param {@link MdseInfo} 商品基本信息对象
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "updateMdseInfo", async = false)
    boolean updateMdseInfo(@Param(name = "mdseInfo") MdseInfo mdseInfo) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据批次I查询商品详细信息（包含在该渠道销售的所有规格信息以及组合子项信息）〉
     * 
     * @param batchId 批次ID
     * @return {@link MdseDocument}
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "getMdseDocumentBybatchIdForHTML", async = false)
    List<MdseDocument> getMdseDocumentBybatchIdForHTML(@Param(name = "batchId") Long batchId) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据批次I查询商品详细信息（包含在该渠道销售的所有规格信息以及组合子项信息）〉
     * 
     * @param mdseId 商品ID
     * @return {@link MdseDocument}
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "getMdseDocumentByMdseIdForHTML", async = false)
    List<MdseDocument> getMdseDocumentByMdseIdForHTML(@Param(name = "mdseId") Long mdseId) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据展现模板查询所有的商品信息）〉
     * 
     * @param templateNo 商品展现模板编码
     * @return {@link MdseDocument}
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "getMdseDocumentByTemplateNoForHTML", async = false)
    List<MdseDocument> getMdseDocumentByTemplateNoForHTML(@Param(name = "templateNo") String templateNo)
            throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据供应商ID和销售渠道查询商品列表信息〉
     * 
     * @param providerId
     * @param channelType
     * @return
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "queryMdseDocumentListByProviderAndChn", async = false)
    List<MdseDocument> queryMdseDocumentListByProviderAndChn(@Param(name = "providerId") Long providerId,
            @Param(name = "channelType") Integer channelType) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据主商品规格ID和组合批次ID查询组合商品信息〉
     * 
     * @param batchId 组合批次ID 可空
     * @param mainSkuChnId 主商品规格ID 可空
     * @return {@link CombinedMdseDocument} {@link List}
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "getCombinedMdseDocumentByBatchId", async = false)
    List<CombinedMdseDocument> getCombinedMdseDocumentByBatchId(@Param(name = "batchId") Long batchId,
            @Param(name = "mainSkuChnId") Long mainSkuChnId) throws MongoEbizException;
    
    
    /**
     * 
     * 功能描述: <br>
     * 〈根据销售目录ID和查选条件查询某一个销售目录下的符合查选条件的商品列表〉 注意事项：<br>
     * 1. 使用未建立索引的查询字段会引起性能下降; <br>
     * 2. 使用BigDecimal字段查询时，需要进行DecimalFormat转换。<br>
     * 例子：Map<String, Object> map = new HashMap<String, Object>(); <br>
     * DecimalFormat df = new DecimalFormat("0.00"); <br>
     * String data = df.format(700); <br>
     * map.put(MdseChnPriceMgVO.SETTLEMENT_PRICE, data); <br>
     * 3. 对于根据扩展属性的查询条件，Map中的key存在属性编码，value存放属性值
     * 
     * @param catalogId 销售目录ID
     * @param paraMap 参数：
     * @return
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "getCatalogDocsWithPaginationByParams", async = false)
    PaginationResult<List<CatalogDocument>> getCatalogDocsWithPaginationByParams(@Param(name = "catalogIds") List<Long> catalogIds,
            @Param(name = "paraMap") Map<String, Object> paraMap,@Param(name = "pagination") Pagination pagination,@Param(name = "paraOrder") Map<String,String> paraOrder) throws MongoEbizException;
}
