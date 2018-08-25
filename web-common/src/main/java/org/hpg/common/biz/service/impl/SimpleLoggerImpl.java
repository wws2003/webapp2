/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.hpg.common.biz.service.abstr.ILogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

/**
 * Simplest logger implementation
 *
 * @author wws2003
 */
@PropertySources({
    @PropertySource("classpath:application.properties")
})
public class SimpleLoggerImpl implements ILogger {

    @Autowired
    private Environment environment;

    private static final Logger LOGGER = Logger.getLogger(SimpleLoggerImpl.class.getName());

    private final int maxTraceLevel = environment.getProperty("logger.max-trace-level", Integer.class);

    @Override
    public void trace(Supplier<String> messegeSupplier) {
        // Trace
        String tracingMessageLines = getMessageLinesForStackTrace(Thread.currentThread().getStackTrace(),
                maxTraceLevel);
        LOGGER.log(Level.FINEST, tracingMessageLines);
        // Message
        LOGGER.log(Level.FINEST, messegeSupplier);
    }

    @Override
    public void debug(Supplier<String> messegeSupplier) {
        LOGGER.log(Level.FINER, messegeSupplier);
    }

    @Override
    public void info(Supplier<String> messegeSupplier) {
        LOGGER.log(Level.INFO, messegeSupplier);
    }

    @Override
    public void error(Supplier<String> messegeSupplier) {
        LOGGER.log(Level.SEVERE, messegeSupplier);
    }

    @Override
    public void error(Exception e) {
        // Trace
        String tracingMessageLines = getMessageLinesForStackTrace(e.getStackTrace(),
                maxTraceLevel);
        LOGGER.log(Level.FINEST, tracingMessageLines);
    }

    /**
     * Collect info from stack trace into message lines
     *
     * @param stackTraceElements
     * @param maxTracingLevel
     * @return
     */
    private String getMessageLinesForStackTrace(StackTraceElement[] stackTraceElements, int maxTracingLevel) {
        int actualMaxTraceLevel = Math.min(maxTracingLevel, Optional.ofNullable(stackTraceElements).map(st -> st.length).orElse(0));

        return IntStream.range(0, actualMaxTraceLevel).boxed()
                .map(index -> stackTraceElements[index])
                .map(
                        stackTraceElement -> String.format("%s.%s(): line %d",
                                stackTraceElement.getClassName(),
                                stackTraceElement.getMethodName(),
                                stackTraceElement.getLineNumber())
                )
                .collect(Collectors.joining("¥n"));
    }
}
