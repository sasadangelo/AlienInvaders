package org.androidforfun.alieninvaders.view;

import org.androidforfun.framework.Screen;
import org.androidforfun.framework.impl.AndroidGame;

public class AlienInvadersGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}