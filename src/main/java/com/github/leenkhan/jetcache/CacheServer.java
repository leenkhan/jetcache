package com.github.leenkhan.jetcache;

import com.github.leenkhan.jetcache.server.http.JetServer;

import java.io.IOException;

public class CacheServer {

    public static void main(String[] args){
        System.out.println("server start ...");
        JetServer server = new JetServer();
        try {
            server.start();
            System.out.println("server start success!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
