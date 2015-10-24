/*
 * Copyright (C), 2013-2014, 上海汽车集团有限公司
 * FileName: MdseBaseMgVO.java
 * Author:   v_xieyingbin
 * Date:     2014年6月23日 下午5:12:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.vo.mdse;

import java.util.List;

import com.saic.ebiz.mongodb.basebean.MdseBatchInfo;
import com.saic.ebiz.mongodb.basebean.MdseSkuChnPrice;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 商品信息接入接口对象
 * 
 * @author v_hudong
 * @see MdseBatchInfo
 * @see MdseSkuChnPrice
 * @since [产品/模块版本] （可选）
 */
public class MdseBatchPackageVO extends MdseBatchInfo implements java.io.Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2077746795564742147L;

    /**
     * 规格渠道信息
     */
    private List<MdseSkuChnPrice> skuPrices;

    /**
     * @return the skuPrices
     */
    public List<MdseSkuChnPrice> getSkuPrices() {
        return skuPrices;
    }

    /**
     * @param skuPrices the skuPrices to set
     */
    public void setSkuPrices(List<MdseSkuChnPrice> skuPrices) {
        this.skuPrices = skuPrices;
    }

}
