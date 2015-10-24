/*
 * Copyright (C), 2013-2014, 上海汽车集团有限公司
 * FileName: MdseCatalogMgVO.java
 * Author:   v_xieyingbin
 * Date:     2014年6月23日 下午5:12:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.vo.mdse;

import java.util.List;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 销售目录信息
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CatalogMdseVO implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = 6631730932926300966L;

    /** catalogId */
    private Long catalogId;

    /** 渠道类别 */
    private Integer channelType;

    /**
     * 商品id
     */
    private Long mdseId;

    /**
     * 商品批次id
     */
    private Long batchId;

    /**
     * 规格信息
     */
    private List<Long> skuList;

    /**
     * @return the catalogId
     */
    public Long getCatalogId() {
        return catalogId;
    }

    /**
     * @param catalogId the catalogId to set
     */
    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    /**
     * @return the mdseId
     */
    public Long getMdseId() {
        return mdseId;
    }

    /**
     * @param mdseId the mdseId to set
     */
    public void setMdseId(Long mdseId) {
        this.mdseId = mdseId;
    }

    /**
     * @return the batchId
     */
    public Long getBatchId() {
        return batchId;
    }

    /**
     * @param batchId the batchId to set
     */
    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    /**
     * @return the skuList
     */
    public List<Long> getSkuList() {
        return skuList;
    }

    /**
     * @param skuList the skuList to set
     */
    public void setSkuList(List<Long> skuList) {
        this.skuList = skuList;
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

}
