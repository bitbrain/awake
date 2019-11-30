package com.punchbrain.awake.model;

import box2dLight.Light;
import de.bitbrain.braingdx.world.GameObject;

public class Circuit {

    GameObject lamp;
    GameObject flipSwitch;
    Light lightObject;

    boolean isOn;

    public Circuit(GameObject lamp, GameObject flipSwitch, Light lightObject){
        this.lamp = lamp;
        this.flipSwitch = flipSwitch;
        this.lightObject = lightObject;
    }

    public boolean isOn(){
        return isOn;
    }

    public Light getLightObject(){
        return lightObject;
    }

    public void flipSwitch(){
        isOn = !isOn;
    }

    public GameObject getLampGameObject(){
        return lamp;
    }

    public GameObject getFlipSwitchGameObject(){
        return flipSwitch;
    }
}
