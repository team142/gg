/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.utils;

import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.messages.outgoing.stats.MessagePlayerJoinStats;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author just1689
 */
public class Reporter {

    private static final Executor REPORT_THREAD_POOL = Executors.newFixedThreadPool(2, (Runnable r) -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    public static void report() {
        Reporter.REPORT_THREAD_POOL.execute(() -> reportNewPlayerForStats());

    }

    private static void reportNewPlayerForStats() {
        if (Server.REPORT_STATS) {
            String json = JsonUtils.toJson(new MessagePlayerJoinStats(Server.SERVER_NAME));
            HttpUtils.postSilently(Server.REPORT_URL, json);
        }
    }

}
