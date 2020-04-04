package com.github.leenkhan.jetcache.cache;

import com.github.leenkhan.jetcache.utils.DateUint;

import java.util.Hashtable;

public class JetCacheContext<K, V> implements CacheContext<K, V> {

    private  Hashtable<K, V> table = null;

    @Override
    public V put(K key, V value) {
        return table.put(key, value);
    }

    @Override
    public V put(K key, V value, DateUint uint, int expireDate) {
        return null;
    }

    @Override
    public V get(K key) {
        return table.get(key);
    }

    @Override
    public V remove(K key) {
        return table.remove(key);
    }

    @Override
    public long count() {
        return table.size();
    }

    private JetCacheContext(int initCount){
       table =  new Hashtable<>(initCount);
    }

    private JetCacheContext(){
        this(64 * 1024 * 1024);
    }


    public static CacheContext getContext() {
        return new JetCacheContext();
    }
}
