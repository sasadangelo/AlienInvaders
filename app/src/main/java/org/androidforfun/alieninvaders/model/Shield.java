package org.androidforfun.alieninvaders.model;

/**
 * Created by Administrator on 2/1/2016.
 */
public class Shield {
    // Large shield is 8x6 boxes
    public final static int LARGE_SHIELD_WIDTH=8;
    public final static int LARGE_SHIELD_HEIGHT=6;
    // Medium shield is 7x5 boxes
    public final static int MEDIUM_SHIELD_WIDTH=7;
    public final static int MEDIUM_SHIELD_HEIGHT=5;
    // Medium shield is 4x3 boxes
    public final static int SMALL_SHIELD_WIDTH=4;
    public final static int SMALL_SHIELD_HEIGHT=3;

    public enum ShieldSize {
        LARGE, MEDIUM, SMALL, NONE;
    }

    //public final static int LARGE=0;
    //public final static int MEDIUM=1;
    //public final static int SMALL=2;

    private int x;
    private int y;
    private boolean alive;
    private ShieldSize size;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ShieldSize getSize() {
        return size;
    }

    public Shield(int x, int y) {
        this.x = x;
        this.y = y;
        this.alive = true;
        this.size = ShieldSize.LARGE;
    }

    public boolean hit(Projectile projectile) {
        switch(size) {
            case LARGE:
                if (projectile.getX()>=x && projectile.getX()<=x+LARGE_SHIELD_WIDTH &&
                        projectile.getY()>=y && projectile.getY()<=y+LARGE_SHIELD_HEIGHT)
                    return true;
                return false;
            case MEDIUM:
                if (projectile.getX()>=x && projectile.getX()<=x+MEDIUM_SHIELD_WIDTH &&
                        projectile.getY()>=y && projectile.getY()<=y+MEDIUM_SHIELD_HEIGHT)
                    return true;
                return false;
            case SMALL:
                if (projectile.getX()>=x && projectile.getX()<=x+SMALL_SHIELD_WIDTH &&
                        projectile.getY()>=y && projectile.getY()<=y+SMALL_SHIELD_HEIGHT)
                    return true;
                return false;
        }
        return false;
    }

    public void reduce() {
        switch(size) {
            case LARGE:
                size=ShieldSize.MEDIUM;
                break;
            case MEDIUM:
                size=ShieldSize.SMALL;
                break;
            case SMALL:
                size=ShieldSize.NONE;
                break;
        }
    }

    public boolean isAlive() {
        return size!=ShieldSize.NONE;
    }

    public void hitbox() {
        //hitbox_x = ((@x - @image.width/2).to_i..(@x + @image.width/2.to_i)).to_a
        //        hitbox_y = ((@y - @image.width/2).to_i..(@y + @image.width/2).to_i).to_a
        //{:x => hitbox_x, :y => hitbox_y}
    }
}
