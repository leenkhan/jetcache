package com.github.leenkhan.jetcache.server.handler;

import com.github.leenkhan.jetcache.cache.CacheContext;
import com.github.leenkhan.jetcache.constants.Constants;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

public class CacheAccessHandler implements HttpHandler {

    private CacheContext cacheContext;

    public CacheAccessHandler(CacheContext cacheContext){
        this.cacheContext = cacheContext;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod();
        if (requestMethod !=null && requestMethod.length()>2) {
            String method = requestMethod.toUpperCase();
            Headers requestHeaders = httpExchange.getRequestHeaders();
            String key = null;
            if(!requestHeaders.containsKey(Constants.HTTP_ACCESS_HEADER_KEY_NAME)){
                writeReturn(httpExchange, "paramter error");
                return;
            }
            List values = requestHeaders.get(Constants.HTTP_ACCESS_HEADER_KEY_NAME);
            key = values.toString();
            if(key == null || key.length()<1){
                writeReturn(httpExchange, "key is null");
                return;
            }

            InputStream inputStream = httpExchange.getRequestBody();
            String body = IOUtils.toString(inputStream, Charset.forName("UTF-8"));
            switch (method){
                case "GET":
                    Object v = cacheContext.get(key);
                    if(v==null){
                        writeValueIsNull(httpExchange);
                    }else {
                        writeReturn(httpExchange, v.toString());
                    }
                    break;
                case "POST":
                    cacheContext.put(key, body);
                    writeReturn(httpExchange, "ok");
                    break;
                case "DELETE":
                    cacheContext.remove(key);
                    writeReturn(httpExchange, "ok");
                    break;
            }
            System.out.println(String.format("request method:%s, key:%s",method, key ));
        }else {
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            String response = "not suppose this method";
            os.write(response.getBytes());
            os.close();
        }
    }

    void writeReturn(HttpExchange httpExchange, String msg) throws  IOException{
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        String response = msg;
        os.write(response.getBytes());
        os.close();
    }

    void writeValueIsNull(HttpExchange httpExchange) throws IOException{
        httpExchange.sendResponseHeaders(404, 0);
        OutputStream os = httpExchange.getResponseBody();
        String response = "value is not exist";
        os.write(response.getBytes());
        os.close();
    }
}
