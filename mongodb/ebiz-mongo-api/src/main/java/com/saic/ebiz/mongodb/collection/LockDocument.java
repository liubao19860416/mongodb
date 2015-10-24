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

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 锁定信息
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Document(collection = "t_lockdocument")
public class LockDocument implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = -7466166094671502067L;

    /** 模板编号 */
    public static final String FIELD_BUSINESSNO = "businessNo";
    /** 模板文件名 */
    public static final String FIELD_FILENAME = "fileName";
    /** 版本 */
    public static final String FIELD_VERSION = "version";

    /**
     * 文档id
     */
    @Id
    private String id;

    /** businessno */
    private String businessNo;

    /** fileType */
    private String fileType;

    /** filename */
    private String fileName;

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
     * @return the businessNo
     */
    public String getBusinessNo() {
        return businessNo;
    }

    /**
     * @param businessNo the businessNo to set
     */
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
