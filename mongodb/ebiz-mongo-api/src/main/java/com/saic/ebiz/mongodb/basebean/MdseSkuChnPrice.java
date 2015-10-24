/*
 * Copyright (C), 2013-2014, 上海汽车集团有限公司
 * FileName: MdseChnPriceVO.java
 * Author:   v_Lijiang
 * Date:     2014年6月17日 上午9:02:15
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
 * 规格渠道信息
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MdseSkuChnPrice implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = -8721289144878658191L;

    /** id */
    private Long skuChnId;

    /** 规格ID */
    private Long skuId;

    /** 规格名称 */
    private String skuName;

    /** 渠道标识 */
    private Integer chnType;

    /** 初始库存 */
    private Integer initStock;

    /** 总库存 */
    private Integer stock;

    /** 冻结库存 */
    private Integer stkReserved;

    /** 后台订单预留总库存 */
    private Integer stkBackorders;

    /** 后台订单库存占用数 */
    private Integer stkReservedBk;

    /** 支付方式 */
    private Integer paymentType;

    /** 积分价格 */
    private BigDecimal pointPrice;

    /** 现金价格 */
    private BigDecimal cashPrice;

    /** 结算价 **/
    private BigDecimal settlementPrice;

    /** 现场支付给服务提供商的现金 **/
    private BigDecimal plusPrice;

    /** 　第三方规格ＩＤ　 */
    private String skuPartnerId;

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
     * @return the skuId
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * @param skuId the skuId to set
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * @return the skuName
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * @param skuName the skuName to set
     */
    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    /**
     * @return the chnType
     */
    public Integer getChnType() {
        return chnType;
    }

    /**
     * @param chnType the chnType to set
     */
    public void setChnType(Integer chnType) {
        this.chnType = chnType;
    }

    /**
     * @return the initStock
     */
    public Integer getInitStock() {
        return initStock;
    }

    /**
     * @param initStock the initStock to set
     */
    public void setInitStock(Integer initStock) {
        this.initStock = initStock;
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
     * @return the stkReserved
     */
    public Integer getStkReserved() {
        return stkReserved;
    }

    /**
     * @param stkReserved the stkReserved to set
     */
    public void setStkReserved(Integer stkReserved) {
        this.stkReserved = stkReserved;
    }

    /**
     * @return the stkBackorders
     */
    public Integer getStkBackorders() {
        return stkBackorders;
    }

    /**
     * @param stkBackorders the stkBackorders to set
     */
    public void setStkBackorders(Integer stkBackorders) {
        this.stkBackorders = stkBackorders;
    }

    /**
     * @return the stkReservedBk
     */
    public Integer getStkReservedBk() {
        return stkReservedBk;
    }

    /**
     * @param stkReservedBk the stkReservedBk to set
     */
    public void setStkReservedBk(Integer stkReservedBk) {
        this.stkReservedBk = stkReservedBk;
    }

    /**
     * @return the paymentType
     */
    public Integer getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
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
     * @return the settlementPrice
     */
    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    /**
     * @param settlementPrice the settlementPrice to set
     */
    public void setSettlementPrice(BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    /**
     * @return the plusPrice
     */
    public BigDecimal getPlusPrice() {
        return plusPrice;
    }

    /**
     * @param plusPrice the plusPrice to set
     */
    public void setPlusPrice(BigDecimal plusPrice) {
        this.plusPrice = plusPrice;
    }

    /**
     * @return the skuPartnerId
     */
    public String getSkuPartnerId() {
        return skuPartnerId;
    }

    /**
     * @param skuPartnerId the skuPartnerId to set
     */
    public void setSkuPartnerId(String skuPartnerId) {
        this.skuPartnerId = skuPartnerId;
    }

}
