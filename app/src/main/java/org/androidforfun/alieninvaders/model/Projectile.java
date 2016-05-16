package org.androidforfun.alieninvaders.model;

/**
 * Created by Administrator on 2/1/2016.
 */
public abstract class Projectile extends Actor {
    protected boolean active;

    public Projectile(int x, int y) {
        super(x, y);
        this.active = true;
    }

    public abstract void move();
    public void kill() {
        this.active = false;
    }
    public boolean isInactive() {
        return !this.active;
    }
}
