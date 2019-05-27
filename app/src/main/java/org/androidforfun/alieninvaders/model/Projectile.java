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

    public boolean equals(Object object) {
        if (super.equals(object)) {
            if (object instanceof Projectile) {
                Projectile projectile = (Projectile) object;
                if (active==projectile.active) {
                    return true;
                }
            }
        }
        return false;
    }
}
