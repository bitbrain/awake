package com.punchbrain.awake.behavior;

import com.punchbrain.awake.animation.PlayerDirection;
import com.punchbrain.awake.model.Player;
import de.bitbrain.braingdx.behavior.BehaviorAdapter;
import de.bitbrain.braingdx.world.GameObject;

public class PlayerUpdateBehavior extends BehaviorAdapter {

    Player player;

    public PlayerUpdateBehavior(Player player) {
        this.player = player;
    }

    @Override
    public void update(GameObject source, float delta) {
        switch (player.getState()) {
            case FALLING:
                if (player.getVelocityX() > 0) {
                    player.getGameObject().setAttribute(PlayerDirection.class, PlayerDirection.FALLING_RIGHT);
                } else {
                    player.getGameObject().setAttribute(PlayerDirection.class, PlayerDirection.FALLING_LEFT);
                }
                break;
            case WALKING:
                if( player.getVelocityX() < 0.1 && player.getVelocityX() > -0.1){
                    player.getGameObject().setAttribute(PlayerDirection.class, PlayerDirection.STANDING_LEFT);
                }
                else if (player.getVelocityX() > 0.1) {
                    player.getGameObject().setAttribute(PlayerDirection.class, PlayerDirection.RUNNING_RIGHT);
                } else {
                    player.getGameObject().setAttribute(PlayerDirection.class, PlayerDirection.RUNNING_LEFT);
                }
                break;
            case JUMPING:
                if (player.getVelocityX() > 0) {
                    player.getGameObject().setAttribute(PlayerDirection.class, PlayerDirection.JUMPING_RIGHT);
                } else {
                    player.getGameObject().setAttribute(PlayerDirection.class, PlayerDirection.JUMPING_LEFT);
                }
                break;
        }
        super.update(source, delta);
    }
}
