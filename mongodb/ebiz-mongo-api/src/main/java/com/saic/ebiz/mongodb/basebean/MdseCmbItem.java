/*
 * Copyright (C), 2013-2014, 上海汽车集团有限公司
 * FileName: MdseItemBean.java
 * Author:   v_suding01
 * Date:     2014年5月21日 下午8:19:20
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.basebean;

import java.math.BigDecimal;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 组合商品子项
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MdseCmbItem implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = 1805819719263887553L;

    /** 规格渠道价格ID */
    private Long skuChnId;

    /** 商品ID */
    private Long mdseId;

    /** 批次id */
    private Long batchId;

    /** 商品名称 */
    private String mdseName;

    /** 市场价 */
    private BigDecimal marketPrice;

    /** 列表图 默认为主图 列表+详情 */
    private String mainImage;

    /** 组合后积分价格 */
    private BigDecimal pointPrice;

    /** 组合后现金价格 */
    private BigDecimal cashPrice;

    /** 原积分价格 */
    private BigDecimal originalPointPrice;

    /** 原现金价格 */
    private BigDecimal originalCashPrice;

    /** 　库存　　 */
    private Integer stock;

    /** 是否主商品 */
    private Integer isMainMdse;

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
     * @return the mdseName
     */
    public String getMdseName() {
        return mdseName;
    }

    /**
     * @param mdseName the mdseName to set
     */
    public void setMdseName(String mdseName) {
        this.mdseName = mdseName;
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
     * @return the skuChnId
     */
    public Long getSkuChnId() {
        return skuChnId;
    }

    /**
     * @param skuChnId the skuChnId to set
     */
    public void setSkuChnId(Long skuChnId) {
        this.skuChnId = skuChnId;
    }

    /**
     * @return the isMainMdse
     */
    public Integer getIsMainMdse() {
        return isMainMdse;
    }

    /**
     * @param isMainMdse the isMainMdse to set
     */
    public void setIsMainMdse(Integer isMainMdse) {
        this.isMainMdse = isMainMdse;
    }

    /**
     * @return the stock
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * @return the marketPrice
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * @param marketPrice the marketPrice to set
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * @return the mainImage
     */
    public String getMainImage() {
        return mainImage;
    }

    /**
     * @param mainImage the mainImage to set
     */
    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    /**
     * @return the pointPrice
     */
    public BigDecimal getPointPrice() {
        return pointPrice;
    }

    /**
     * @param pointPrice the pointPrice to set
     */
    public void setPointPrice(BigDecimal pointPrice) {
        this.pointPrice = pointPrice;
    }

    /**
     * @return the cashPrice
     */
    public BigDecimal getCashPrice() {
        return cashPrice;
    }

    /**
     * @param cashPrice the cashPrice to set
     */
    public void setCashPrice(BigDecimal cashPrice) {
        this.cashPrice = cashPrice;
    }

    /**
     * @return the originalPointPrice
     */
    public BigDecimal getOriginalPointPrice() {
        return originalPointPrice;
    }

    /**
     * @param originalPointPrice the originalPointPrice to set
     */
    public void setOriginalPointPrice(BigDecimal originalPointPrice) {
        this.originalPointPrice = originalPointPrice;
    }

    /**
     * @return the originalCashPrice
     */
    public BigDecimal getOriginalCashPrice() {
        return originalCashPrice;
    }

    /**
     * @param originalCashPrice the originalCashPrice to set
     */
    public void setOriginalCashPrice(BigDecimal originalCashPrice) {
        this.originalCashPrice = originalCashPrice;
    }

}
