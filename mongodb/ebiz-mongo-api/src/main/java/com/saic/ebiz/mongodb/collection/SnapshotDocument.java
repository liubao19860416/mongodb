package com.saic.ebiz.mongodb.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 快照信息
 * 
 * @author v_meizhi
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Document(collection = "t_snapshotdocument")
@CompoundIndexes({ @CompoundIndex(name = "idx_t_snapshot_1", def = "{'skuChnId': 1, 'mdseVersion': -1}") })
public class SnapshotDocument {

    /**
     * 文档id
     */
    @Id
    private String id;

    /**
     * 渠道价格ID
     */
    private Long skuChnId;

    /**
     * 规格ID
     */
    private Long skuId;

    /**
     * 批次ID
     */
    private Long batchId;

    /**
     * 商品ID
     */
    private Long mdseId;

    /**
     * 商品版本
     */
    private String version;

    /**
     * 快照ID
     */
    private String snapshotId;

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
     * @return the skuId
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * @param skuId the skuId to set
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * @return the batchId
     */
    public Long getBatchId() {
        return batchId;
    }

    /**
     * @param batchId the batchId to set
     */
    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

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
     * @return the mdseVersion
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param mdseVersion the mdseVersion to set
     */
    public void setVersion(String version) {
        this.version = version;
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
