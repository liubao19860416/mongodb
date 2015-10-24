/*
 * Copyright (C), 2014-2014, 上海汽车集团有限公司
 * FileName: MongoConstants.java
 * Author:   v_hudong
 * Date:     2014年05月23日 上午11:39:42
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 〈一句话功能简述〉<br>
 * mongodb的常量类
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public final class MongoConstants {
    /**
     * private constructor
     */
    private MongoConstants() {
    }

    /** 模板文件类型 */
    public static final String FILE_TYPE_FTL = "ftl";
    /** 静态文件后缀 */
    public static final String FILE_TYPE_HTML_SUFFIX = ".html";
    /** 模板文件保存table */
    public static final String COLLECTION_FTL = "t_ftl_collection";
    /** Collection名 */
    public static final String COLLECTION_MDSE = "t_mdsedocument";
    /** 临时Collection名 */
    public static final String COLLECTION_MDSE_TEMP = "t_mdsedocument_temp";
    /** Collection名 */
    public static final String COLLECTION_CATALOG = "t_catalogdocument";
    /** Collection名 */
    public static final String COLLECTION_CATALOG_BACKUP = "t_catalogdocument_backup";
    /** Collection名 */
    public static final String COLLECTION_CATALOG_TEMP = "t_catalogdocument_temp";

    /** 文件类型和collection匹配关系 */
    public static final Map<String, String> COLLECTION_FILETYPE_MAP = new HashMap<String, String>();

    static {
        COLLECTION_FILETYPE_MAP.put(FILE_TYPE_FTL, COLLECTION_FTL);
    }

    /** collection field defination */
    public static final String BATCH_ID = "batchId";
    /** 渠道类型 */
    public static final String CHANNEL_TYPE = "channelType";
    /** 销售目录id */
    public static final String CATALOG_ID = "catalogId";
    /** 批处理状态 */
    public static final String STATUS = "status";
    /** 展现模板编码 */
    public static final String DISPLAY_TPLNO = "displayTpltNo";
    /** 静态文件名分割符号 */
    public static final String FILE_NAME_SEPARATE = "_";
    /** mongodb主键 */
    public static final String PRIMARY_KEY_ID = "_id";

    /** 主批次id */
    public static final String MAIN_BATCH_ID = "mainBatchId";
    /** 主规格渠道id */
    public static final String MAIN_SKUCHN_ID = "mainSkuChnId";
    /** 商品扩展属性 */
    public static final String ATTRIBUTES = "attributes";
    /** 商品id */
    public static final String MDSE_ID = "mdseId";
    /** 数据值 */
    public static final String BATCH_OBJECT = "object";
    /** 数据类型字段 */
    public static final String BATCH_OBJECTTYPE = "objectType";
    /** 规格渠道id */
    public static final String SKUCHN_ID = "skuChnId";
    /** 版本 */
    public static final String MDSE_VERSION = "version";
    /** 快照号 */
    public static final String SNAPSHOT_ID = "snapshotId";
    /** 展现模板编码 */
    public static final String TEMPLATE_NO = "displayTpltNo";
    /** 供应商编号 */
    public static final String PROVIDER_ID = "providerId";
    /** 单个商品状态：已上架*/
    public static final Integer MDSE_STATUS_SHELFED = 4;
    /** 降排序*/
    public static final String DESC_KEY = "DESC";
    /** 升排序*/
    public static final String ASC_KEY = "ASC";

    /** 批处理类别 */
    public static enum BatchType {
        /** 批处理类别 */
        HTML, OTHER
    }

    /** 数据类型 */
    public static enum ObjectType {
        /** 数据类型 */
        BATCHID, SKUCHNID, MDSEID, PAGEID, FTLID
    }

    /** 业务类型 */
    public static enum BusinessType {
        /** 业务类型 */
        MDSEHTML, STATICHTML, FTLHTML
    }

    /** 批处理状态 */
    public static enum BatchStatus {
        /** 批处理状态 */
        INITIAL, PROCESSING, SUCCESS, FAILED
    }

}
