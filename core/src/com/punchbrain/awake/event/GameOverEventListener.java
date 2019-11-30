package com.punchbrain.awake.event;

import com.punchbrain.awake.screens.LevelScreen;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.event.GameEventListener;

public class GameOverEventListener implements GameEventListener<GameOverEvent> {

    private final GameContext2D gameContext2D;
    private final LevelScreen levelScreen;

    public GameOverEventListener(final GameContext2D gameContext2D, final LevelScreen levelScreen) {
        this.gameContext2D = gameContext2D;
        this.levelScreen = levelScreen;
    }

    @Override
    public void onEvent(final GameOverEvent event) {
        gameContext2D.setPaused(true);
        levelScreen.reset();
    }
}
