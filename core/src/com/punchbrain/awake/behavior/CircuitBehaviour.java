package com.punchbrain.awake.behavior;

import box2dLight.Light;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.punchbrain.awake.animation.LampState;
import com.punchbrain.awake.model.Circuit;
import de.bitbrain.braingdx.behavior.BehaviorAdapter;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.world.GameObject;

import static com.punchbrain.awake.GameObjectType.PLAYER;

/**
 * Adds Circuit behaviours. Is applied to a flipSwitch game object, not a lamp object.
 */
public class CircuitBehaviour extends BehaviorAdapter {

    private Circuit circuit;
    private GameContext2D context;
    private Light lampLight;
    private boolean continuous = false;
    private float deltaAccumulator = 0;


    public CircuitBehaviour(Circuit circuit, GameContext2D context){
        this.circuit = circuit;
        this.context = context;
    }

    @Override
    public void update(GameObject source, float delta){
        circuit.updatePassiveBehaviour(delta);
    }

    @Override
    public void update(GameObject source, GameObject player, float delta) {
        if(PLAYER.isTypeOf(player)){
            Rectangle playerRect = new Rectangle();
            playerRect.set(player.getLeft(), player.getTop(), player.getWidth(), player.getHeight());
            Rectangle circuitRect = new Rectangle();
            circuitRect.set(source.getLeft(), source.getTop(), source.getWidth(), source.getHeight());
            if(playerRect.contains(circuitRect) || playerRect.overlaps(circuitRect)){
                circuit.resolvePlayerCollision();
            }
        }
        super.update(source, delta);
    }
}
