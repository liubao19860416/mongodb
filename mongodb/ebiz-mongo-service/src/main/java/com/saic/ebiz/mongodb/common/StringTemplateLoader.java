/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: StringTemplateLoader.java
 * Author:   v_hudong
 * Date:     2014年7月14日 下午1:20:21
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.common;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import freemarker.cache.TemplateLoader;

/**
 * 〈一句话功能简述〉<br>
 * 〈模板加载器〉
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StringTemplateLoader implements TemplateLoader {

    /** 模板文件内容 */
    private String template;

    public StringTemplateLoader(String template) {
        this.template = template;
        if (template == null) {
            this.template = "";
        }
    }

    public void closeTemplateSource(Object templateSource) throws IOException {
        ((StringReader) templateSource).close();
    }

    public Object findTemplateSource(String name) throws IOException {
        return new StringReader(template);
    }

    public long getLastModified(Object templateSource) {
        return 0;
    }

    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return (Reader) templateSource;
    }

}
