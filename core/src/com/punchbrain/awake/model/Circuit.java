package com.punchbrain.awake.model;

import box2dLight.Light;
import de.bitbrain.braingdx.world.GameObject;

public class Circuit {

    GameObject lamp;
    GameObject flipSwitch;
    Light lightObject;
    float deltaAccumulator = 0;
    float deltaLimit = 30;

    boolean isOn;

    public Circuit(GameObject lamp, GameObject flipSwitch, Light lightObject) {
        this.lamp = lamp;
        this.flipSwitch = flipSwitch;
        this.lightObject = lightObject;
    }

    public boolean isOn() {
        if (deltaAccumulator > deltaLimit) {
            return false;
        }
        return isOn;
    }

    public Light getLightObject() {
        return lightObject;
    }

    public float increaseDelta(float delta) {
        if (deltaAccumulator < deltaLimit) {
            this.deltaAccumulator += delta;
            if(deltaAccumulator > deltaLimit){
                this.getLightObject().setDistance(0);
            }
        }
        return this.deltaAccumulator;
    }

    public void flipSwitch() {
        if (deltaAccumulator == 0) {
            isOn = true;
            deltaAccumulator += 1;
        }
    }

    public GameObject getLampGameObject() {
        return lamp;
    }

    public GameObject getFlipSwitchGameObject() {
        return flipSwitch;
    }
}
