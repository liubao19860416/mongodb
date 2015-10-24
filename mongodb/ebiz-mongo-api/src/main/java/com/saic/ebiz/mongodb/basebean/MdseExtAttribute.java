package com.saic.ebiz.mongodb.basebean;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 商品扩展信息
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MdseExtAttribute implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = -8387042448412942451L;

    /** 　ID */
    private Long id;

    /** 　属性组编码　 */
    private String attrGrpNo;

    /** 　属性编码　　 */
    private String attrDefNo;

    /** 　属性值编码　　 */
    private String attrNo;

    /** 属性名称 */
    private String attributeName;

    /** 显示名称 */
    private String displayName;

    /** 属性值 */
    private String value;

    /** 显示类型 */
    private Integer attributeType;

    /** 显示顺序 */
    private Integer displayOrder;

    /** 显示标志 */
    private Integer displayFlag;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the attrGrpNo
     */
    public String getAttrGrpNo() {
        return attrGrpNo;
    }

    /**
     * @param attrGrpNo the attrGrpNo to set
     */
    public void setAttrGrpNo(String attrGrpNo) {
        this.attrGrpNo = attrGrpNo;
    }

    /**
     * @return the attrDefNo
     */
    public String getAttrDefNo() {
        return attrDefNo;
    }

    /**
     * @param attrDefNo the attrDefNo to set
     */
    public void setAttrDefNo(String attrDefNo) {
        this.attrDefNo = attrDefNo;
    }

    /**
     * @return the attrNo
     */
    public String getAttrNo() {
        return attrNo;
    }

    /**
     * @param attrNo the attrNo to set
     */
    public void setAttrNo(String attrNo) {
        this.attrNo = attrNo;
    }

    /**
     * @return the attributeName
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * @param attributeName the attributeName to set
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the attributeType
     */
    public Integer getAttributeType() {
        return attributeType;
    }

    /**
     * @param attributeType the attributeType to set
     */
    public void setAttributeType(Integer attributeType) {
        this.attributeType = attributeType;
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

    /**
     * @return the displayFlag
     */
    public Integer getDisplayFlag() {
        return displayFlag;
    }

    /**
     * @param displayFlag the displayFlag to set
     */
    public void setDisplayFlag(Integer displayFlag) {
        this.displayFlag = displayFlag;
    }

}
