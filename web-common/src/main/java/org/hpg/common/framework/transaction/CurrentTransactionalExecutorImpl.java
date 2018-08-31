/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework.transaction;

import java.util.function.Function;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class to execute the processor in current transaction
 *
 * @author trungpt
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
public class CurrentTransactionalExecutorImpl implements ITransactionExecutor {

    // Have to put method body here in order to have Transactional annotation work !"!??
    @Override
    public <T, R> R execute(Function<T, R> func, T input) {
        return func.apply(input);
    }
}
