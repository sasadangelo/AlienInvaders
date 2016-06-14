package org.androidforfun.alieninvaders.model;

import org.androidforfun.alieninvaders.framework.Actor;

import java.util.Random;

public abstract class Alien extends Actor {
    private long lastShot;

    public Alien(int x, int y) {
        super(x, y, AlienInvadersWorld.CELL_WIDTH, AlienInvadersWorld.CELL_HEIGHT);
        this.lastShot = System.currentTimeMillis() + new Random().nextInt(15);
    }

    abstract public int getScore();

    void moveLeft() {
        x -= 1;
    }

    void moveRight() {
        x += 1;
    }

    public void moveForward() {
        y += 2;
    }

    public Projectile shoot() {
        if ((System.currentTimeMillis() - lastShot) > 320) {
            lastShot = System.currentTimeMillis();
            return new AlienProjectile(x, y);
        }
        return null;
    }
}
