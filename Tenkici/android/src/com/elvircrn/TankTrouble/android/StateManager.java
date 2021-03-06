package com.elvircrn.TankTrouble.android;

/**
 * Created by elvircrn on 3/1/2016.
 */
public class StateManager {
    public static enum State {BLUE, MAINMENU, SINGLEPLAYER, MULTIPLAYER, OPTIONS, VICTORY_SCREEN, MP_MENU};

    private static boolean justChanged = false;
    private static State currentState, prevState;

    public static State getCurrentState() {
        return currentState;
    }

    public static void changeState(State newState) {
        currentState = newState;
    }

    public static void update(float deltaTime) {
        if (prevState == null || prevState != currentState)
            justChanged = true;
        else
            justChanged = false;

        prevState = currentState;
    }
}
