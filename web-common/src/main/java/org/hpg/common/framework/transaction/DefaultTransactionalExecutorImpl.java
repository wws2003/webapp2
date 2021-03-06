/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework.transaction;

import java.util.function.Function;
import org.springframework.transaction.annotation.Transactional;

/**
 * Execute function in a simple transaction
 *
 * @author trungpt
 */
@Transactional(rollbackFor = Exception.class)
public class DefaultTransactionalExecutorImpl implements ITransactionExecutor {

    // Have to put method body here in order to have Transactional annotation work !"!??
    @Override
    public <T, R> R execute(Function<T, R> func, T input) {
        return func.apply(input);
    }
}
