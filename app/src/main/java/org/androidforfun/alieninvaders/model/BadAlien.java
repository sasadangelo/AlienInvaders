package org.androidforfun.alieninvaders.model;

public class BadAlien extends Alien {
    public BadAlien(int x, int y) {
        super(x, y);
    }

    public int getScore() {
        return 20;
    }
}
