/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author just1689
 */
public class Server {

    public static final int TICK_MS = 17;
    public static final double DEFAULT_SPEED = 0.125;
    public static final AtomicInteger TAGS = new AtomicInteger(1000);

    public static String SERVER_NAME;
    public static boolean REPORT_STATS;
    public static final String REPORT_URL = "https://us-central1-good-game-192610.cloudfunctions.net/function-newPlayer-v1";

}
