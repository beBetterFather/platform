package com.encdata.core;

import java.io.*;
import java.util.Properties;

/**
 * @Author: jinsiwei
 * @Date: 2019/7/29 14:13
 * @Description: 向本地磁盘持久化文件
 */
public class LocalDiskCacheUtils {

    private static final String FILE_PREFIX = "file:";

    private static final String CLASSPATH_PREFIX = "classpath:";

    /**
     * @Description 加载缓存文件
     * @param propertyFileName
     * @return
     */
    public static Properties loadProp(String propertyFileName){

        if(StringUtils.isEmpty(propertyFileName)){
            return  null;
        }

        Properties properties = new Properties();
        if(propertyFileName.startsWith(FILE_PREFIX)){
            //文件路径读取数据
            String absolutePath = propertyFileName.substring(FILE_PREFIX.length());
            File file = new File(absolutePath);
            if(!file.exists()){
                return null;
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                properties.load(new InputStreamReader(fileInputStream, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(propertyFileName.startsWith(CLASSPATH_PREFIX)){
            //类路径读取
            try {
                InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFileName.substring(CLASSPATH_PREFIX.length()));
                properties.load(new InputStreamReader(inputStream, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    //向指定路径写入数据
    public static boolean writeProp(Properties properties, String filePathName){
        FileOutputStream fileOutputStream = null;

        File file = new File(filePathName);
        if(!file.exists()){
            file.getParentFile().mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(file, false);
//            properties.store(fileOutputStream, null);
            properties.store(new OutputStreamWriter(fileOutputStream, "UTF-8"), null);//Writer UTF-8编码直接是字符
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
//        Properties properties = new Properties();
//        properties.setProperty("a", "中国人");
//        LocalDiskCacheUtils.writeProp(properties, "/data/applogs/xxl-conf/confdata/dev/a.properties");
        Properties properties = LocalDiskCacheUtils.loadProp("file:/data/applogs/xxl-conf/confdata/dev/a.properties");
        System.out.println(properties.get("a"));
    }
}
