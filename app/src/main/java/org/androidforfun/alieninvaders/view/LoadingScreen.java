package org.androidforfun.alieninvaders.view;

import android.util.Log;
import org.androidforfun.framework.Game;
import org.androidforfun.framework.Graphics;
import org.androidforfun.framework.Graphics.PixmapFormat;
import org.androidforfun.framework.Screen;
import org.androidforfun.alieninvaders.model.Settings;

public class LoadingScreen extends Screen {
    private static final String LOG_TAG = "Invaders.LoadingScreen";
    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Log.i(LOG_TAG, "update -- begin");
        Graphics g = game.getGraphics();

        Assets.logo = g.newPixmap("logo.png", PixmapFormat.RGB565);

        // Screens
        Assets.startscreen = g.newPixmap("startscreen.png", PixmapFormat.RGB565);
        Assets.gamescreen = g.newPixmap("gamescreen.png", PixmapFormat.RGB565);
        Assets.highscoresscreen = Assets.startscreen;
        Assets.gameoverscreen = g.newPixmap("gameover.png", PixmapFormat.RGB565);

        // Menus
        Assets.mainmenu = g.newPixmap("mainmenu.png", PixmapFormat.RGB565);
        Assets.pausemenu = g.newPixmap("pausemenu.png", PixmapFormat.RGB565);
        Assets.readymenu = g.newPixmap("ready.png", PixmapFormat.ARGB4444);

        // Aliens
        Assets.alienUgly1 = g.newPixmap("alien-ugly1.png", PixmapFormat.RGB565);
        Assets.alienUgly2 = g.newPixmap("alien-ugly2.png", PixmapFormat.RGB565);
        Assets.alienBad1 = g.newPixmap("alien-bad1.png", PixmapFormat.RGB565);
        Assets.alienBad2 = g.newPixmap("alien-bad2.png", PixmapFormat.RGB565);
        Assets.alienGood1 = g.newPixmap("alien-good1.png", PixmapFormat.RGB565);
        Assets.alienGood2 = g.newPixmap("alien-good2.png", PixmapFormat.RGB565);

        // Ship
        Assets.ship = g.newPixmap("ship.png", PixmapFormat.RGB565);
        Assets.shipLife = g.newPixmap("ship-life.png", PixmapFormat.RGB565);

        // Shields
        Assets.shieldLarge = g.newPixmap("shield-large.png", PixmapFormat.RGB565);
        Assets.shieldMedium = g.newPixmap("shield-medium.png", PixmapFormat.RGB565);
        Assets.shieldSmall = g.newPixmap("shield-small.png", PixmapFormat.RGB565);

        // Projectiles
        Assets.shipProjectile = g.newPixmap("projectile-ship.png", PixmapFormat.RGB565);
        Assets.alienProjectile = g.newPixmap("projectile-alien.png", PixmapFormat.RGB565);

        // buttons and numbers
        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.RGB565);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);

        // Audio effects
        Assets.explosion = game.getAudio().newSound("explosion.wav");
        Assets.laserCanon = game.getAudio().newSound("lasercanon.wav");
        Assets.laserClash = game.getAudio().newSound("laserclash.wav");
        Assets.shieldImpact = game.getAudio().newSound("shieldimpact.wav");
        Assets.click = game.getAudio().newSound("click.ogg");
        Assets.bitten = game.getAudio().newSound("bitten.ogg");

        // Music
        Assets.musicInvaders = game.getAudio().newMusic("invaders.wav");

        Settings.load(game.getFileIO());
        game.setScreen(new StartScreen(game));
    }

    @Override
    public void draw(float deltaTime) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}