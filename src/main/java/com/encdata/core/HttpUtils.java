package com.encdata.core;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author: Siwei Jin
 * @Date: 2019/7/30 13:48
 * @Description: 定制化接口调用工具 post 和 get
 * Attention 所有方法时间参数单位为 秒
 */
public class HttpUtils {

    private static final String POST = "POST";

    private static final String GET = "GET";

    private static final int CONNECT_TIMEOUT = 10;//单位秒

    /**
     * @Description: 通过post方式调用接口
     * @Param url 地址
     * @Pram jsonBody 传递的参数
     * @Param timeout 超时时间
     * @return String
     */
    public static String post(String url, String jsonBody, int timeout){
        HttpURLConnection connection = null;
        DataOutputStream dataOutputStream = null;
        InputStreamReader reader = null;
        try {
            URL realUrl = new URL(url);
            connection = (HttpURLConnection) realUrl.openConnection();
            // 处理消息头
            processHttpHeader(connection, POST, timeout);
            connection.connect();
            // 传参
            dataOutputStream = new DataOutputStream(connection.getOutputStream()) ;
            dataOutputStream.write(jsonBody.getBytes("UTF-8"));
            dataOutputStream.flush();
            // result
            return resolveResult(connection, reader, url);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection(connection, dataOutputStream, reader);
        }
        return null;
    }

    /**
     * @Description: 通过get方式调用接口
     * @Param url 地址
     * @Pram jsonBody 传递的参数
     * @Param timeout 超时时间
     * @return String
     */
    public static String get(String url, int timeout){
        HttpURLConnection connection = null;
        InputStreamReader reader = null;
        try {
            connection = (HttpURLConnection)getConnection(new URL(url));
            // 处理消息头
            processHttpHeader(connection, GET, timeout);
            // 连接
            connection.connect();
            // result
            return resolveResult(connection, reader, url);
        } catch (Exception e) {
//            e.printStackTrace();
        }finally {
            closeConnection(connection, null, reader);
        }
        return null;
    }

    //处理消息头
    private static void processHttpHeader(HttpURLConnection connection, String method, int timeout) throws ProtocolException {
        // 请求的与实体对应的MIME信息
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        // 浏览器可以接受的字符编码集
        connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");
        // 表示是否需要持久连接
        connection.setRequestProperty("connection", "Keep-Alive");
        // 连接超时时间
        connection.setConnectTimeout(CONNECT_TIMEOUT * 1000);

        connection.setRequestMethod(method);
        connection.setConnectTimeout(timeout);
        connection.setDefaultUseCaches(false);
        connection.setDoInput(true);//可以输入
        connection.setDoOutput(true);//接受输出
        connection.setReadTimeout(timeout * 1000);
    }

    //关闭连接
    private static synchronized  void closeConnection(HttpURLConnection connection, DataOutputStream outputStream, InputStreamReader reader){
        int i = 0;
        try {
            if(outputStream != null){
                outputStream.close();
            }
            if(reader != null){
                reader.close();
            }
            if(connection != null){
                connection.disconnect();
            }
        }catch (Exception e){
            e.printStackTrace();
            if(++i < 3){
                closeConnection(connection, outputStream, reader);
            }
        }
    }

    //获取连接
    private static URLConnection getConnection(URL url) throws IOException {
        return url.openConnection();
    }

    //处理返回结果
    private static String resolveResult(HttpURLConnection connection, InputStreamReader reader, String url) throws IOException {
        int code = connection.getResponseCode();
        if(code != 200){
            throw new RuntimeException("http request StatusCode("+ code +") invalid. for url : " + url);
        }
        reader = new InputStreamReader(connection.getInputStream());
        BufferedReader bf = new BufferedReader(reader);
        StringBuilder ret = new StringBuilder(200);
        String line = "";
        while(!StringUtils.isEmpty(line = bf.readLine())){
            ret.append(line);
        }
        return ret.toString();
    }

//    public static void main(String[] args){
//        Map<String, String> param = Maps.newHashMap();
//        param.put("userId", "112233445BCM");
//        param.put("appId", "DAM");
//        System.out.println(HttpUtils.post("http://10.19.248.200:32470/BCM/skin/query", JsonUtils.toJson(param), 100));
//
//        System.out.println(HttpUtils.get("http://10.19.248.200:32470/BCM/skin/query",  100));
//    }
}
