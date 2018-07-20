/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.libcommon;

/**
 * Tuple generator class
 *
 * @author XXXX
 */
public class Tuple {

    /**
     * Generate Tuple2 instance
     *
     * @param <T1>
     * @param <T2>
     * @param t1
     * @param t2
     * @return
     */
    public static <T1, T2> Tuple2<T1, T2> newTuple(T1 t1, T2 t2) {
        Tuple2<T1, T2> val = new Tuple2<>();
        val.setItem1(t1);
        val.setItem2(t2);
        return val;
    }

    /**
     * Generate Tuple3 instance
     *
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param t1
     * @param t2
     * @param t3
     * @return
     */
    public static <T1, T2, T3> Tuple3<T1, T2, T3> newTuple(T1 t1, T2 t2, T3 t3) {
        Tuple3<T1, T2, T3> val = new Tuple3<>();
        val.setItem1(t1);
        val.setItem2(t2);
        val.setItem3(t3);
        return val;
    }

    /**
     * Generate Tuple4 instance
     *
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param t1
     * @param t2
     * @param t3
     * @param t4
     * @return
     */
    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> newTuple(T1 t1, T2 t2, T3 t3, T4 t4) {
        Tuple4<T1, T2, T3, T4> val = new Tuple4<>();
        val.setItem1(t1);
        val.setItem2(t2);
        val.setItem3(t3);
        val.setItem4(t4);
        return val;
    }

}
