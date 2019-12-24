package com.punchbrain.awake.model.map;

import com.punchbrain.awake.model.Circuit;
import de.bitbrain.braingdx.world.GameObject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Maps circuit time game objects to their {@link com.punchbrain.awake.model.Circuit} models.
 */
public class CircuitModelMap {
    private final Map<GameObject, Circuit> lampMap;
    private final Map<GameObject, Circuit> flipSwitchMap;

    public CircuitModelMap(){
        this.lampMap = new HashMap<>();
        this.flipSwitchMap = new HashMap<>();
    }

    public void registerLamp(GameObject gameObject, Circuit circuit) {
        lampMap.put(gameObject, circuit);
    }

    public void registerFlipSwitch(GameObject gameObject, Circuit circuit) {
        lampMap.put(gameObject, circuit);
    }

    public Circuit getFromLamp(GameObject gameObject){
        return lampMap.get(gameObject);
    }

    public Circuit getFromFlipSwitch(GameObject gameObject){
        return flipSwitchMap.get(gameObject);
    }

}
