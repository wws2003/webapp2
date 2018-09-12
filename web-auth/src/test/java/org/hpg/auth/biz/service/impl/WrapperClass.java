/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

/**
 *
 * @author trungpt
 */
public class WrapperClass {

    public static class In1 {

        public static class In2 {

            public int getVal() {
                return 1;
            }
        }
    }

    public static class In3 {

        public void printSth() {
            System.out.println("In3");
        }
    }

    public void printSth() {
        System.out.println("Who are you");
    }
}
