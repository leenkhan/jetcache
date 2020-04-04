package com.github.leenkhan.jetcache.server;

import java.io.IOException;

public interface Server {

    public static final String GLOBLE_CACHE_NAME = "jetcache";

    void start() throws IOException;


    void start(String host, int port) throws IOException;
}
