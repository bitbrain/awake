package com.punchbrain.awake.bootstrap;

import com.punchbrain.awake.model.map.CircuitModelMap;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.tmx.TiledMapContext;

public interface LevelBootstrap {
    void boostrap(GameContext2D gameContext2D, TiledMapContext tiledMapContext);
    //TODO: get rid of once a generic game context collection exists.
    void setCircuitModelMap(CircuitModelMap circuitModelMap);
    boolean isApplicable(String tiledMapPath);
}
