/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.utils;

import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.messages.outgoing.stats.MessageCapacity;
import com.team142.gg.server.model.messages.outgoing.stats.MessageNotifyPushover;
import com.team142.gg.server.model.messages.outgoing.stats.MessagePlayerJoinStats;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author just1689
 */
public class Reporter {

    private static final String PLAYER_JOINS_MESSAGE = "Player joined ";

    private static final Executor REPORT_THREAD_POOL = Executors.newFixedThreadPool(2, (Runnable r) -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    public static void reportJoins(String name) {
        if (Server.REPORT_STATS) {
            Reporter.REPORT_THREAD_POOL.execute(() -> reportNewPlayerForStats());
            Reporter.REPORT_THREAD_POOL.execute(() -> reportDSPlayerCapcity());
        }
        if (Server.NOTIFY_PUSHOVER_ON_JOIN) {
            Reporter.REPORT_THREAD_POOL.execute(() -> reportNewPlayerForPushOver(name));
        }

    }

    public static void reportLeaves() {
        if (Server.REPORT_STATS) {
            Reporter.REPORT_THREAD_POOL.execute(() -> reportDSPlayerCapcity());
        }

    }

    private static void reportDSPlayerCapcity() {
        String json = JsonUtils.toJson(new MessageCapacity());
        HttpUtils.postSilently(Server.REPORT_DS_FUNCTION_URL, json);
    }

    private static void reportNewPlayerForStats() {
        String json = JsonUtils.toJson(new MessagePlayerJoinStats(Server.SERVER_NAME));
        HttpUtils.postSilently(Server.REPORT_URL, json);
    }

    private static void reportNewPlayerForPushOver(String name) {
        String json = JsonUtils.toJson(new MessageNotifyPushover(Server.NOTIFY_PUSHOVER_USER, Server.NOTIFY_PUSHOVER_TOKEN, PLAYER_JOINS_MESSAGE + ": " + name));
        HttpUtils.postSilently(Server.NOTIFY_PUSHOVER_URL, json);
    }

}
