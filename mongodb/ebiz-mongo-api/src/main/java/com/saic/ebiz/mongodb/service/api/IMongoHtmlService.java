/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: IHtmlService.java
 * Author:   v_hudong
 * Date:     2014年7月14日 上午9:43:22
 * Description: //页面静态化服务      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.service.api;

import java.util.List;

import com.meidusa.venus.annotations.Endpoint;
import com.meidusa.venus.annotations.Param;
import com.meidusa.venus.annotations.Service;
import com.saic.ebiz.mongodb.utils.MongoEbizException;
import com.saic.ebiz.mongodb.vo.snapshot.SnapShotVO;

/**
 * 〈页面静态化服务〉<br>
 * 〈根据数据和页面模板生存静态化页面服务〉
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service(name = "MongoHtmlService", version = 1, description = "静态化页面生成")
public interface IMongoHtmlService {

    /**
     * 
     * 功能描述: <br>
     * 〈根据模板编码、html生成目录、对象类型、对象生成静态化页面〉 1、根据模板编码从ftldocument抽取出ftl模板 2、根据objectType,data抓取对象
     * 3、freemarker根据模板和对象生成html到对应的目录中
     * 
     * @param templateNo 模板编码
     * @param ftlBase64String ftl文件base64字符
     * @param htmlFilePath html生成目录
     * @param data 数据
     * @return String: 文件ID
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "buildHtml", async = false)
    String buildHtml(@Param(name = "ftlBase64String") String ftlBase64String, @Param(name = "data") Object data)
            throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据展示模板编码刷新在架商品静态页面〉
     * 
     * @param templateNo 展现模板
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "refreshMdseHtmlByTemplateNo", async = false)
    boolean refreshMdseHtmlByTemplateNo(@Param(name = "templateNo") String templateNo) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据商品批次ID生成该批次的商品详情页〉
     * 
     * @param batchId 批次ID
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "buildHtmlByMdseBatchId", async = false)
    boolean buildHtmlByMdseBatchId(@Param(name = "batchId") Long batchId) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈根据商品批次ID生成该批次的商品详情页〉
     * 
     * @param 商品 ID
     * @return boolean: true为成功；false为失败
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "buildHtmlByMdseId", async = false)
    boolean buildHtmlByMdseId(@Param(name = "mdseId") Long mdseId) throws MongoEbizException;

    /**
     * 
     * 功能描述: <br>
     * 〈查询商品快照对应关系〉
     * 
     * @param snapshotList {@link SnapShotVO}
     * @return
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "getSnapShotList", async = false)
    List<SnapShotVO> getSnapShotList(@Param(name = "snapShotList") List<SnapShotVO> snapShotList)
            throws MongoEbizException;
}