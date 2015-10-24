/*
 * Copyright (C), 2013-2014, 上海汽车集团有限公司
 * FileName: MdseImgsVO.java
 * Author:   v_xieyingbin
 * Date:     2014年6月23日 下午1:26:05
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.basebean;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 商品图片
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MdseImage implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = -2102572081818573659L;

    /**
     * 渠道类别
     */
    private Integer channelType;

    /**
     * 原始图地址
     */
    private String sourceFullImage;

    /**
     * 是否主图
     */
    private Integer isPrimaryImage;

    /**
     * 显示顺序
     */
    private Integer displayOrder;

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
     * @return the sourceFullImage
     */
    public String getSourceFullImage() {
        return sourceFullImage;
    }

    /**
     * @param sourceFullImage the sourceFullImage to set
     */
    public void setSourceFullImage(String sourceFullImage) {
        this.sourceFullImage = sourceFullImage;
    }

    /**
     * @return the isPrimaryImage
     */
    public Integer getIsPrimaryImage() {
        return isPrimaryImage;
    }

    /**
     * @param isPrimaryImage the isPrimaryImage to set
     */
    public void setIsPrimaryImage(Integer isPrimaryImage) {
        this.isPrimaryImage = isPrimaryImage;
    }

    /**
     * @return the displayOrder
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    /**
     * @param displayOrder the displayOrder to set
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

}
