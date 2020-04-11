package com.github.leenkhan.jetcache.client;

import com.github.leenkhan.jetcache.utils.HttpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JetCacheClient implements CacheClient {

    private String httpSchemas = "http://";
    private String host = "127.0.0.1";
    private int port = 9511;

    public  final String INFO_URI = "/jetcache";
    public  final String ACCESS_URI = "/access";

    public JetCacheClient(){

    }

    public JetCacheClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    @Override
    public Object put(String key, Object value) {
        HttpUtil httpUtil = new HttpUtil();
        return httpUtil.doPost(getAccessUrl(), key, value, "UTF-8");
    }

    @Override
    public Object get(String key) {
        HttpUtil httpUtil = new HttpUtil();
        return httpUtil.doGet(getAccessUrl(), key, "UTF-8");
    }

    @Override
    public Object remove(String key) {
        HttpUtil httpUtil = new HttpUtil();
        return httpUtil.doDelete(getAccessUrl(), key, "UTF-8");
    }

    String getAccessUrl(){
        return httpSchemas + host + ":" + port + ACCESS_URI;
    }

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        JetCacheClient client = new JetCacheClient();
        List<String> list = new ArrayList<>();
        for(int i=0;i<10000;i++){
            String key = UUID.randomUUID().toString();
            client.put(key,key);
            list.add(key);
        }
        System.out.println("used time:"+ (System.currentTimeMillis() - start) + "ms");

        for(String key : list){
            System.out.println("key's value:"+client.get(key));
        }
    }
}
