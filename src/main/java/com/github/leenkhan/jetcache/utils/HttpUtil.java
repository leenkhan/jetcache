package com.github.leenkhan.jetcache.utils;


import com.alibaba.fastjson.JSONObject;
import com.github.leenkhan.jetcache.constants.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class HttpUtil {

    public Object doGet(String url, String key, String encode){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Object responseObj = null;
        CloseableHttpResponse response = null;
        if(encode == null){
            encode = "utf-8";
        }

        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader(Constants.HTTP_ACCESS_HEADER_KEY_NAME, key);
            response = httpClient.execute(httpGet);
            int stateCode = response.getStatusLine().getStatusCode();
            if(stateCode == 200) {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    String entityStr = EntityUtils.toString(entity, encode);
                    responseObj = JSONObject.parse(entityStr);
                }
            }else if(stateCode == 404){
                responseObj = null;
            }

        }catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO异常");
            e.printStackTrace();
        } finally {
            // 释放连接
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("释放连接出错");
                    e.printStackTrace();
                }
            }
        }
        return responseObj;
    }

    public String doPost(String url, String key, Object object, String encode){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String entityStr = null;
        CloseableHttpResponse response = null;
        if(encode == null){
            encode = "utf-8";
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(Constants.HTTP_ACCESS_HEADER_KEY_NAME, key);

            String stringJson = JSONObject.toJSONString(object, true);

            //组织请求参数
            StringEntity stringEntity = new StringEntity(stringJson, encode);
            httpPost.setEntity(stringEntity);

            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                entityStr = EntityUtils.toString(entity, "UTF-8");
            }
        }catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO异常");
            e.printStackTrace();
        } finally {
            // 释放连接
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("释放连接出错");
                    e.printStackTrace();
                }
            }
        }
        return entityStr;
    }

    public String doDelete(String url, String key, String encode){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String entityStr = null;
        CloseableHttpResponse response = null;
        if(encode == null){
            encode = "utf-8";
        }

        try {
            HttpDelete httpDelete = new HttpDelete(url);
            httpDelete.addHeader(Constants.HTTP_ACCESS_HEADER_KEY_NAME, key);
            response = httpClient.execute(httpDelete);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                entityStr = EntityUtils.toString(entity, encode);
            }
        }catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO异常");
            e.printStackTrace();
        } finally {
            // 释放连接
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("释放连接出错");
                    e.printStackTrace();
                }
            }
        }
        return entityStr;
    }
}
