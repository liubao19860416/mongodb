package com.saic.ebiz.mongodb;

import com.meidusa.toolkit.common.runtime.Application;
import com.meidusa.toolkit.common.runtime.ApplicationConfig;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MongoDBApplicationServer extends Application<ApplicationConfig> {

    @Override
    public void doRun() {

    }

    @Override
    public ApplicationConfig getApplicationConfig() {
        return null;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] { "classpath:applicationContext-mongo.xml" };
    }

    /**
     * 
     * 功能描述: <br>
     * main 方法
     * 
     * @param args 入参
     * @see 无
     * @since 无
     */
    public static void main(String[] args) {
        System.setProperty(ApplicationConfig.PROJECT_MAINCLASS, MongoDBApplicationServer.class.getName());
        Application.main(args);
    }
}
