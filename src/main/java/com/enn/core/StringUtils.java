package com.enn.core;

import java.io.UnsupportedEncodingException;

/**
 * @Author: jinsiwei
 * @Description: 字符串处理工具类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{

    private static final String CHARSET_NAME = "UTF-8";

    private static final char SEPARATOR = '_';
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
        if(str != null){
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 转换为字节数组
     * @param bytes
     * @return
     */
    public static String toString(byte[] bytes){
        try {
            return new String(bytes, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 是否包含字符串
     * @param str 待验证匹配的字符串
     * @param strs 需要验证的字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs) {
        if(str != null){
            for(String target: strs){
                if(str.equals(target)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 如果不为空，则设置值
     * @param target
     * @param source
     */
    public static void setValueIfNotBlank(String target, String source) {
        if(isNotBlank(source)){
            target = source;
        }
    }

    /**
     * 驼峰命名法工具將 "_" 转换成驼峰形式
     * 例如：toCamelCase("hello_world") == "helloWorld"
     */
    public static String toCamelCase(String source){
        //1、判断字符
        if(source == null){
            return null;
        }

        //2、创建一个线程安全的StringBuilder
        StringBuilder sb = new StringBuilder(source.length());

        //3、设置一个用于起到替换作用的标识符
        boolean upperCase = false;

        //4、循环source的每个字符
        for(int i=0; i<source.length(); i++){
            char c = source.charAt(i);
            if(c == SEPARATOR){
                upperCase = true;
            }else if(upperCase){
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            }else{
                sb.append(c);
            }
        }
        //5、返回转换后的字符串
        return sb.toString();
    }

    /**
     * 字符串转换为Double类型
     * @param val
     */
    public static Double toDouble(Object val){
        if (val == null){
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 字符串转换成 Float 类型
     * @param val
     */
    public static Float toFloat(Object val){
        return toDouble(val).floatValue();
    }

    /**
     * 字符串转换成 Long 类型
     * @
     */
    public static Long toLong(Object val){
        return toDouble(val).longValue();
    }

    /**
     * 字符串转换成 Long 类型
     * @
     */
    public static Integer toInteger(Object val){
        return toLong(val).intValue();
    }
}
