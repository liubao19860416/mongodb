/*
 * Copyright (C), 2013-2014, 上汽集团
 * FileName: OrgIsNotFoundException.java
 * Author:   v_suding01
 * Date:     2014年4月18日 下午1:37:26
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.utils;

import com.meidusa.venus.exception.CodedException;
import com.meidusa.venus.exception.ExceptionLevel;
import com.meidusa.venus.exception.VenusExceptionLevel;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_suding01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MongoEbizException extends Exception implements CodedException, VenusExceptionLevel {

    /**
     */
    private static final long serialVersionUID = -1333650208991337087L;

    /**
     * 异常编码
     */
    private int errorCode;

    /**
     * 异常信息对象
     */
    private Exception exception;
    /** 异常信息 */
    private String errorMsg;

    /** 参数错误 */
    public static final Integer MONGO_DEFAULT_ERROR_CODE = 201408;

    /** 默认错误信息 */
    public static final String MONGO_DEFAULT_ERROR_MSG = "ebiz mongodb error!";

    /**
     * 默认构造函数,初始化errorCode和errorMsg
     */
    public MongoEbizException() {
        this.errorCode = MongoEbizException.MONGO_DEFAULT_ERROR_CODE;
    }

    public MongoEbizException(int errorCode, String errrorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errrorMsg;
    }

    /**
     * @param errorCode
     * @param errorMsg
     */
    public MongoEbizException(int errorCode, Exception exception) {
        super();
        this.errorCode = errorCode;
        this.exception = exception;
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the ExceptionLevel.ERROR
     */
    public ExceptionLevel getLevel() {
        return ExceptionLevel.ERROR;
    }

    /**
     * @return the exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * @param exception the exception to set
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * @return the errrorMsg
     */
    public String getErrorMsg() {
        if (errorMsg != null && !"".equals(errorMsg)) {
            return errorMsg;
        } else if (exception != null) {
            return exception.getMessage();
        }
        return "";
    }

    /**
     * @param errrorMsg the errrorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
