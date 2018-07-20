/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.libcommon;

import java.io.Serializable;

/**
 * Tuple2 class
 *
 * @author XXXX
 * @param <T1> Component1 type
 * @param <T2> Component2 type
 */
public class Tuple2<T1, T2> implements Serializable {

    private static final long serialVersionUID = 1L;
    private T1 item1;
    private T2 item2;

    public Tuple2() {
    }

    /**
     * Constructor
     *
     * @param item1
     * @param item2
     */
    public Tuple2(T1 item1, T2 item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    /**
     * Get component1
     *
     * @return
     */
    public T1 getItem1() {
        return item1;
    }

    /**
     * Get component2
     *
     * @return
     */
    public T2 getItem2() {
        return item2;
    }

    /**
     * Set component1
     *
     * @param item1
     */
    public void setItem1(T1 item1) {
        this.item1 = item1;
    }

    /**
     * Set component2
     *
     * @param item2
     */
    public void setItem2(T2 item2) {
        this.item2 = item2;
    }
}
