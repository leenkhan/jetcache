package com.github.leenkhan.jetcache.server.http;

import com.github.leenkhan.jetcache.cache.CacheContext;
import com.github.leenkhan.jetcache.cache.JetCacheContext;
import com.github.leenkhan.jetcache.server.Server;
import com.github.leenkhan.jetcache.server.handler.CacheAccessHandler;
import com.github.leenkhan.jetcache.server.handler.JetHttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class JetServer implements Server {

    private String host = "127.0.0.1";
    private int port = 9511;
    @Override
    public void start() throws IOException {

        start(host, port);
    }

    @Override
    public void start(String host, int port) throws IOException {
        CacheContext cacheContext = JetCacheContext.getContext();
        HttpServerProvider provider = HttpServerProvider.provider();
        HttpServer server =provider.createHttpServer(new InetSocketAddress(port), 100);
        server.createContext("/jetcache", new JetHttpHandler(cacheContext));
        server.createContext("/access", new CacheAccessHandler(cacheContext));
        server.start();
    }

}
