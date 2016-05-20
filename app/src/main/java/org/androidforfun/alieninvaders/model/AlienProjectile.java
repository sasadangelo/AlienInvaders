package org.androidforfun.alieninvaders.model;

/**
 * Created by Administrator on 2/1/2016.
 */
public class AlienProjectile extends Projectile {
    public AlienProjectile(int x, int y) {
        super(x, y);
    }

    public void move() {
        moveBy(0, 1);
        //y += 1;
        //distanceTraveled += 1;
        //if (distanceTraveled > maxDistance)
        //    kill();
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
