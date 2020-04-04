package com.github.leenkhan.jetcache.client;

public interface CacheClient {

    Object put(String key, Object value);

    Object get(String key);

    Object remove(String key);
}
