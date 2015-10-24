/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: IBatchService.java
 * Author:   v_hudong
 * Date:     2014年7月14日 上午9:43:22
 * Description: //页面静态化服务      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.service.api;

import com.meidusa.venus.annotations.Endpoint;
import com.meidusa.venus.annotations.Service;
import com.saic.ebiz.mongodb.utils.MongoEbizException;

/**
 * 〈页面静态化服务〉<br>
 * 〈mongodb批处理服务〉
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service(name = "MongoBatchService", version = 1, description = "批处理服务")
public interface IMongoBatchService {

    /**
     * 
     * 功能描述: <br>
     * 〈静态页面批处理〉
     * 
     * @return
     * @throws MongoEbizException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Endpoint(name = "buildHtmlBatch", async = false)
    boolean buildHtmlBatch() throws MongoEbizException;

}