/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework.transaction;

import java.util.function.Function;

/**
 * No transaction required to execute
 *
 * @author wws2003
 */
public class NoTransactionExecutorImpl implements ITransactionExecutor {

    @Override
    public <T, R> R execute(Function<T, R> func, T input) {
        return func.apply(input);
    }
}
