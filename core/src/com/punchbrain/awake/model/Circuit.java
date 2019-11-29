package com.punchbrain.awake.model;

import de.bitbrain.braingdx.world.GameObject;

public class Circuit {

    GameObject lamp;
    GameObject flipSwitch;

    boolean isOn;

    public Circuit(GameObject lamp, GameObject flipSwitch){
        this.lamp = lamp;
        this.flipSwitch = flipSwitch;
    }

    public boolean isOn(){
        return isOn;
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
