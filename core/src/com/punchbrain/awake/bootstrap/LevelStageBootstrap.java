package com.punchbrain.awake.bootstrap;

import static com.punchbrain.awake.GameObjectType.HOUND;

import box2dLight.Light;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.punchbrain.awake.GameObjectType;
import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.behavior.HoundBehavior;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.graphics.renderer.SpriteRenderer;
import de.bitbrain.braingdx.tmx.TiledMapContext;
import de.bitbrain.braingdx.world.GameObject;

public class LevelStageBootstrap implements LevelBootstrap {

    @Override
    public void boostrap(final GameContext2D gameContext2D, final TiledMapContext tiledMapContext) {
        // add hound
        GameObject hound = gameContext2D.getGameWorld().addObject();
        hound.setType(HOUND);
        hound.setDimensions(8f, 8f);
        hound.setPosition(0f, tiledMapContext.getWorldHeight() - 16f);
        hound.setActive(true);
        hound.setZIndex(99999f);
        Light light = gameContext2D.getLightingManager().createPointLight(200f, Color.BLACK);
        gameContext2D.getLightingManager().attach(light, hound);
        GameObject player = null;
        for (GameObject o : gameContext2D.getGameWorld().getObjects()) {
            if (GameObjectType.PLAYER.isTypeOf(o)) {
                player = o;
                break;
            }
        }
        if (player != null) {
            gameContext2D.getBehaviorManager().apply(new HoundBehavior(player), hound);
        } else {
            throw new GdxRuntimeException("Unable to initialise hound! Player not found");
        }
    }

    @Override
    public boolean isApplicable(final String tiledMapPath) {
        return tiledMapPath.contains("level");
    }
}