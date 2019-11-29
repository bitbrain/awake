package com.punchbrain.awake.behavior;

import box2dLight.Light;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.punchbrain.awake.model.Circuit;
import de.bitbrain.braingdx.behavior.BehaviorAdapter;
import de.bitbrain.braingdx.context.GameContext;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.world.GameObject;

import static com.punchbrain.awake.GameObjectType.PLAYER;

public class CircuitBehaviour extends BehaviorAdapter {

    Circuit circuit;
    GameContext2D context;
    Light lampLight;
    boolean continuous = false;


    public CircuitBehaviour(Circuit circuit, GameContext2D context){
        this.circuit = circuit;
        this.context = context;
        this.lampLight = context.getLightingManager().createPointLight(200, Color.GOLD);
    }

    @Override
    public void update(GameObject source, GameObject player, float delta) {
        if(PLAYER.isTypeOf(player)){
            Rectangle playerRect = new Rectangle();
            playerRect.set(player.getLeft(), player.getTop(), player.getWidth(), player.getHeight());
            Rectangle circuitRect = new Rectangle();
            circuitRect.set(source.getLeft(), source.getTop(), source.getWidth(), source.getHeight());
            if(playerRect.contains(circuitRect) || playerRect.overlaps(circuitRect)){
                if(!continuous) {
                    circuit.flipSwitch();
                    float distance = circuit.isOn() ? 200 : 0;
                    circuit.getLightObject().setDistance(distance);
                    continuous = true;
                }
            } else {
                continuous = false;
            }
        }
        super.update(source, delta);
    }
}
