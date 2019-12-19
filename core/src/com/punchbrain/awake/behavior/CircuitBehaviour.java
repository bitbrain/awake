package com.punchbrain.awake.behavior;

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


    public CircuitBehaviour(Circuit circuit, GameContext2D context){
        this.circuit = circuit;
    }

    @Override
    public void update(GameObject source, float delta){
        circuit.updatePassiveBehaviour(delta);
    }

    @Override
    public void update(GameObject source, GameObject player, float delta) {
        if(PLAYER.isTypeOf(player)){
            if(player.collidesWith(source)){
                circuit.resolvePlayerCollision();
            }
        }
        super.update(source, delta);
    }
}
