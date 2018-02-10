/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import com.team142.gg.server.model.mappable.organic.MapTileElement;
import java.util.HashMap;
import lombok.Data;

/**
 *
 * @author just1689
 */
@Data
public class GameMap {

    private final HashMap<String, MapTileElement> TILES;
    private boolean[][][] bitmap;
    private int x, z;

    public GameMap(int x, int z) {
        this.TILES = new HashMap<>();
        this.x = x;
        this.z = z;
        setBitmap(new boolean[x][z][2]);
    }

    public void setTileBitmapMovable(int x, int z, boolean val) {
        this.bitmap[x][z][0] = val;
    }

    public void setTileBitmapShootover(int x, int z, boolean val) {
        this.bitmap[x][z][1] = val;
    }

    public boolean isMovable(int x, int z) {
        if (x >= this.x || x <= 0 || z >= this.z || z < 0) {
            return false;
        }
        return this.bitmap[x][z][0];
    }

    public boolean isShootover(int x, int z) {
        if (x >= this.x || x <= 0 || z >= this.z || z < 0) {
            return false;
        }
        return this.bitmap[x][z][1];
    }

    public boolean isMovable(double x, double z) {
        return isMovable((int) x, (int) z);
    }

    public boolean isShootover(double x, double z) {
        return isShootover((int) x, (int) z);
    }

}
