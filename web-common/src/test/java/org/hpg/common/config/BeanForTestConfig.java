/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.config;

import org.hpg.common.service.IProjectTransactionServiceForTest;
import org.hpg.common.service.ITransactionServiceForTest;
import org.hpg.common.service.ProjectTransactionServiceForTestImpl;
import org.hpg.common.service.TransactionServiceForTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author trungpt
 */
@Configuration
public class BeanForTestConfig {

    @Bean
    public ITransactionServiceForTest getTransactionServiceForTest() {
        return new TransactionServiceForTest();
    }

    @Bean
    public IProjectTransactionServiceForTest getProjectTransactionServiceForTest() {
        return new ProjectTransactionServiceForTestImpl();
    }
}
