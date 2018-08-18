/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.libcommon;

import java.util.List;

/**
 * Class for basic checking operations
 *
 * @author wws2003
 */
public class CH {

    /**
     * Check if array null or empty
     *
     * @param <T>
     * @param arr
     * @return
     */
    public static final <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length == 0;
    }

    /**
     * Check string is empty or null
     *
     * @param str
     * @return
     */
    public static final boolean isEmpty(String str) {
        return (str == null || str.isEmpty());

    }

    /**
     * Check list is empty or null
     *
     * @param <T>
     * @param list
     * @return
     */
    public static final <T> boolean isEmpty(List<T> list) {
        return (list == null || list.isEmpty());
    }
}
