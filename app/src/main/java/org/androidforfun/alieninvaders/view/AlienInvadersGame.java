package org.androidforfun.alieninvaders.view;

import org.androidforfun.alieninvaders.framework.Screen;
import org.androidforfun.alieninvaders.framework.impl.AndroidGame;

public class AlienInvadersGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}