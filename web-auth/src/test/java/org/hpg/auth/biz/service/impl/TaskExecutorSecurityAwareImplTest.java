/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author trungpt
 */
public class TaskExecutorSecurityAwareImplTest {

    public TaskExecutorSecurityAwareImplTest() {
    }

    /**
     * Test of start method, of class TaskExecutorSecurityAwareImpl.
     */
    @Test
    public void test() throws InterruptedException {
        System.out.println("start");
        TaskExecutorSecurityAwareImpl instance = new TaskExecutorSecurityAwareImpl();
        // Start
        boolean expResult = true;
        boolean result = instance.start();
        assertEquals(expResult, result);
        // Submit
        String taskKey = "1234";
        instance.submit(taskKey, () -> {
            try {
                System.out.println("Task is submitted and to be processed.......................................");
                // Can not cancel sleeping thread as interrupt is not used !!
                doSomeIOBlockingTask();
                // doCPUIntensiveTask();
                // doSomeSleepTask(); // Cancel OK !?
                System.out.println("Task has been processed.......................................");
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        });

        // A workaround to make sure the task has already started before try to cancel it
        Thread.sleep(1300);

        // Cancel
        boolean toCancel = true;
        if (toCancel) {
            System.out.println("Trying to cancel the task.......................................");
            boolean expCancelResult = false;
            boolean cancelResult = instance.cancelTask(taskKey);
            assertEquals(expCancelResult, cancelResult);
        }
        // Shutdown
        instance.stop();
    }

    private void doSomeSleepTask() throws InterruptedException {
        Thread.sleep(12000);
    }

    private void doSomeIOBlockingTask() throws Exception {
        String ipAddress = "10.12.35.222";
        int port = 8080;

        // An interruptible channel actually
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);
        socketChannel.connect(new InetSocketAddress(ipAddress, port));
        while (!socketChannel.finishConnect()) {
            // Just wait
        }
//
//        // IO blocking -> Not interruptible !?
//        try (Socket socket = new Socket(ipAddress, port)) {
//            socket.connect(new InetSocketAddress(ipAddress, port), 12000);
//
//        } catch (UnknownHostException ex) {
//            System.out.println("Server not found: " + ex.getMessage());
//        } catch (IOException ex) {
//            System.out.println("I/O error: " + ex.getMessage());
//        }
    }

    private void doCPUIntensiveTask() {
        for (long i1 = Long.MIN_VALUE; i1 < Long.MAX_VALUE; i1++) {
            for (long i2 = Long.MIN_VALUE; i2 < Long.MAX_VALUE; i2++) {
                for (long i3 = Long.MIN_VALUE; i3 < Long.MAX_VALUE; i3++) {
                    for (long i4 = Long.MIN_VALUE; i4 < Long.MAX_VALUE; i4++) {
                        for (long i5 = Long.MIN_VALUE; i5 < Long.MAX_VALUE; i5++) {
                            for (long i6 = Long.MIN_VALUE; i6 < Long.MAX_VALUE; i6++) {
                                Collections.sort(IntStream.range(Integer.MIN_VALUE, Integer.MAX_VALUE)
                                        .boxed()
                                        .collect(Collectors.toList()));
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Finish doCPUIntensiveTask");
    }
}
