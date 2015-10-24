/*
 * Copyright (C), 2013-2013, 上海汽车集团股份有限公司
 * FileName: BaseTestVenus.java
 * Author:   v_suding01
 * Date:     2013年11月14日 下午2:26:24
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package mongodb.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_suding01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@ContextConfiguration(locations = { "classpath:applicationContext-mongo-test.xml" })
public class BaseTestVenus extends AbstractTestNGSpringContextTests {
   
}
