/*
 * Copyright (C), 2013-2014, 上海汽车集团有限公司
 * FileName: MdseDocument.java
 * Author:   v_hudong
 * Date:     2014年6月23日 下午5:12:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.collection;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.saic.ebiz.mongodb.basebean.MdseBatchInfo;
import com.saic.ebiz.mongodb.basebean.MdseSkuChnPrice;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 商品详情document
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Document(collection = "t_mdsedocument")
public class MdseDocument extends MdseBatchInfo implements java.io.Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2077746795564742147L;

    /**
     * 文档id
     */
    @Id
    private String id;

    /** 渠道类别 */
    private Integer channelType;

    /** 规格渠道价格 */
    private List<MdseSkuChnPrice> skuPrices;

    /** 该商品所在的渠道下的组合商品 */
    @Transient
    private List<CombinedMdseDocument> combinedDocuments;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the channelType
     */
    public Integer getChannelType() {
        return channelType;
    }

    /**
     * @param channelType the channelType to set
     */
    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    /**
     * @return the skuPrices
     */
    public List<MdseSkuChnPrice> getSkuPrices() {
        return skuPrices;
    }

    /**
     * @param skuPrices the skuPrices to set
     */
    public void setSkuPrices(List<MdseSkuChnPrice> skuPrices) {
        this.skuPrices = skuPrices;
    }

    /**
     * @return the combinedDocuments
     */
    public List<CombinedMdseDocument> getCombinedDocuments() {
        return combinedDocuments;
    }

    /**
     * @param combinedDocuments the combinedDocuments to set
     */
    public void setCombinedDocuments(List<CombinedMdseDocument> combinedDocuments) {
        this.combinedDocuments = combinedDocuments;
    }

}
