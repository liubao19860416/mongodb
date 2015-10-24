/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: SnapShotVO.java
 * Author:   v_hudong
 * Date:     2014年7月22日 下午1:50:37
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.vo.snapshot;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SnapShotVO implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = 1993048854284832916L;

    /**
     * 渠道价格ID
     */
    private Long skuChnId;

    /**
     * 商品版本
     */
    private Integer mdseVersion;

    /**
     * 快照ID
     */
    private String snapshotId;

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
     * @return the mdseVersion
     */
    public Integer getMdseVersion() {
        return mdseVersion;
    }

    /**
     * @param mdseVersion the mdseVersion to set
     */
    public void setMdseVersion(Integer mdseVersion) {
        this.mdseVersion = mdseVersion;
    }

    /**
     * @return the snapshotId
     */
    public String getSnapshotId() {
        return snapshotId;
    }

    /**
     * @param snapshotId the snapshotId to set
     */
    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

}
