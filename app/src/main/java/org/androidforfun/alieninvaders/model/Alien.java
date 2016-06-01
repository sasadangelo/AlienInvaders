package org.androidforfun.alieninvaders.model;

import org.androidforfun.alieninvaders.framework.Actor;

import java.util.ArrayList;
import java.util.Random;

public abstract class Alien extends Actor {
    private float speedX;
    private long lastShot;
    private ArrayList<Projectile> projectiles;

    public Alien(int x, int y) {
        super(x, y, AlienInvadersWorld.CELL_WIDTH, AlienInvadersWorld.CELL_HEIGHT);
        this.speedX = 1;
        this.lastShot = System.currentTimeMillis() + new Random().nextInt(15);
        this.projectiles = new ArrayList<Projectile>();
    }

    abstract public int getScore();

    void moveLeft() {
        x -= Math.round(speedX);
    }

    void moveRight() {
        x += Math.round(speedX);
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

    public void increaseSpeed() {
        speedX+=(0.01*AlienInvadersWorld.getInstance().getLevel());
    }
}
