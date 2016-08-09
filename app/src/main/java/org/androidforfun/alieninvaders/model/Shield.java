package org.androidforfun.alieninvaders.model;

import org.androidforfun.framework.Actor;

public class Shield extends Actor  {
    // Large shield is 8x6 boxes
    public final static int LARGE_SHIELD_WIDTH=8;
    public final static int LARGE_SHIELD_HEIGHT=6;
    // Medium shield is 7x5 boxes
    public final static int MEDIUM_SHIELD_WIDTH=7;
    public final static int MEDIUM_SHIELD_HEIGHT=5;
    // Medium shield is 4x3 boxes
    public final static int SMALL_SHIELD_WIDTH=4;
    public final static int SMALL_SHIELD_HEIGHT=3;

    public enum ShieldSize {
        LARGE, MEDIUM, SMALL, NONE;
    }

    private boolean alive;
    private ShieldSize size;

    public ShieldSize getSize() {
        return size;
    }

    public Shield(int x, int y) {
        setBounds(x, y, LARGE_SHIELD_WIDTH, LARGE_SHIELD_HEIGHT);
        this.alive = true;
        this.size = ShieldSize.LARGE;
    }

    public void reduce() {
        switch(size) {
            case LARGE:
                size=ShieldSize.MEDIUM;
                setBounds(x, y, MEDIUM_SHIELD_WIDTH, MEDIUM_SHIELD_HEIGHT);
                break;
            case MEDIUM:
                size=ShieldSize.SMALL;
                setBounds(x, y, SMALL_SHIELD_WIDTH, SMALL_SHIELD_HEIGHT);
                break;
            case SMALL:
                size=ShieldSize.NONE;
                setBounds(x, y, 0, 0);
                break;
        }
    }

    public boolean isAlive() {
        return size!=ShieldSize.NONE;
    }
}
