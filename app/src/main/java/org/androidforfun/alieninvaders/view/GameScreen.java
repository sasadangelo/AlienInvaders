package org.androidforfun.alieninvaders.view;

import android.util.Log;

import org.androidforfun.alieninvaders.framework.Pixmap;
import org.androidforfun.alieninvaders.model.Alien;
import org.androidforfun.alieninvaders.model.AlienInvadersWorld;
import org.androidforfun.alieninvaders.model.AlienProjectile;
import org.androidforfun.alieninvaders.model.BadAlien;
import org.androidforfun.alieninvaders.model.GoodAlien;
import org.androidforfun.alieninvaders.model.ShipProjectile;
import org.androidforfun.alieninvaders.model.Ship;
import org.androidforfun.alieninvaders.model.Projectile;
import org.androidforfun.alieninvaders.model.Settings;
import org.androidforfun.alieninvaders.framework.Game;
import org.androidforfun.alieninvaders.framework.Graphics;
import org.androidforfun.alieninvaders.framework.Input.TouchEvent;
import org.androidforfun.alieninvaders.framework.Screen;
import org.androidforfun.alieninvaders.framework.TextStyle;
import org.androidforfun.alieninvaders.model.Shield;
import org.androidforfun.alieninvaders.model.UglyAlien;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GameScreen extends Screen {
    // Each cell is a 4x4 matrix. The reason we need cells is that ship and aliens do move across a
    // cells requires 4 steps in horizontal and 4 in vertical.
    static final int CELL_WIDTH_PIXEL = 5;
    static final int CELL_HEIGHT_PIXEL = 5;
    //static final int CELL_WIDTH_STEP = CELL_WIDTH_PIXEL/AlienInvadersWorld.CELL_WIDTH;
    //static final int CELL_HEIGHT_STEP = CELL_HEIGHT_PIXEL/AlienInvadersWorld.CELL_HEIGHT;

    private static final String LOG_TAG = "Alien.GameScreen";

    private boolean isShipMovingLeft=false;
    private boolean isShipMovingRight=false;
    private int shipMovingLeftPointer=-1;
    private int shipMovingRightPointer=-1;

    private Map<AlienInvadersWorld.GameState, GameState> states = new EnumMap<AlienInvadersWorld.GameState, GameState>(AlienInvadersWorld.GameState.class);

    private int lastAlienXPosition;
    private boolean alienMove=false;
    private long lastUpdateTime;

    Pixmap alienGood;
    Pixmap alienBad;
    Pixmap alienUgly;

    public GameScreen(Game game) {
        super(game);
        Log.i(LOG_TAG, "constructor -- begin");
        states.put(AlienInvadersWorld.GameState.Paused, new GamePaused());
        states.put(AlienInvadersWorld.GameState.Ready, new GameReady());
        states.put(AlienInvadersWorld.GameState.Running, new GameRunning());
        states.put(AlienInvadersWorld.GameState.GameOver, new GameOver());
        alienGood = Assets.alienGood1;
        alienBad = Assets.alienBad1;
        alienUgly = Assets.alienUgly1;
    }

    @Override
    public void update(float deltaTime) {
        Log.i(LOG_TAG, "update -- begin");
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        states.get(AlienInvadersWorld.getInstance().getState()).update(touchEvents, deltaTime);
    }
    
    @Override
    public void draw(float deltaTime) {
        Log.i(LOG_TAG, "draw -- begin");
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.gamescreen, 0, 0);
        //drawAlienInvaders();
        states.get(AlienInvadersWorld.getInstance().getState()).draw();

        //if (AlienInvadersWorld.getInstance().getState() != AlienInvadersWorld.GameState.GameOver) {
        //    TextStyle style = new TextStyle();
        //    style.setColor(0xffffffff);
        //    style.setTextSize(10);
        //    style.setAlign(TextStyle.Align.CENTER);
            //g.drawText("" + AlienInvadersWorld.getInstance().getLevel(), 30 + leftRegion.getX(), 165 + leftRegion.getY(), style);
            //g.drawText("" + AlienInvadersWorld.getInstance().getScore(), 30 + rightRegion.getX(), 265 + rightRegion.getY(), style);
        //}
    }

    private void drawAlienInvaders() {
        Graphics g = game.getGraphics();

        Ship ship = AlienInvadersWorld.getInstance().getShip();
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

    public void drawText(Graphics g, String line, int x, int y) {
        Log.i(LOG_TAG, "drawText -- begin");
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    @Override
    public void pause() {
        Log.i(LOG_TAG, "pause -- begin");
        if(AlienInvadersWorld.getInstance().getState() == AlienInvadersWorld.GameState.Running)
            AlienInvadersWorld.getInstance().setState(AlienInvadersWorld.GameState.Paused);

        if(AlienInvadersWorld.getInstance().getState() == AlienInvadersWorld.GameState.GameOver) {
            Settings.addScore(AlienInvadersWorld.getInstance().getScore());
            Settings.save(game.getFileIO());
        }
    }

    abstract class GameState {
        abstract void update(List<TouchEvent> touchEvents, float deltaTime);
        abstract void draw();
    }

    class GameRunning extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "update -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                switch(event.type) {
                    case TouchEvent.TOUCH_UP:
                        // Finish move on left
                        if (shipMovingLeftPointer==event.pointer) {
                            isShipMovingLeft=false;
                            shipMovingLeftPointer=-1;
                        }
                        // Finish move on right
                        if (shipMovingRightPointer==event.pointer) {
                            isShipMovingRight=false;
                            shipMovingRightPointer=-1;
                        }
                        break;
                    case TouchEvent.TOUCH_DRAGGED:
                        if (shipMovingLeftPointer==event.pointer) {
                            if(event.x < 30 || event.x > 80 || event.y < 425 || event.y >= 475) {
                                isShipMovingLeft=false;
                                shipMovingLeftPointer=-1;
                            }
                        }
                        // Finish move on right
                        if (shipMovingRightPointer==event.pointer) {
                            if(event.x < 100 || event.x >= 150 || event.y < 425 || event.y >= 475) {
                                isShipMovingRight=false;
                                shipMovingRightPointer=-1;
                            }
                        }
                        break;
                    case TouchEvent.TOUCH_DOWN:
                        if(event.x >= 5 && event.x < 55 && event.y >= 20 && event.y < 70) {
                            if(Settings.soundEnabled)
                                Assets.click.play(1);
                            AlienInvadersWorld.getInstance().setState(AlienInvadersWorld.GameState.Paused);
                            return;
                        }
                        // Move ship on the left
                        if(event.x >= 30 && event.x < 80 && event.y >= 425 && event.y < 475) {
                            isShipMovingLeft=true;
                            shipMovingLeftPointer=event.pointer;
                        }
                        // Move ship on the right
                        if(event.x >= 100 && event.x < 150 && event.y >= 425 && event.y < 475) {
                            isShipMovingRight=true;
                            shipMovingRightPointer=event.pointer;
                        }
                        // Shoot the aliens
                        if(event.x >= 240 && event.x < 290 && event.y >= 425 && event.y < 475) {
                            AlienInvadersWorld.getInstance().getShip().shoot();
                            if(Settings.soundEnabled)
                                Assets.laserCanon.play(1);
                        }
                        break;
                }
            }

            if (isShipMovingLeft) {
                AlienInvadersWorld.getInstance().getShip().moveLeft();
            }
            if (isShipMovingRight) {
                AlienInvadersWorld.getInstance().getShip().moveRight();
            }

            AlienInvadersWorld.getInstance().update(deltaTime);
            if (AlienInvadersWorld.getInstance().getState() == AlienInvadersWorld.GameState.GameOver) {
                if(Settings.soundEnabled)
                    Assets.bitten.play(1);
            }
            detectCollisions();
            if (!AlienInvadersWorld.getInstance().getShip().isAlive()) {
                AlienInvadersWorld.getInstance().setState(AlienInvadersWorld.GameState.GameOver);
            }

            if(Settings.soundEnabled)
                if (!Assets.musicInvaders.isPlaying()) {
                    Assets.musicInvaders.setLooping(true);
                    Assets.musicInvaders.play();
                }
        }

        void draw() {
            Log.i(LOG_TAG, "draw -- begin");
            Graphics g = game.getGraphics();

            g.drawPixmap(Assets.buttons, 5, 20, 50, 100, 51, 51); // pause button
            g.drawPixmap(Assets.buttons, 30, 425, 50, 50, 51, 51); // left button
            g.drawPixmap(Assets.buttons, 100, 425, 0, 50, 51, 51); // right button
            g.drawPixmap(Assets.buttons, 240, 425, 0, 150, 51, 51); // down button
            drawAlienInvaders();
            TextStyle style = new TextStyle();
            style.setColor(0xffffffff);
            style.setTextSize(14);
            style.setStyle(TextStyle.Style.BOLD);
            style.setAlign(TextStyle.Align.CENTER);
            g.drawText("Score:", 100, 40, style);
            g.drawText("" + AlienInvadersWorld.getInstance().getScore(), 100, 60, style);
            g.drawText("Highscore:", 200, 40, style);
            g.drawText("" + Settings.highscores[0], 200, 60, style);
        }

        private void detectCollisions() {
            if (AlienInvadersWorld.getInstance().isShipHitAlien()) {
                if(Settings.soundEnabled)
                    Assets.explosion.play(1);
            }

            if (AlienInvadersWorld.getInstance().isShipProjectileHitAlien()) {
                if(Settings.soundEnabled)
                    Assets.laserClash.play(1);
            }

            if (AlienInvadersWorld.getInstance().isAlienProjectileHitShip()) {
                if(Settings.soundEnabled)
                    Assets.explosion.play(1);
            }

            AlienInvadersWorld.getInstance().isProjectilesHit();

            if (AlienInvadersWorld.getInstance().isAlienProjectileHitShield()) {
                if(Settings.soundEnabled)
                    Assets.shieldImpact.play(1);
            }

            AlienInvadersWorld.getInstance().isShipProjectileHitShield();
        }
    }

    class GamePaused extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "update -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if(event.x > 80 && event.x <= 240) {
                        if(event.y > 100 && event.y <= 148) {
                            if(Settings.soundEnabled)
                                Assets.click.play(1);
                            AlienInvadersWorld.getInstance().setState(AlienInvadersWorld.GameState.Running);
                            return;
                        }
                        if(event.y > 148 && event.y < 196) {
                            if(Settings.soundEnabled)
                                Assets.click.play(1);
                            game.setScreen(new StartScreen(game));
                            return;
                        }
                    }
                }
            }
            if(Settings.soundEnabled)
                if (Assets.musicInvaders.isPlaying())
                    Assets.musicInvaders.pause();
        }

        void draw() {
            Log.i(LOG_TAG, "draw -- begin");
            Graphics g = game.getGraphics();
            g.drawPixmap(Assets.pausemenu, 100, 100);
        }
    }

    class GameReady extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "update -- begin");
            if(touchEvents.size() > 0)
                AlienInvadersWorld.getInstance().setState(AlienInvadersWorld.GameState.Running);
        }

        void draw() {
            Log.i(LOG_TAG, "draw -- begin");
            Graphics g = game.getGraphics();

            g.drawPixmap(Assets.readymenu, 65, 60);
            g.drawPixmap(Assets.buttons, 30, 425, 50, 50, 51, 51); // left button
            g.drawPixmap(Assets.buttons, 100, 425, 0, 50, 51, 51); // right button
            g.drawPixmap(Assets.buttons, 240, 425, 0, 150, 51, 51); // down button
            drawAlienInvaders();
        }
    }

    class GameOver extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "update -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if(event.x >= 128 && event.x <= 192 && event.y >= 200 && event.y <= 264) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        game.setScreen(new StartScreen(game));
                        AlienInvadersWorld.getInstance().clear();
                        return;
                    }
                }
            }
            if(Settings.soundEnabled)
                if (Assets.musicInvaders.isPlaying())
                    Assets.musicInvaders.stop();
        }

        void draw() {
            Log.i(LOG_TAG, "drawGameOverUI -- begin");
            Graphics g = game.getGraphics();

            g.drawPixmap(Assets.gameoverscreen, 0, 0);
            g.drawPixmap(Assets.buttons, 128, 200, 0, 100, 51, 51);
            drawText(g, ""+ AlienInvadersWorld.getInstance().getScore(), 180, 280);
        }
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}