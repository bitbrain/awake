package com.punchbrain.awake.model;

import box2dLight.Light;
import com.badlogic.gdx.audio.Sound;
import com.punchbrain.awake.assets.Assets;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.audio.AudioManager;
import de.bitbrain.braingdx.world.GameObject;

public class Circuit {

    private final GameObject lamp;
    private final GameObject flipSwitch;
    private final Light lightObject;
    private final AudioManager audioManager;
    float deltaAccumulator = 0;
    float deltaLimit = 30;

    boolean isOn;

    public Circuit(GameObject lamp, GameObject flipSwitch, Light lightObject, AudioManager audioManager) {
        this.lamp = lamp;
        this.flipSwitch = flipSwitch;
        this.lightObject = lightObject;
        this.audioManager = audioManager;
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

    public void flipSwitch(){
        if (deltaAccumulator == 0) {
            isOn = true;
            deltaAccumulator += 1;
        }
        if (isOn) {
            audioManager.spawnSound(Assets.Sounds.SWITCH_ON, flipSwitch.getLeft(), flipSwitch.getTop(), 1f, 1f, 200f);
        } else {
            audioManager.spawnSound(Assets.Sounds.SWITCH_OFF, flipSwitch.getLeft(), flipSwitch.getTop(), 1f, 1f, 200f);
        }
    }

    public GameObject getLampGameObject() {
        return lamp;
    }

    public GameObject getFlipSwitchGameObject() {
        return flipSwitch;
    }
}
