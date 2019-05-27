/*
 *  Copyright (C) 2016 Salvatore D'Angelo
 *  This file is part of Alien Invaders project.
 *  This file derives from the Mr Nom project developed by Mario Zechner for the Beginning Android
 *  Games book (chapter 6).
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
package org.androidforfun.alieninvaders.view;

import android.util.Log;

import org.androidforfun.framework.Game;
import org.androidforfun.framework.Gdx;
import org.androidforfun.framework.Graphics;
import org.androidforfun.framework.Graphics.PixmapFormat;
import org.androidforfun.framework.Screen;
import org.androidforfun.alieninvaders.model.Settings;

/*
 * This class represents the loading screen. It load in memory all the assets used by the game.
 * Usually games show a progress bar in this screen. To simplify the code and since the assets are
 * loaded very quickly I avoided this complication.
 *
 * @author Salvatore D'Angelo
 */
public class LoadingScreen implements Screen {
    private static final String LOG_TAG = "Invaders.LoadingScreen";

    @Override
    public void update(float deltaTime) {
        Log.i(LOG_TAG, "update -- begin");
        Graphics g = Gdx.graphics;

        Assets.gamescreen = g.newPixmap("gamescreen.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.RGB565);

        // Screens
        Assets.startscreen = g.newPixmap("startscreen.png", PixmapFormat.RGB565);
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
        Assets.explosion = Gdx.audio.newSound("explosion.wav");
        Assets.laserCanon = Gdx.audio.newSound("lasercanon.wav");
        Assets.laserClash = Gdx.audio.newSound("laserclash.wav");
        Assets.shieldImpact = Gdx.audio.newSound("shieldimpact.wav");
        Assets.click = Gdx.audio.newSound("click.ogg");
        Assets.bitten = Gdx.audio.newSound("bitten.ogg");

        // Music
        Assets.musicInvaders = Gdx.audio.newMusic("invaders.wav");

        Settings.load(Gdx.fileIO);
        Gdx.game.setScreen(new StartScreen());
    }

    /*
     * Draw nothing.
     */
    public void draw(float deltaTime) {
        Log.i(LOG_TAG, "draw -- begin");
    }

    /*
     * The screen is paused.
     */
    public void pause() {
    }

    /*
     * The screen is resumed.
     */
    public void resume() {
    }

    /*
     * The screen is disposed.
     */
    public void dispose() {
    }
}