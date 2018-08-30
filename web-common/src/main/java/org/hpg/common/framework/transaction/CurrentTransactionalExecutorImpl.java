/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework.transaction;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class to execute the processor in current transaction
 *
 * @author trungpt
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
public class CurrentTransactionalExecutorImpl implements ITransactionExecutor {
}
