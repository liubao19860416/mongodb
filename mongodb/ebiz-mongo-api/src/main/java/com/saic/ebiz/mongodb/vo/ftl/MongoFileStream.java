/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: MongoFileStream.java
 * Author:   v_hudong
 * Date:     2014年6月30日 下午4:28:28
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.vo.ftl;

/**
 * 〈mongodb文件存储对象〉<br>
 * mongodb文件存储请求接口参数: 文件类型、业务类型（展现模板编码）、文件名、数据信息base64
 * 
 * @author v_hudong
 * @see com.mongodb.gridfs.GridFSInputFile
 * @since [产品/模块版本] （可选）
 */
public class MongoFileStream implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = 1843725791486813410L;

    /** 文件类型 */
    private String fileType;

    /** 业务编码 如展现模板编号 */
    private String businessNo;

    /** 　文件id　 */
    private String fileName;

    /** 数据流 Base64 */
    private String base64String;

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
     * @return the base64String
     */
    public String getBase64String() {
        return base64String;
    }

    /**
     * @param base64String the base64String to set
     */
    public void setBase64String(String base64String) {
        this.base64String = base64String;
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

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("fileType=");
        stringBuffer.append(getFileType());
        stringBuffer.append(" businessNo=");
        stringBuffer.append(getBusinessNo());
        stringBuffer.append(" fileName=");
        stringBuffer.append(getFileName());
        return stringBuffer.toString();
    }

}
