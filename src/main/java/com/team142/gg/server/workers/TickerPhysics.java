/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.workers;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.workers.base.AbstractTickerWorker;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class TickerPhysics extends AbstractTickerWorker {

    public TickerPhysics(Game game) {
        super(game);
    }

    private void moveThings() {
        //TODO: implement

        try {
            Thread.sleep(20); //Meh
        } catch (InterruptedException ex) {
            Logger.getLogger(TickerPhysics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doTick() {
        moveThings();
    }

}
