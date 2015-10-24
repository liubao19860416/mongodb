/*
 * Copyright (C), 2013-2014, 上海汽车集团有限公司
 * FileName: BatchDocument.java
 * Author:   v_hudong
 * Date:     2014年6月23日 下午5:12:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.collection;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 批处理信息
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Document(collection = "t_batchdocument")
public class BatchDocument implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = -1683336329657136063L;

    /**
     * 批次类型/ public static final String FIELD_BATCHTYPE = "batchType"; /**批次状态
     */
    public static final String FIELD_STATUS = "status";

    /**
     * 文档id
     */
    @Id
    private String id;

    /** 对象类别 */
    @Indexed
    private String batchType;

    /** 业务类型 */
    private String businessType;

    /** 对象ID */
    private String objectType;

    /** 对象 */
    private String object;

    /** 状态 */
    @Indexed
    private Integer status;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

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
     * @return the batchType
     */
    public String getBatchType() {
        return batchType;
    }

    /**
     * @param batchType the batchType to set
     */
    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    /**
     * @return the businessType
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * @param businessType the businessType to set
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * @return the objectType
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     * @param objectType the objectType to set
     */
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    /**
     * @return the object
     */
    public String getObject() {
        return object;
    }

    /**
     * @param object the object to set
     */
    public void setObject(String object) {
        this.object = object;
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

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
