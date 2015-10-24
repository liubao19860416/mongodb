/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: MdseInfo.java
 * Author:   v_hudong
 * Date:     2014年7月10日 下午3:32:03
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.basebean;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * 〈一句话功能简述〉<br>
 * 商品信息
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MdseInfo implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = 5760389589529437445L;

    /** id 列表+详情 */
    @Indexed
    private Long mdseId;

    /** 品牌ID */
    private Long brandId;

    /** 商品品牌英文名称 */
    private String enName;

    /** 商品品牌中文名称 */
    private String chName;
    
    /** 供应方式：空或0：自提 1 ：配送 */
    private String supplyMode; 

    /** 商品名称 列表+详情 */
    private String mdseName;

    /** 商品卖点 */
    private String sellingPoint;

    /** 商品详情 */
    private String richDetail;

    /** 市场价 */
    private BigDecimal marketPrice;

    /** 属性组ID */
    private Long attrGrpId;
    
    /** 属性组名称 */
    private String attrGrpName;
    
	/** 展现模板编码 */
    private String displayTpltNo;

    /** 列表图 默认为主图 列表+详情 */
    private String mainImage;

    /** 版本 */
    private String version;

    /** 图片清单 */
    private List<MdseImage> images;

    /** 商品扩展属性 */
    private List<MdseExtAttribute> attributes;

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
     * @return the brandId
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * @param brandId the brandId to set
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * @return the enName
     */
    public String getEnName() {
        return enName;
    }

    /**
     * @param enName the enName to set
     */
    public void setEnName(String enName) {
        this.enName = enName;
    }

    /**
     * @return the chName
     */
    public String getChName() {
        return chName;
    }

    /**
     * @param chName the chName to set
     */
    public void setChName(String chName) {
        this.chName = chName;
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
     * @return the sellingPoint
     */
    public String getSellingPoint() {
        return sellingPoint;
    }

    /**
     * @param sellingPoint the sellingPoint to set
     */
    public void setSellingPoint(String sellingPoint) {
        this.sellingPoint = sellingPoint;
    }

    /**
     * @return the richDetail
     */
    public String getRichDetail() {
        return richDetail;
    }

    /**
     * @param richDetail the richDetail to set
     */
    public void setRichDetail(String richDetail) {
        this.richDetail = richDetail;
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
     * @return the attrGrpId
     */
    public Long getAttrGrpId() {
        return attrGrpId;
    }

    /**
     * @param attrGrpId the attrGrpId to set
     */
    public void setAttrGrpId(Long attrGrpId) {
        this.attrGrpId = attrGrpId;
    }

    /**
     * @return the displayTpltNo
     */
    public String getDisplayTpltNo() {
        return displayTpltNo;
    }

    /**
     * @param displayTpltNo the displayTpltNo to set
     */
    public void setDisplayTpltNo(String displayTpltNo) {
        this.displayTpltNo = displayTpltNo;
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
     * @return the images
     */
    public List<MdseImage> getImages() {
        return images;
    }

    /**
     * @param images the images to set
     */
    public void setImages(List<MdseImage> images) {
        this.images = images;
    }

    /**
     * @return the attributes
     */
    public List<MdseExtAttribute> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(List<MdseExtAttribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
    
    /**
     * @return the AttrGrpName
     */
    public String getAttrGrpName() {
		return attrGrpName;
	}

    /**
     * @param set AttrGrpName
     */
	public void setAttrGrpName(String attrGrpName) {
		this.attrGrpName = attrGrpName;
	}

    public String getSupplyMode() {
 		return supplyMode;
 	}
 
 	public void setSupplyMode(String supplyMode) {
 		this.supplyMode = supplyMode;
 	}

}
