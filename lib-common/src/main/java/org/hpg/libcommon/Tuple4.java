/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.libcommon;

import java.io.Serializable;

/**
 * Tuple4 class
 *
 * @author XXXX
 * @param <T1> Component1 type
 * @param <T2> Component2 type
 * @param <T3> Component3 type
 * @param <T4> Component4 type
 */
public class Tuple4<T1, T2, T3, T4> implements Serializable {

    private static final long serialVersionUID = 1L;
    private T1 item1;
    private T2 item2;
    private T3 item3;
    private T4 item4;

    /**
     * Constructor
     */
    public Tuple4() {
    }

    /**
     * Constructor
     *
     * @param item1
     * @param item2
     * @param item3
     * @param item4
     */
    public Tuple4(T1 item1, T2 item2, T3 item3, T4 item4) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
    }

    /**
     * @return the item1
     */
    public T1 getItem1() {
        return item1;
    }

    /**
     * @param item1 the item1 to set
     */
    public void setItem1(T1 item1) {
        this.item1 = item1;
    }

    /**
     * @return the item2
     */
    public T2 getItem2() {
        return item2;
    }

    /**
     * @param item2 the item2 to set
     */
    public void setItem2(T2 item2) {
        this.item2 = item2;
    }

    /**
     * @return the item3
     */
    public T3 getItem3() {
        return item3;
    }

    /**
     * @param item3 the item3 to set
     */
    public void setItem3(T3 item3) {
        this.item3 = item3;
    }

    /**
     * @return the item4
     */
    public T4 getItem4() {
        return item4;
    }

    /**
     * @param item4 the item4 to set
     */
    public void setItem4(T4 item4) {
        this.item4 = item4;
    }
}
