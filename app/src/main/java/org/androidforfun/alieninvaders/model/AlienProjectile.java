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

public class AlienProjectile extends Projectile {
    public AlienProjectile(int x, int y) {
        super(x, y);
    }

    public void move() {
        moveBy(0, 1);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            if (object instanceof AlienProjectile) {
                return true;
            }
        }
        return false;
    }
}
