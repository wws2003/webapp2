/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.config;

/**
 * Qualifier names
 *
 * @author trungpt
 */
public class CommonQualifierConstant {

    public static class TransactionExecutor {

        public static final String DEFAULT = "defaultTransactionalExecutor";

        public static final String DEFAULT_READONLY = "defaultReadOnlyTransactionalExecutor";

        public static final String NEW = "newTransactionalExecutor";

        public static final String NEW_READONLY = "newReadOnlyTransactionalExecutor";

        public static final String CURRENT = "currentTransactionalExecutor";

        public static final String CURRENT_READONLY = "currentReadOnlyTransactionalExecutor";

    }
}
