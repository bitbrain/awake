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

public class CircuitBehaviour extends BehaviorAdapter {

    private Circuit circuit;
    private GameContext2D context;
    private Light lampLight;
    private boolean continuous = false;


    public CircuitBehaviour(Circuit circuit, GameContext2D context){
        this.circuit = circuit;
        this.context = context;
        this.lampLight = context.getLightingManager().createPointLight(200, Color.GOLD);
    }

    @Override
    public void update(GameObject source, float delta){
        if(circuit.isOn()){
            circuit.getFlipSwitchGameObject().setAttribute(LampState.class, LampState.ON);
            circuit.getLampGameObject().setAttribute(LampState.class, LampState.ON);
        } else {
            circuit.getFlipSwitchGameObject().setAttribute(LampState.class, LampState.OFF);
            circuit.getLampGameObject().setAttribute(LampState.class, LampState.OFF);
        }
    }

    @Override
    public void update(GameObject source, GameObject player, float delta) {
        if(PLAYER.isTypeOf(player)){
            System.out.println(player.getZIndex());
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
