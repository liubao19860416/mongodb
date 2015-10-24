/*
 * Copyright (C), 2013-2014, 上汽集团
 * FileName: Md5Util.java
 * Author:   v_chenchao
 * Date:     2014年5月30日 下午2:15:28
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.mongodb.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meidusa.toolkit.common.util.Base64;

/**
 * 
 * 〈一句话功能简述〉<br>
 * MD5加密工具
 * 
 * @author v_hudong
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public final class MD5Util {

    /** 日志记录对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);
    /** 生成md5所用的数据和字符 */
    private static final char HEXDIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };
    /**
     * 保存加密信息变量
     */
    private static MessageDigest messagedigest;

    private MD5Util() {

    }

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            LOGGER.error(nsaex.getMessage());
        }
    }

    /**
     * 生成文件的md5校验值
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = inputStream.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        return bufferToHex(messagedigest.digest());
    }

    /**
     * 生成字符串的md5校验值 非base64位
     * 
     * @param s
     * @return
     */
    public static String getMD5String(String s) {
        try {
            return getMD5String(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
        }
        return "";
    }

    /**
     * 生成字符串的md5校验值 非base64位
     * 
     * @param s
     * @return
     */
    public static String getMD5ForBase64Str(String s) {
        byte[] bs = Base64.decode(s);
        return getMD5String(bs);
    }

    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = HEXDIGITS[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = HEXDIGITS[bt & 0xf];// 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
