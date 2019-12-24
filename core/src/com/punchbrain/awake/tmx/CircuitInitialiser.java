package com.punchbrain.awake.tmx;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import box2dLight.Light;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.punchbrain.awake.Colors;
import com.punchbrain.awake.GameObjectType;
import com.punchbrain.awake.animation.LampState;
import com.punchbrain.awake.animation.PlayerDirection;
import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.behavior.CircuitBehaviour;
import com.punchbrain.awake.behavior.PlayerUpdateBehavior;
import com.punchbrain.awake.model.Circuit;
import com.punchbrain.awake.model.Player;
import com.punchbrain.awake.model.map.CircuitModelMap;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.event.GameEventListener;
import de.bitbrain.braingdx.tmx.TiledMapEvents;
import de.bitbrain.braingdx.tweens.PointLight2DTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.world.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.punchbrain.awake.GameObjectType.*;

public class CircuitInitialiser implements GameEventListener<TiledMapEvents.OnLoadGameObjectEvent> {

   private final GameContext2D context;
   Map<String, GameObject> circuitPairMap;
   CircuitModelMap circuitModelMap;

   private GameObject targetTeleport;
   private GameObject playerObject;


   public CircuitInitialiser(GameContext2D context, CircuitModelMap modelMap) {
      this.context = context;
      this.circuitModelMap = modelMap;
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
            Light light = context.getLightingManager().createPointLight(0, Colors.LANTERN);
            context.getLightingManager().attach(light, object);
            object.setAttribute(LampState.class, LampState.ON);
            circuitFlipSwitch.setAttribute(LampState.class, LampState.ON);
            Circuit circuit = new Circuit(object, circuitFlipSwitch, light, context.getAudioManager());
            circuitModelMap.registerLamp(object, circuit);
            circuitModelMap.registerFlipSwitch(circuitFlipSwitch, circuit);
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
            Light light = context.getLightingManager().createPointLight(0, Colors.LANTERN);
            /*
            Tween.to(light, PointLight2DTween.DISTANCE, 0.9f)
                    .target(205f)
                    .ease(TweenEquations.easeOutElastic)
                    .repeat(Tween.INFINITY, 0f)
                    .start(SharedTweenManager.getInstance());

             */
            context.getLightingManager().attach(light, circuitLamp, true);
            object.setAttribute(LampState.class, LampState.ON);
            circuitLamp.setAttribute(LampState.class, LampState.ON);
            Circuit circuit = new Circuit(circuitLamp, object, light, context.getAudioManager());
            CircuitBehaviour behavior = new CircuitBehaviour(circuit, context);
            circuitModelMap.registerLamp(circuitLamp, circuit);
            circuitModelMap.registerFlipSwitch(object, circuit);
            context.getBehaviorManager().apply(behavior, object);
         }
      }
   }
}

