package com.github.leenkhan.jetcache.client;

import com.github.leenkhan.jetcache.utils.HttpUtil;

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
        return null;
    }

    @Override
    public Object get(String key) {
        HttpUtil httpUtil = new HttpUtil();
        return httpUtil.doGet(getAccessUrl(), key);
    }

    @Override
    public Object remove(String key) {
        return null;
    }

    String getAccessUrl(){
        return httpSchemas + host + ":" + port + ACCESS_URI;
    }

    public static void main(String[] args){
        JetCacheClient client = new JetCacheClient();
        System.out.println("find key:"+ client.get("hello"));
    }
}
