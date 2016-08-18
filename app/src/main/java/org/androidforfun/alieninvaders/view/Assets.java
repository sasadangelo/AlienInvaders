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
 *  Droids is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License.
 */
package org.androidforfun.alieninvaders.view;

import org.androidforfun.framework.Music;
import org.androidforfun.framework.Pixmap;
import org.androidforfun.framework.Sound;

/*
 This class contains the global references to all the assets used in AlienInvadersGame game.
 *
 * @author Salvatore D'Angelo
 */
public class Assets {
    // the logo asset
    public static Pixmap logo;

    // the screen used in AlienInvadersWorld game
    public static Pixmap gamescreen;
    public static Pixmap startscreen;
    public static Pixmap highscoresscreen;
    public static Pixmap gameoverscreen;

    // the menu used in AlienInvadersWorld game
    public static Pixmap mainmenu;
    public static Pixmap pausemenu;
    public static Pixmap readymenu;

    // buttons and numbers
    public static Pixmap buttons;
    public static Pixmap numbers;

    // the Alien bitmaps
    public static Pixmap alienBad1;
    public static Pixmap alienBad2;
    public static Pixmap alienUgly1;
    public static Pixmap alienUgly2;
    public static Pixmap alienGood1;
    public static Pixmap alienGood2;

    // the Ship bitmaps
    public static Pixmap ship;
    public static Pixmap shipLife;

    // the Shield bitmaps
    public static Pixmap shieldLarge;
    public static Pixmap shieldMedium;
    public static Pixmap shieldSmall;

    // the Shield bitmaps
    public static Pixmap shipProjectile;
    public static Pixmap alienProjectile;

    // sounds
    public static Sound explosion;
    public static Sound laserCanon;
    public static Sound laserClash;
    public static Sound shieldImpact;
    public static Sound click;
    public static Sound bitten;

    // music
    public static Music musicInvaders;
}
