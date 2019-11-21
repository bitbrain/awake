package com.punchbrain.awake.screens;

import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.punchbrain.awake.AwakeGame;
import com.punchbrain.awake.Colors;
import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.input.intro.IntroControllerInputAdapter;
import com.punchbrain.awake.input.intro.IntroKeyboardInputAdapter;
import com.punchbrain.awake.model.Circuit;
import com.punchbrain.awake.model.Player;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.graphics.lighting.LightingConfig;
import de.bitbrain.braingdx.graphics.lighting.PointLightBehavior;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.graphics.renderer.SpriteRenderer;
import de.bitbrain.braingdx.input.InputManager;
import de.bitbrain.braingdx.screen.BrainGdxScreen2D;
import de.bitbrain.braingdx.util.Hash;
import de.bitbrain.braingdx.world.GameObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.punchbrain.awake.GameObjectType.*;
import static com.punchbrain.awake.util.TmxUtil.loadTiledMap;

public class IntroScreen extends BrainGdxScreen2D<AwakeGame> {

   private GameObject playerObject;
   private Player player;
   private List<Circuit> circuits;
   private Map<GameObject, Circuit> flipSwitchToCircuitMap;

   public IntroScreen(AwakeGame game) {
      super(game);
   }

   @Override
   protected void onCreate(GameContext2D context) {
      context.setBackgroundColor(Colors.BACKGROUND);
      setupWorld(context);
      setupGraphics(context);
      setupPhysics(context);
      setupInput(context.getInputManager());
   }

   private void setupInput(InputManager inputManager) {
      inputManager.register(new IntroControllerInputAdapter(this));
      inputManager.register(new IntroKeyboardInputAdapter(this, player));
   }

   private void setupGraphics(GameContext2D context) {
      context.getRenderManager().register(PLAYER.name(), new SpriteRenderer(Assets.Textures.PLAYER));
      LightingConfig lightingConfig = new LightingConfig();
      lightingConfig.rays(200);
      lightingConfig.blur(false);
      context.getLightingManager().setConfig(lightingConfig);
      context.getLightingManager().setAmbientLight(Colors.BACKGROUND);
      context.getBehaviorManager().apply(new PointLightBehavior(Colors.FOREGROUND, 256f, context.getLightingManager()), playerObject);

      context.getRenderPipeline().addEffects(RenderPipeIds.WORLD_UI, context.getShaderManager().createBloomEffect());

   }

   private void setupPhysics(GameContext2D context) {
      context.getPhysicsManager().setGravity(0f, -98);

      BodyDef playerBodyDef = new BodyDef();
      playerBodyDef.type = BodyDef.BodyType.DynamicBody;
      playerBodyDef.position.set(playerObject.getLeft() + playerObject.getWidth() / 2f, playerObject.getTop() + playerObject.getHeight() / 2f);
      playerBodyDef.fixedRotation = true;
      FixtureDef fixtureDef = new FixtureDef();
      PolygonShape shape = new PolygonShape();
      shape.setAsBox(playerObject.getWidth() / 2f, playerObject.getHeight() / 2f);
      fixtureDef.shape = shape;
      fixtureDef.density = 0.00001f;
      fixtureDef.friction = 0f;
      fixtureDef.restitution = 0f;

      Body body = context.getPhysicsManager().attachBody(playerBodyDef, fixtureDef, playerObject);
      this.player = new Player(playerObject, body);
   }

   private void setupWorld(GameContext2D context) {
      loadTiledMap(Assets.TiledMaps.BOYS_ROOM, context);

      flipSwitchToCircuitMap = new HashMap<>();
      Map<GameObject, GameObject> lampToFlipSwitch = new HashMap<>();
      Map<String, GameObject[]> lampSwitchGrouper = new HashMap<>();
      //[Lamp, Switch] in array

      for (GameObject object : context.getGameWorld().getObjects()) {
         if (PLAYER.isTypeOf(object)) {
            context.getGameCamera().setTrackingTarget(object);
            context.getGameCamera().setTargetTrackingSpeed(0.004f);
            object.setDimensions(32, 64);
            this.playerObject = object;
         } else if (COLLISION.isTypeOf(object)) {
            //bodydef
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(object.getLeft() + object.getWidth() / 2f, object.getTop() + object.getHeight() / 2f);
            Body body = context.getBox2DWorld().createBody(bodyDef);

            //shape
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(object.getWidth() / 2f, object.getHeight() / 2f);

            //fixture
            FixtureDef fixture = new FixtureDef();
            fixture.friction = 0f;
            fixture.shape = shape;

            body.createFixture(fixture);
            shape.dispose();
         } if (LIGHT.isTypeOf(object)) {
            context.getLightingManager().addPointLight(UUID.randomUUID().toString(), new Vector2(object.getLeft(), object.getTop()), 100f, Colors.FOREGROUND);
         } if (FLIPSWITCH.isTypeOf(object)){
            String circitId = (String) object.getAttribute("circuit");
            GameObject[] group = lampSwitchGrouper.get(circitId);
            if(group == null){
               GameObject[] fill = {null, object};
               lampSwitchGrouper.put(circitId, fill);
            } else {
               group[1] = object;
            }
         } else if (LAMP.isTypeOf(object)){
            String circitId = (String) object.getAttribute("circuit");
            GameObject[] group = lampSwitchGrouper.get(circitId);
            if(group == null){
               GameObject[] fill = {object, null};
               lampSwitchGrouper.put(circitId, fill);
            } else {
               group[0] = object;
            }
         }
      }

      for(String id : lampSwitchGrouper.keySet()){
         GameObject[] group = lampSwitchGrouper.get(id);
         Circuit circuit = new Circuit(group[0], group[1]);
         circuits.add(circuit);
         flipSwitchToCircuitMap.put(group[1], circuit);
      }
   }
}
