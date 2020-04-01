package com.github.leenkhan.jetcache.cache;

import com.github.leenkhan.jetcache.utils.DateUint;

public interface CacheContext<K,V> {

    V put(K key, V value);

    V put(K key, V value, DateUint uint, int expireDate);

    V get(K key);

    V remove(K key);

}
