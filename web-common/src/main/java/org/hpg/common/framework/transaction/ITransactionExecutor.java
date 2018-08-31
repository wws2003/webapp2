/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework.transaction;

import java.util.function.Function;

/**
 * Execute with transaction considering
 *
 * @author trungpt
 */
public interface ITransactionExecutor {

    /**
     * Execute (default is not transaction needed)
     *
     * @param <T>
     * @param <R>
     * @param func
     * @param input
     * @return
     */
    public <T, R> R execute(Function<T, R> func, T input);
}
