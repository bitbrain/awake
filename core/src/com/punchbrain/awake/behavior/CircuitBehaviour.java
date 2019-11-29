package com.punchbrain.awake.behavior;

import com.punchbrain.awake.model.Circuit;
import de.bitbrain.braingdx.behavior.BehaviorAdapter;

public class CircuitBehaviour extends BehaviorAdapter {

    Circuit circuit;
    public CircuitBehaviour(Circuit circuit){
        this.circuit = circuit;
    }
}
