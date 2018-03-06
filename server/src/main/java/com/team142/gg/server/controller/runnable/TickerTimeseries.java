/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable;

import com.team142.gg.server.model.KillEventTracker;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class TickerTimeseries implements Runnable {

    private static final long SLEEP_TIME_MS = 60000;

    @Override
    public void run() {
        while (true) {
            KillEventTracker.pruneAll();
            try {
                Thread.sleep(SLEEP_TIME_MS);
            } catch (InterruptedException ex) {
                Logger.getLogger(TickerTimeseries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
