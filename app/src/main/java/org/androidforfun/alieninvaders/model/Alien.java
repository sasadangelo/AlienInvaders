package org.androidforfun.alieninvaders.model;

import java.util.ArrayList;
import java.util.Random;

public abstract class Alien extends Actor {
    private int speedX, speedY;
    private int startX, startY;
    private int angle;
    private String direction;
    private long lastShot;
    private ArrayList<Projectile> projectiles;

    public Alien(int x, int y) {
        super(x, y, AlienInvadersWorld.CELL_WIDTH, AlienInvadersWorld.CELL_HEIGHT);
        this.speedX = 1;
        this.speedY = 2;
        this.startX = x;
        this.startY = y;
        this.angle = 180;
        this.direction = "left";
        this.lastShot = System.currentTimeMillis() + new Random().nextInt(15);
        this.projectiles = new ArrayList<Projectile>();
    }

    abstract public int getScore();

    public void move() {
        if (direction.equals("left")) {
            moveLeft();
        }
        if (direction.equals("right")) {
            moveRight();
        }
    }

    public void moveLeft() {
        if ((x - startX) < 8) {
            x += speedX;
        } else {
            changeDirection();
        }
    }

    public void moveRight() {
        if ((startX - x) < 8) {
            x -= speedX;
        } else {
            changeDirection();
        }
    }

    public void changeDirection() {
        y += speedY;
        direction = (direction.equals("left")) ? "right" : "left";
    }

    public Projectile shoot() {
        if ((System.currentTimeMillis() - lastShot) > 320) {
            lastShot = System.currentTimeMillis();
            return new AlienProjectile(x, y);
        }
        return null;
    }

    public void increaseSpeed() {
        speedX+=0.1;
        speedY+=0.1;
    }
}
