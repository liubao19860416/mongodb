/*
 * Copyright (C), 2013-2014, 上汽集团
 * FileName: MongoServiceTest.java
 * Author:   v_hudong
 * Date:     2014年4月8日 下午4:08:59
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package mongodb.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.meidusa.toolkit.common.util.Base64;
import com.saic.ebiz.mongodb.basebean.MdseExtAttribute;
import com.saic.ebiz.mongodb.basebean.MdseImage;
import com.saic.ebiz.mongodb.basebean.MdseSkuChnPrice;
import com.saic.ebiz.mongodb.collection.CatalogDocument;
import com.saic.ebiz.mongodb.collection.MdseDocument;
import com.saic.ebiz.mongodb.service.api.IMongoBatchService;
import com.saic.ebiz.mongodb.service.api.IMongoFileService;
import com.saic.ebiz.mongodb.service.api.IMongoMdseService;
import com.saic.ebiz.mongodb.utils.MongoConstants;
import com.saic.ebiz.mongodb.vo.ftl.MongoFileStream;
import com.saic.ebiz.mongodb.vo.mdse.CatalogMdseVO;
import com.saic.ebiz.mongodb.vo.mdse.MdseBatchPackageVO;


/**
 * 
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MongoServiceTest extends BaseTestVenus {
    
    Logger LOGGER = LoggerFactory.getLogger(MongoServiceTest.class);
    
    
    @Autowired
    IMongoFileService mongoFileService;
    
    @Autowired
    IMongoMdseService mongoMdseService;
    
    @Autowired
    IMongoBatchService mongoBatchService;

//    ////@Test
    public void ftlSaveTest() throws Exception{
        MongoFileStream fileMetadata = new MongoFileStream();
        fileMetadata.setFileName("hotel.ftl");
        fileMetadata.setFileType(MongoConstants.FILE_TYPE_FTL);
        fileMetadata.setBusinessNo("1");
        fileMetadata.setBase64String(Base64.encode(new String("<p>test ftl5555 save</p>").getBytes()));
        mongoFileService.saveFile(fileMetadata);
        LOGGER.error("#######>>>>>>>>>>>>>>>>"+fileMetadata.getBase64String());
        
        String ftlString = mongoFileService.fetchFTLByBusinessNo("1");
        LOGGER.error("#######>>>>>>>>>>>>>>>>"+ftlString);
    }
    
    ////@Test
    public void mdseDocumentSaveTest() throws Exception{
        MdseBatchPackageVO mdseBatchPackageVO =  new MdseBatchPackageVO();
        //
        mdseBatchPackageVO.setAttrGrpId(1L);
        //ext attributes
        List<MdseExtAttribute> attributes = new ArrayList<MdseExtAttribute>();
        for(int i=1;i<10;i++){
            MdseExtAttribute attribute = new MdseExtAttribute();
            attribute.setAttrGrpNo("P01");
            attribute.setAttrDefNo("P01A0"+i);
            attribute.setAttributeName("P01NAME"+i);
            attribute.setAttributeType(1);
            attribute.setAttrNo(attribute.getAttrDefNo()+"V0"+i);
            attribute.setDisplayFlag(1);
            attribute.setDisplayName(attribute.getAttributeName());
            attribute.setDisplayOrder(i);
            attribute.setValue("TESTVALUE"+i);
            attribute.setId(Long.valueOf("999"+i));
            attributes.add(attribute);
        }
        mdseBatchPackageVO.setAttributes(attributes);
        //
        mdseBatchPackageVO.setBatchId(9999l);
        mdseBatchPackageVO.setBrandId(1l);
        mdseBatchPackageVO.setBuyDateEnd(new Date());
        mdseBatchPackageVO.setBuyDateStart(DateUtils.addDays(new Date(), -10));
        mdseBatchPackageVO.setChName("测试品牌");
        mdseBatchPackageVO.setDateEnd(DateUtils.addDays(new Date(), 60));
        mdseBatchPackageVO.setDateStart(DateUtils.addDays(new Date(), 30));
        mdseBatchPackageVO.setDisplayTpltNo("1");
        mdseBatchPackageVO.setEnName("test-brand");
        //images
        List<MdseImage> images = new ArrayList<MdseImage>();
        for(int j=1; j<5;j++){
            MdseImage image = new MdseImage();
            image.setChannelType(1);
            image.setDisplayOrder(j);
            image.setIsPrimaryImage(j==1?1:0);
            image.setSourceFullImage("/HDHASKJDHYIUSAJDA32DSADSA/DAHJDAS.jpg");
            images.add(image);
        }
        mdseBatchPackageVO.setImages(images);
        //
        mdseBatchPackageVO.setIsCombined(0);
        mdseBatchPackageVO.setMainImage("/HDHASKJDHYIUSAJDA32DSADSA/DAHJDAS.jpg");
        mdseBatchPackageVO.setManualFlag(0);
        mdseBatchPackageVO.setMarketPrice(BigDecimal.valueOf(99.99));
        mdseBatchPackageVO.setMdseId(9999l);
        mdseBatchPackageVO.setMdseName("胡冬测试的商品");
        mdseBatchPackageVO.setOffshelfFlag(0);
        mdseBatchPackageVO.setProductSource(1);
        mdseBatchPackageVO.setProviderId(11l);
        mdseBatchPackageVO.setProviderName("测试供应商");
        mdseBatchPackageVO.setRichDetail("HDHASKJDHYIUSAJDA32DSADSA");
        mdseBatchPackageVO.setSellingPoint("不卖就后悔！！！！");
        mdseBatchPackageVO.setSelloffshelfFlag(0);
        mdseBatchPackageVO.setStatus(4);
        //
        List<MdseSkuChnPrice> skuPrices =  new ArrayList<MdseSkuChnPrice>();
        for(int z=1;z<=3;z++){
            MdseSkuChnPrice skuChnPrice = new MdseSkuChnPrice();
            skuChnPrice.setSkuChnId(Long.valueOf("11"+z));
            skuChnPrice.setCashPrice(BigDecimal.valueOf(200.2));
            skuChnPrice.setChnType(1);
            skuChnPrice.setInitStock(9999);
            skuChnPrice.setPaymentType(3);
            skuChnPrice.setPlusPrice(BigDecimal.valueOf(z));
            skuChnPrice.setPointPrice(BigDecimal.valueOf(200.2));
            skuChnPrice.setSettlementPrice(BigDecimal.valueOf(180));
            skuChnPrice.setSkuId(Long.valueOf("11"+z));
            skuChnPrice.setSkuName("测试规则"+z);
            skuChnPrice.setSkuPartnerId("2121");
            skuChnPrice.setStock(9999);
            //
            skuPrices.add(skuChnPrice);
        }
        mdseBatchPackageVO.setSkuPrices(skuPrices);
        //保存
        mongoMdseService.mergeMdseDocument(mdseBatchPackageVO );
    }
    
    ////@Test
    public void mdseDocumentBatchIdQueryTest() throws Exception{
        MdseDocument mdseDocument = mongoMdseService.getMdseDocumentBybatchId(9999l,777l, 1);
        LOGGER.error(">>>>>>MdseDocument>>>>>>>>>"+mdseDocument.getMdseName());
        
    }
    
    
    ////@Test
    public void catalogDocumentSaveTest() throws Exception{
        List<CatalogMdseVO> mdseBatchPackageVO =  new ArrayList<CatalogMdseVO>();
        //保存
        mongoMdseService.mergeCatalogDocument(111L,mdseBatchPackageVO);
    }
    
    
    ////@Test
    public void catalogDocumentParaQueryTest() throws Exception{
        Map<String,Object> paraMap =  new HashMap<String,Object>();
        paraMap.put("P01A01", "TESTVALUE1");
        paraMap.put("P01A02", "TESTVALUE2");
        List<CatalogDocument> catalogDocsByParams = mongoMdseService.getCatalogDocsByParams(111l, paraMap);
        LOGGER.error(">>>>>>MdseDocument>>>>>>>>>"+catalogDocsByParams.get(0).getMdseName());
        
    }
    
    ////@Test
    public void buildHtmlBatchTest() throws Exception{
    	mongoBatchService.buildHtmlBatch();        
    }
}