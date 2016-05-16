package org.androidforfun.alieninvaders.model;

public class Ship extends Actor {
    private int lives;
    private long lastShot;
    private long lastMovement;

    public Ship() {
        super(2*AlienInvadersWorld.CELL_WIDTH, 19*AlienInvadersWorld.CELL_HEIGHT,
                AlienInvadersWorld.CELL_WIDTH+1, AlienInvadersWorld.CELL_HEIGHT);
        lastShot = System.currentTimeMillis();
        lives = 3;
    }

    public void moveLeft() {
        if ((System.currentTimeMillis() - lastMovement) > 20) {
            if (x > 8) {
                x -= 1;
            }
            lastMovement=System.currentTimeMillis();
        }
    }

    public void moveRight() {
        if ((System.currentTimeMillis() - lastMovement) > 20) {
            if (x < AlienInvadersWorld.WORLD_WIDTH - 8) {
                x += 1;
            }
            lastMovement=System.currentTimeMillis();
        }
    }

    public void  shoot() {
        if ((System.currentTimeMillis() - lastShot) > 320) {
            AlienInvadersWorld.getInstance().getProjectiles().add(new ShipProjectile(x+2, y));
            lastShot = System.currentTimeMillis();
        }
    }

    public boolean isAlive() {
        return lives > 0;
    }

    public void kill() {
        lives -= 1;
        x=2*AlienInvadersWorld.CELL_WIDTH;
        y=19*AlienInvadersWorld.CELL_HEIGHT;
    }

    public int getLives() {
        return lives;
    }
}
