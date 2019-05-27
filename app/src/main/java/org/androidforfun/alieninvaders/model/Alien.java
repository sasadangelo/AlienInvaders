/*
 *  Copyright (C) 2016 Salvatore D'Angelo
 *  This file is part of Alien Invaders project.
 *
 *  Alien Invaders is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Alien Invaders is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License.
 */
package org.androidforfun.alieninvaders.model;

import org.androidforfun.framework.Actor;

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
