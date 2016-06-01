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
