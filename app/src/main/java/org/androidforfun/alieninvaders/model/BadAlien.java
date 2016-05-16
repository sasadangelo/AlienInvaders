package org.androidforfun.alieninvaders.model;

/**
 * Created by Administrator on 2/1/2016.
 */
public class BadAlien extends Alien {
    public BadAlien(int x, int y) {
        super(x, y);
    }

    public int getScore() {
        return 20;
    }
}
