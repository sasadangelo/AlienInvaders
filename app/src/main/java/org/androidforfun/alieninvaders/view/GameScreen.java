package org.androidforfun.alieninvaders.view;

import android.util.Log;

import org.androidforfun.alieninvaders.model.AlienInvadersWorld;
import org.androidforfun.alieninvaders.model.Rectangle;
import org.androidforfun.alieninvaders.model.Settings;
import org.androidforfun.alieninvaders.framework.Game;
import org.androidforfun.alieninvaders.framework.Graphics;
import org.androidforfun.alieninvaders.framework.Input.TouchEvent;
import org.androidforfun.alieninvaders.framework.Screen;
import org.androidforfun.alieninvaders.framework.TextStyle;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GameScreen extends Screen {
    private static final String LOG_TAG = "Alien.GameScreen";

    private boolean isShipMovingLeft=false;
    private boolean isShipMovingRight=false;
    private int shipMovingLeftPointer=-1;
    private int shipMovingRightPointer=-1;

    private Map<AlienInvadersWorld.GameState, GameState> states = new EnumMap<AlienInvadersWorld.GameState, GameState>(AlienInvadersWorld.GameState.class);

    private Rectangle pauseButtonBounds;
    private Rectangle leftButtonBounds;
    private Rectangle rightButtonBounds;
    private Rectangle shootButtonBounds;
    private Rectangle xButtonBounds;
    private Rectangle resumeMenuBounds;
    private Rectangle quitMenuBounds;

    private AlienInvadersWorldRenderer renderer;

    private AlienInvadersWorld.WorldListener worldListener;

    public GameScreen(Game game) {
        super(game);
        Log.i(LOG_TAG, "constructor -- begin");
        states.put(AlienInvadersWorld.GameState.Paused, new GamePaused());
        states.put(AlienInvadersWorld.GameState.Ready, new GameReady());
        states.put(AlienInvadersWorld.GameState.Running, new GameRunning());
        states.put(AlienInvadersWorld.GameState.GameOver, new GameOver());

        pauseButtonBounds=new Rectangle(5, 20, 50, 50);
        leftButtonBounds=new Rectangle(30, 425, 50, 50);
        rightButtonBounds=new Rectangle(100, 425, 50, 50);
        shootButtonBounds=new Rectangle(240, 425, 50, 50);
        resumeMenuBounds=new Rectangle(80, 100, 160, 48);
        quitMenuBounds=new Rectangle(80, 148, 160, 48);
        xButtonBounds=new Rectangle(128, 200, 50, 50);

        renderer = new AlienInvadersWorldRenderer(game.getGraphics());

        worldListener=new AlienInvadersWorld.WorldListener() {
            public void explosion() {
                if(Settings.soundEnabled)
                    Assets.explosion.play(1);
            }
            public void laserClash() {
                if(Settings.soundEnabled)
                    Assets.laserClash.play(1);
            }
            public void shieldImpact() {
                if(Settings.soundEnabled)
                    Assets.shieldImpact.play(1);
            }
        };
        AlienInvadersWorld.getInstance().setWorldListener(worldListener);
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
        states.get(AlienInvadersWorld.getInstance().getState()).draw();
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

            int srcX;
            int srcWidth;
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
                            if(!leftButtonBounds.contains(event.x, event.y)) {
                                isShipMovingLeft=false;
                                shipMovingLeftPointer=-1;
                            }
                        }
                        // Finish move on right
                        if (shipMovingRightPointer==event.pointer) {
                            if(!rightButtonBounds.contains(event.x, event.y)) {
                                isShipMovingRight=false;
                                shipMovingRightPointer=-1;
                            }
                        }
                        break;
                    case TouchEvent.TOUCH_DOWN:
                        if(pauseButtonBounds.contains(event.x, event.y)) {
                            if(Settings.soundEnabled)
                                Assets.click.play(1);
                            AlienInvadersWorld.getInstance().setState(AlienInvadersWorld.GameState.Paused);
                            return;
                        }
                        // Move ship on the left
                        if(leftButtonBounds.contains(event.x, event.y)) {
                            isShipMovingLeft=true;
                            shipMovingLeftPointer=event.pointer;
                        }
                        // Move ship on the right
                        if(rightButtonBounds.contains(event.x, event.y)) {
                            isShipMovingRight=true;
                            shipMovingRightPointer=event.pointer;
                        }
                        // Shoot the aliens
                        if(shootButtonBounds.contains(event.x, event.y)) {
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

            g.drawPixmap(Assets.buttons, pauseButtonBounds.getX(), pauseButtonBounds.getY(), 50, 100,
                    pauseButtonBounds.getWidth()+1, pauseButtonBounds.getHeight()+1); // pause button
            g.drawPixmap(Assets.buttons, leftButtonBounds.getX(), leftButtonBounds.getY(), 50, 50,
                    leftButtonBounds.getWidth() + 1, leftButtonBounds.getHeight() + 1); // left button
            g.drawPixmap(Assets.buttons, rightButtonBounds.getX(), rightButtonBounds.getY(), 0, 50,
                    rightButtonBounds.getWidth()+1, rightButtonBounds.getHeight()+1); // right button
            g.drawPixmap(Assets.buttons, shootButtonBounds.getX(), shootButtonBounds.getY(), 0, 200,
                    shootButtonBounds.getWidth() + 1, shootButtonBounds.getHeight() + 1); // down button
            renderer.draw();
            TextStyle style = new TextStyle();
            style.setColor(0xffffffff);
            style.setTextSize(14);
            style.setStyle(TextStyle.Style.BOLD);
            style.setAlign(TextStyle.Align.CENTER);
            g.drawText("Score:", 100, 40, style);
            g.drawText("" + AlienInvadersWorld.getInstance().getScore(), 100, 60, style);
            g.drawText("Highscore:", 200, 40, style);
            g.drawText("" + Settings.highscores[0], 200, 60, style);
            g.drawText("Level:", 300, 40, style);
            g.drawText("" + AlienInvadersWorld.getInstance().getLevel(), 300, 60, style);
        }
    }

    class GamePaused extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "update -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if (resumeMenuBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        AlienInvadersWorld.getInstance().setState(AlienInvadersWorld.GameState.Running);
                        return;
                    }
                    if (quitMenuBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        game.setScreen(new StartScreen(game));
                        return;
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
            g.drawPixmap(Assets.buttons, leftButtonBounds.getX(), leftButtonBounds.getY(), 50, 50,
                    leftButtonBounds.getWidth()+1, leftButtonBounds.getHeight()+1); // left button
            g.drawPixmap(Assets.buttons, rightButtonBounds.getX(), rightButtonBounds.getY(), 0, 50,
                    rightButtonBounds.getWidth()+1, rightButtonBounds.getHeight()+1); // right button
            g.drawPixmap(Assets.buttons, shootButtonBounds.getX(), shootButtonBounds.getY(), 0, 200,
                    shootButtonBounds.getWidth()+1, shootButtonBounds.getHeight()+1); // down button
            renderer.draw();
        }
    }

    class GameOver extends GameState {
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "update -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if (xButtonBounds.contains(event.x, event.y)) {
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
            g.drawPixmap(Assets.buttons, xButtonBounds.getX(), xButtonBounds.getY(), 0, 100,
                    xButtonBounds.getWidth()+1, xButtonBounds.getHeight()+1);
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