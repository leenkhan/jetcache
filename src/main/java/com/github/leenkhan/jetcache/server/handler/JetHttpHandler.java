package com.github.leenkhan.jetcache.server.handler;

import com.github.leenkhan.jetcache.cache.CacheContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class JetHttpHandler implements HttpHandler {

    private CacheContext cacheContext;

    public JetHttpHandler(CacheContext cacheContext){
        this.cacheContext = cacheContext;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("jetcache is work!\n");
        sb.append("cache key count:"+ cacheContext.count());
        exchange.sendResponseHeaders(200, 0);
        OutputStream os = exchange.getResponseBody();
        String response = sb.toString();
        os.write(response.getBytes());
        os.close();
    }
}
