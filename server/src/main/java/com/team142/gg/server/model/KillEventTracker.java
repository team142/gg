/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class KillEventTracker {

    private static final Logger LOG = Logger.getLogger(KillEventTracker.class.getName());

    private static final ConcurrentHashMap<String, LinkedList<Long>> EVENTS = new ConcurrentHashMap<>();
    private static final long EXPIRES_MS = 1 * 60 * 1000;
    private static final long NORMAL_INTEREST_MS = 1 * 30 * 1000;

    public static int killAndReport(Game game, Player killer) {
        String key = getKey(game, killer);
        return pushAndTell(key, NORMAL_INTEREST_MS);
    }

    public static String getKey(Game game, Player killer) {
        return "key." + game.getId() + "." + killer.getId();
    }

    public static void pruneAll() {
        LOG.log(Level.INFO, "PRUNING ALL TIME EVENTS...");
        EVENTS
                .values()
                .forEach(KillEventTracker::pruneList);
    }

    public static void pruneList(LinkedList<Long> list) {
        Iterator<Long> iterator = list.iterator();
        while (iterator.hasNext()) {
            pruneElement(iterator);
        }
    }

    public static void pruneElement(Iterator<Long> iterator) {
        long rightNow = System.currentTimeMillis();
        if (rightNow - EXPIRES_MS > iterator.next()) {
            iterator.remove();
            LOG.log(Level.INFO, "REMOVED element -> timed out...");
        }

    }

    private static synchronized LinkedList<Long> getList(String key) {
        LinkedList<Long> list = EVENTS.get(key);
        if (list == null) {
            list = new LinkedList<>();
            EVENTS.put(key, list);
        }
        return list;
    }

    public static void push(String key) {
        LinkedList<Long> list = getList(key);
        list.add(System.currentTimeMillis());

    }

    public static int count(String key, long interest) {
        int result = 0;
        LinkedList<Long> list = getList(key);
        if (list == null) {
            System.err.println("Could not find EventRepository for: " + key);
            return 0;
        }
        Iterator<Long> iterator = list.iterator();
        long rightNow = System.currentTimeMillis();
        while (iterator.hasNext()) {
            if (rightNow - interest <= iterator.next()) {
                result++;
            }
        }
        return result;

    }

    public static synchronized int pushAndTell(String key, long interest) {
        push(key);
        return count(key, interest);

    }

}
