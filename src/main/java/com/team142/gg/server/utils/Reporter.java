/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author just1689
 */
public class Reporter {

    public static final Executor REPORT_THREAD_POOL = Executors.newFixedThreadPool(2, (Runnable r) -> {
        Thread thread = new Thread();
        thread.setDaemon(true);
        return thread;
    });

}
