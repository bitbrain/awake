package com.punchbrain.awake.bootstrap;

import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.tmx.TiledMapContext;

public interface LevelBootstrap {
    void boostrap(GameContext2D gameContext2D, TiledMapContext tiledMapContext);
    boolean isApplicable(String tiledMapPath);
}
