/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: MdseBatchInfo.java
 * Author:   v_hudong
 * Date:     2014年7月10日 下午3:34:02
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.basebean;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * 〈一句话功能简述〉<br>
 * 商品批次信息
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MdseBatchInfo extends MdseInfo implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = -6514350339806246681L;

    /** 批次Id */
    @Indexed
    private Long batchId;

    /** 　第三方产品ＩＤ　 */
    private String partnerId;

    /** 供应商ID */
    private Long providerId;

    /** 供应商名称 */
    private String providerName;
    
    /** dop编码 */
    private String dopNo;

    /** 商品来源 */
    private Integer productSource;

    /** 可购买时间（开始） */
    private Date buyDateStart;

    /** 可购买时间（结束） */
    private Date buyDateEnd;

    /** 有效期（开始） */
    private Date dateStart;

    /** 有效期（结束） */
    private Date dateEnd;

    /** 是否组合商品 */
    private Integer isCombined;

    /** 自动下架时间 */
    private Date offshelfTime;

    /** 售罄下架标识 */
    private Integer selloffshelfFlag;

    /** 自动下架标志 */
    private Integer offshelfFlag;

    /** 人工下架标志 */
    private Integer manualFlag;

    /**
     * 0 - 不在架， 1 - 在架
     */
    private Integer status;

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
     * @return the partnerId
     */
    public String getPartnerId() {
        return partnerId;
    }

    /**
     * @param partnerId the partnerId to set
     */
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    /**
     * @return the providerId
     */
    public Long getProviderId() {
        return providerId;
    }

    /**
     * @param providerId the providerId to set
     */
    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    /**
     * @return the providerName
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * @param providerName the providerName to set
     */
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    /**
     * @return the productSource
     */
    public Integer getProductSource() {
        return productSource;
    }

    /**
     * @param productSource the productSource to set
     */
    public void setProductSource(Integer productSource) {
        this.productSource = productSource;
    }

    /**
     * @return the buyDateStart
     */
    public Date getBuyDateStart() {
        return buyDateStart;
    }

    /**
     * @param buyDateStart the buyDateStart to set
     */
    public void setBuyDateStart(Date buyDateStart) {
        this.buyDateStart = buyDateStart;
    }

    /**
     * @return the buyDateEnd
     */
    public Date getBuyDateEnd() {
        return buyDateEnd;
    }

    /**
     * @param buyDateEnd the buyDateEnd to set
     */
    public void setBuyDateEnd(Date buyDateEnd) {
        this.buyDateEnd = buyDateEnd;
    }

    /**
     * @return the dateStart
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * @param dateStart the dateStart to set
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * @return the dateEnd
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * @param dateEnd the dateEnd to set
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * @return the isCombined
     */
    public Integer getIsCombined() {
        return isCombined;
    }

    /**
     * @param isCombined the isCombined to set
     */
    public void setIsCombined(Integer isCombined) {
        this.isCombined = isCombined;
    }

    /**
     * @return the offshelfTime
     */
    public Date getOffshelfTime() {
        return offshelfTime;
    }

    /**
     * @param offshelfTime the offshelfTime to set
     */
    public void setOffshelfTime(Date offshelfTime) {
        this.offshelfTime = offshelfTime;
    }

    /**
     * @return the selloffshelfFlag
     */
    public Integer getSelloffshelfFlag() {
        return selloffshelfFlag;
    }

    /**
     * @param selloffshelfFlag the selloffshelfFlag to set
     */
    public void setSelloffshelfFlag(Integer selloffshelfFlag) {
        this.selloffshelfFlag = selloffshelfFlag;
    }

    /**
     * @return the offshelfFlag
     */
    public Integer getOffshelfFlag() {
        return offshelfFlag;
    }

    /**
     * @param offshelfFlag the offshelfFlag to set
     */
    public void setOffshelfFlag(Integer offshelfFlag) {
        this.offshelfFlag = offshelfFlag;
    }

    /**
     * @return the manualFlag
     */
    public Integer getManualFlag() {
        return manualFlag;
    }

    /**
     * @param manualFlag the manualFlag to set
     */
    public void setManualFlag(Integer manualFlag) {
        this.manualFlag = manualFlag;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getDopNo() {
		return dopNo;
	}

	public void setDopNo(String dopNo) {
		this.dopNo = dopNo;
	}
}
