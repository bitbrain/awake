package com.punchbrain.awake.model;

import box2dLight.Light;
import com.badlogic.gdx.audio.Sound;
import com.punchbrain.awake.animation.LampState;
import com.punchbrain.awake.assets.Assets;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.audio.AudioManager;
import de.bitbrain.braingdx.world.GameObject;

public class Circuit {
    public enum State {OFF, ON, BROKEN}

    private final float lightRadius = 200;

    private final GameObject lamp;
    private final GameObject flipSwitch;
    private final Light lightObject;
    private final AudioManager audioManager;
    private float deltaAccumulator = 0;
    private float deltaLimit = 10;

    private State state = State.OFF;

    public Circuit(GameObject lamp, GameObject flipSwitch, Light lightObject, AudioManager audioManager) {
        this.lamp = lamp;
        this.flipSwitch = flipSwitch;
        this.lightObject = lightObject;
        this.audioManager = audioManager;
    }

    public float getLightRadius(){
        return this.state == State.ON ? this.lightRadius : 0;
    }

    public Circuit updatePassiveBehaviour(float delta) {
        updateState(delta);
        updateAnimationState();
        if (state == State.ON) {
            this.lightObject.setDistance(lightRadius);
        } else {
            this.lightObject.setDistance(0);
        }
        return this;
    }

    public Circuit resolvePlayerCollision() {
        // TODO: add player 'push' conditional
        this.flipSwitch();
        return this;
    }

    public State getState(){
        return this.state;
    }

    //TODO: make this a more general state?
    private void updateAnimationState() {
        if (this.state == State.ON) {
            this.flipSwitch.setAttribute(LampState.class, LampState.ON);
            this.lamp.setAttribute(LampState.class, LampState.ON);
        } else {
            this.flipSwitch.setAttribute(LampState.class, LampState.OFF);
            this.lamp.setAttribute(LampState.class, LampState.OFF);
        }
    }

    public GameObject getLampGameObject() {
        return lamp;
    }

    public GameObject getFlipSwitchGameObject() {
        return flipSwitch;
    }

    private void updateState(float delta) {
        if (state == State.ON) {
            deltaAccumulator += delta;
            if (deltaAccumulator >= deltaLimit) {
                state = State.BROKEN;
            }
        }
    }

    private void flipSwitch() {
        if (state == State.OFF) {
            state = State.ON;
            audioManager.spawnSound(Assets.Sounds.SWITCH_ON, flipSwitch.getLeft(), flipSwitch.getTop(), 1f, 1f, 200f);
        }
    }


}
