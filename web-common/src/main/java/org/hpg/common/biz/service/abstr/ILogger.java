/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

import java.util.function.Supplier;

/**
 * Interface for logger
 *
 * @author wws2003
 */
public interface ILogger {

    /**
     * Trace log
     *
     * @param messegeSupplier
     */
    public void trace(Supplier<String> messegeSupplier);

    /**
     * Trace log
     *
     * @param message
     */
    public default void trace(String message) {
        trace(() -> message);
    }

    /**
     * Debug log
     *
     * @param messegeSupplier
     */
    public void debug(Supplier<String> messegeSupplier);

    /**
     * Debug log
     *
     * @param message
     */
    public default void debug(String message) {
        debug(() -> message);
    }

    /**
     * Info log
     *
     * @param messegeSupplier
     */
    public void info(Supplier<String> messegeSupplier);

    /**
     * Info log
     *
     * @param message
     */
    public default void info(String message) {
        info(() -> message);
    }

    /**
     * Error log
     *
     * @param messegeSupplier
     */
    public void error(Supplier<String> messegeSupplier);

    /**
     * Error log
     *
     * @param e
     */
    public void error(Exception e);

    /**
     * Error log
     *
     * @param message
     */
    public default void error(String message) {
        info(() -> message);
    }
}
