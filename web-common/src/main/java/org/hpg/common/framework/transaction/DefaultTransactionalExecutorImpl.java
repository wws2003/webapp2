/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework.transaction;

import org.springframework.transaction.annotation.Transactional;

/**
 * Execute function in a simple transaction
 *
 * @author trungpt
 */
@Transactional(rollbackFor = Exception.class)
public class DefaultTransactionalExecutorImpl implements ITransactionExecutor {
}
