/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: CombinedMdseDocument.java
 * Author:   v_hudong
 * Date:     2014年7月17日 上午10:20:44
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.vo.mdse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.saic.ebiz.mongodb.basebean.MdseCmbItem;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CombinedMdseVO implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = -6325166681640338247L;

    /** 组合商品规格渠道ID */
    private Long skuChnId;

    /** 组合商品批次ID */
    private Long batchId;

    /** 渠道类别 */
    private Integer channelType;

    /** 可购买时间（开始） */
    private Date buyDateStart;

    /** 可购买时间（结束） */
    private Date buyDateEnd;

    /** 组合商品积分价格 */
    private BigDecimal pointPrice;

    /** 组合商品现金价格 */
    private BigDecimal cashPrice;
    
    /**组合商品的状态*/
    private Integer status;

    /** 子项信息 */
    private List<MdseCmbItem> itemList;

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
     * @return the itemList
     */
    public List<MdseCmbItem> getItemList() {
        return itemList;
    }

    /**
     * @param itemList the itemList to set
     */
    public void setItemList(List<MdseCmbItem> itemList) {
        this.itemList = itemList;
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
}
