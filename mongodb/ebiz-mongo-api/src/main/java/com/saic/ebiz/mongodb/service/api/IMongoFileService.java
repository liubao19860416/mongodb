/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: IMongoFileService.java
 * Author:   v_hudong
 * Date:     2014年6月30日 下午4:06:33
 * Description: mongodb文件信息服务     
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 *   胡冬             2014-07-11            V1.0      功能实现
 */
package com.saic.ebiz.mongodb.service.api;

import com.meidusa.venus.annotations.Endpoint;
import com.meidusa.venus.annotations.Param;
import com.meidusa.venus.annotations.Service;
import com.saic.ebiz.mongodb.utils.MongoEbizException;
import com.saic.ebiz.mongodb.vo.ftl.MongoFileStream;

/**
 * mongodb文件信息服务 <br>
 * mongodb文件保存与读取服务
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service(name = "MongoFileService", version = 1, description = "文件流信息mongo保存")
public interface IMongoFileService {

    /**
     * 
     * 功能描述: <br>
     * 保存文件
     * 
     * @param fileMetadata
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "saveFile", async = false)
    boolean saveFile(@Param(name = "fileMetadata") MongoFileStream fileMetadata) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 根据展现模板编号查询ftl信息
     * 
     * @param businessNo 模板编号
     * @return java.lang.String ftl内容 base64
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "fetchFTLByBusinessNo", async = false)
    String fetchFTLByBusinessNo(@Param(name = "businessNo") String businessNo) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 根据展现模板编号删除ftl信息
     * 
     * @param businessNo 模板编号
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "removeFTLMetaByBusinessNo", async = false)
    boolean removeFTLMetaByBusinessNo(@Param(name = "businessNo") String businessNo) throws MongoEbizException;

}
