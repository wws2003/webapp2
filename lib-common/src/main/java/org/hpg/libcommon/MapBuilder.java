/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.libcommon;

import java.util.Map;

/**
 * Simple builder for map
 *
 * @author trungpt
 * @param <K>
 * @param <V>
 */
public class MapBuilder<K, V> {

    private Map<K, V> instance = null;

    /**
     * Create by concrete class name
     *
     * @param insClzName
     */
    private MapBuilder(Map<K, V> instance) {
        this.instance = instance;
    }

    /**
     * Create by concrete class name
     *
     * @param <K>
     * @param <V>
     * @param insClzName
     * @return
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    public static <K, V> MapBuilder<K, V> instance(Class<? extends Map<K, V>> insClzName) throws InstantiationException, IllegalAccessException {
        Map<K, V> instance = insClzName.newInstance();
        return new MapBuilder(instance);
    }

    /**
     * Create by instance
     *
     * @param <K>
     * @param <V>
     * @param instance
     * @return
     */
    public static <K, V> MapBuilder<K, V> instance(Map<K, V> instance) {
        return new MapBuilder(instance);
    }

    /**
     * Add key-val pair (overwrite the val if already exist)
     *
     * @param key
     * @param val
     * @return
     */
    public MapBuilder<K, V> add(K key, V val) {
        instance.put(key, val);
        return this;
    }

    /**
     * Get the result
     *
     * @return
     */
    public Map<K, V> build() {
        return instance;
    }

    /**
     * Clear all content for map
     */
    public void clear() {
        this.instance.clear();
    }
}
