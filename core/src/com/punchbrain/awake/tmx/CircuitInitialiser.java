package com.punchbrain.awake.tmx;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import box2dLight.Light;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.punchbrain.awake.GameObjectType;
import com.punchbrain.awake.animation.LampState;
import com.punchbrain.awake.animation.PlayerDirection;
import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.behavior.CircuitBehaviour;
import com.punchbrain.awake.behavior.PlayerUpdateBehavior;
import com.punchbrain.awake.model.Circuit;
import com.punchbrain.awake.model.Player;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.event.GameEventListener;
import de.bitbrain.braingdx.tmx.TiledMapEvents;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.world.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.punchbrain.awake.GameObjectType.*;

public class CircuitInitialiser implements GameEventListener<TiledMapEvents.OnLoadGameObjectEvent> {

   private final GameContext2D context;
   Map<String, GameObject> circuitPairMap;

   private GameObject targetTeleport;
   private GameObject playerObject;


   public CircuitInitialiser(GameContext2D context) {
      this.context = context;
      this.circuitPairMap = new HashMap<>();
   }

   @Override
   public void onEvent(TiledMapEvents.OnLoadGameObjectEvent event) {
      final GameObject object = event.getObject();
      if (CIRCUIT_LAMP.isTypeOf(object)) {
         object.setDimensions(26,26);
         String circuitId = object.getAttribute("circuit_id", String.class);
         GameObject circuitFlipSwitch = circuitPairMap.get(circuitId);
         if(circuitFlipSwitch == null){
            circuitPairMap.put(circuitId, object);
         } else {
            Light light = context.getLightingManager().createPointLight(100, Color.GOLD);
            context.getLightingManager().attach(light, object);
            object.setAttribute(LampState.class, LampState.ON);
            circuitFlipSwitch.setAttribute(LampState.class, LampState.ON);
            Circuit circuit = new Circuit(object, circuitFlipSwitch, light);
            CircuitBehaviour behavior = new CircuitBehaviour(circuit, context);
            context.getBehaviorManager().apply(behavior, circuitFlipSwitch);
         }
      } else if (CIRCUIT_FLIP_SWITCH.isTypeOf(object)) {
         object.setDimensions(26,26);
         String circuitId = object.getAttribute("circuit_id", String.class);
         GameObject circuitLamp = circuitPairMap.get(circuitId);
         if(circuitLamp == null){
            circuitPairMap.put(circuitId, object);
         } else {
            Light light = context.getLightingManager().createPointLight(100, Color.GOLD);
            context.getLightingManager().attach(light, circuitLamp, true);
            object.setAttribute(LampState.class, LampState.ON);
            circuitLamp.setAttribute(LampState.class, LampState.ON);
            Circuit circuit = new Circuit(circuitLamp, object, light);
            CircuitBehaviour behavior = new CircuitBehaviour(circuit, context);
            context.getBehaviorManager().apply(behavior, object);
         }
      }
   }
}

