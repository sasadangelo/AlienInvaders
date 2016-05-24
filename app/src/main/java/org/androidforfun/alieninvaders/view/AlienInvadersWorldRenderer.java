package org.androidforfun.alieninvaders.view;

import org.androidforfun.alieninvaders.framework.Graphics;
import org.androidforfun.alieninvaders.framework.Pixmap;
import org.androidforfun.alieninvaders.model.Alien;
import org.androidforfun.alieninvaders.model.AlienInvadersWorld;
import org.androidforfun.alieninvaders.model.BadAlien;
import org.androidforfun.alieninvaders.model.GoodAlien;
import org.androidforfun.alieninvaders.model.Projectile;
import org.androidforfun.alieninvaders.model.Shield;
import org.androidforfun.alieninvaders.model.Ship;
import org.androidforfun.alieninvaders.model.ShipProjectile;
import org.androidforfun.alieninvaders.model.UglyAlien;

public class AlienInvadersWorldRenderer {
    // Each cell is a 4x4 matrix. The reason we need cells is that ship and aliens do move across a
    // cells requires 4 steps in horizontal and 4 in vertical.
    static final int CELL_WIDTH_PIXEL = 5;
    static final int CELL_HEIGHT_PIXEL = 5;

    private int lastAlienXPosition;
    private boolean alienMove=false;

    private Pixmap alienGood;
    private Pixmap alienBad;
    private Pixmap alienUgly;

    private Graphics g;

    public AlienInvadersWorldRenderer(Graphics graphics) {
        alienGood = Assets.alienGood1;
        alienBad = Assets.alienBad1;
        alienUgly = Assets.alienUgly1;
        g=graphics;
    }

    public void draw() {
        AlienInvadersWorld world = AlienInvadersWorld.getInstance();

        Ship ship = world.getShip();
        g.drawPixmap(Assets.ship, ship.getX() * CELL_WIDTH_PIXEL, ship.getY() * CELL_HEIGHT_PIXEL);

        for (Shield shield : AlienInvadersWorld.getInstance().getShields()) {
            if (shield.getSize().equals(Shield.ShieldSize.LARGE)) {
                g.drawPixmap(Assets.shieldLarge, shield.getX()*CELL_WIDTH_PIXEL, shield.getY()*CELL_HEIGHT_PIXEL);
            } else if (shield.getSize().equals(Shield.ShieldSize.MEDIUM)) {
                g.drawPixmap(Assets.shieldMedium, shield.getX()*CELL_WIDTH_PIXEL, shield.getY()*CELL_HEIGHT_PIXEL);
            } else if (shield.getSize().equals(Shield.ShieldSize.SMALL)) {
                g.drawPixmap(Assets.shieldSmall, shield.getX()*CELL_WIDTH_PIXEL, shield.getY()*CELL_HEIGHT_PIXEL);
            }
        }

        if (AlienInvadersWorld.getInstance().getAliens().size()>0 &&
                AlienInvadersWorld.getInstance().getAliens().get(0).getX() != lastAlienXPosition) {
            if (alienMove) {
                alienGood = Assets.alienGood2;
                alienBad = Assets.alienBad2;
                alienUgly = Assets.alienUgly2;
            } else {
                alienGood = Assets.alienGood1;
                alienBad = Assets.alienBad1;
                alienUgly = Assets.alienUgly1;
            }
            alienMove=!alienMove;
            lastAlienXPosition=AlienInvadersWorld.getInstance().getAliens().get(0).getX();
        }

        for (Alien alien : AlienInvadersWorld.getInstance().getAliens()) {
            if (alien instanceof GoodAlien) {
                g.drawPixmap(alienGood, alien.getX()*CELL_WIDTH_PIXEL, alien.getY()*CELL_HEIGHT_PIXEL);
            } else if (alien instanceof BadAlien) {
                g.drawPixmap(alienBad, alien.getX()*CELL_WIDTH_PIXEL, alien.getY()*CELL_HEIGHT_PIXEL);
            } else if (alien instanceof UglyAlien) {
                g.drawPixmap(alienUgly, alien.getX()*CELL_WIDTH_PIXEL, alien.getY()*CELL_HEIGHT_PIXEL);
            }
        }

        for (Projectile projectile : AlienInvadersWorld.getInstance().getProjectiles()) {
            if (projectile instanceof ShipProjectile) {
                g.drawPixmap(Assets.shipProjectile, projectile.getX()*CELL_WIDTH_PIXEL, projectile.getY()*CELL_HEIGHT_PIXEL);
            } else {
                g.drawPixmap(Assets.alienProjectile, projectile.getX()*CELL_WIDTH_PIXEL, projectile.getY()*CELL_HEIGHT_PIXEL);
            }
        }

        for (int i=1; i<ship.getLives();i++) {
            g.drawPixmap(Assets.shipLife, 13*i, 400);
        }
    }
}
