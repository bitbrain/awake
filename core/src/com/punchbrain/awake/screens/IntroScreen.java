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
import com.punchbrain.awake.model.Player;
import de.bitbrain.braingdx.GameContext;
import de.bitbrain.braingdx.graphics.GameCamera;
import de.bitbrain.braingdx.graphics.GameObjectRenderManager;
import de.bitbrain.braingdx.graphics.lighting.LightingManager;
import de.bitbrain.braingdx.graphics.lighting.PointLightBehavior;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.graphics.renderer.SpriteRenderer;
import de.bitbrain.braingdx.input.InputManager;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.braingdx.world.GameObject;

import static com.punchbrain.awake.GameObjectType.*;
import static com.punchbrain.awake.util.TmxUtil.loadTiledMap;

public class IntroScreen extends AbstractScreen<AwakeGame> {

   private GameObject playerObject;
   private Player player;

   public IntroScreen(AwakeGame game) {
      super(game);
   }

   @Override
   protected void onCreate(GameContext context) {
      setBackgroundColor(Colors.BACKGROUND);

      setupWorld(context);
      setupGraphics(context);
      setupPhysics(context);
      setupInput(context.getInputManager());
   }

   private void setupInput(InputManager inputManager) {
      inputManager.register(new IntroControllerInputAdapter(this));
      inputManager.register(new IntroKeyboardInputAdapter(this, player));
   }

   private void setupGraphics(GameContext context) {
      context.getRenderManager().register(PLAYER.name(), new SpriteRenderer(Assets.Textures.PLAYER));
      LightingManager.LightingConfig lightingConfig = new LightingManager.LightingConfig();
      lightingConfig.rays(500);
      context.getLightingManager().setConfig(lightingConfig);
      context.getLightingManager().setAmbientLight(Colors.BACKGROUND);
      context.getBehaviorManager().apply(new PointLightBehavior(Colors.FOREGROUND, 256f, context.getLightingManager()), playerObject);

      context.getRenderPipeline().addEffects(RenderPipeIds.WORLD_UI, context.getShaderManager().createBloomEffect());

   }

   private void setupPhysics(GameContext context) {
      context.getPhysicsManager().setGravity(0f, -98);

      BodyDef playerBodyDef = new BodyDef();
      playerBodyDef.type = BodyDef.BodyType.DynamicBody;
      playerBodyDef.position.set(playerObject.getLeft(), playerObject.getTop());
      FixtureDef fixtureDef = new FixtureDef();
      PolygonShape shape = new PolygonShape();
      shape.setAsBox(16, 32);
      fixtureDef.shape = shape;
      fixtureDef.density = 100f;
      playerObject.setOffset(-16,-32);
      Body body = context.getPhysicsManager().attachBody(playerBodyDef, fixtureDef, playerObject);
      this.player = new Player(playerObject, body);
   }

   private void setupWorld(GameContext context) {
      loadTiledMap(Assets.TiledMaps.BOYS_ROOM, context);

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
            shape.setAsBox(object.getWidth() / 2, object.getHeight() / 2);

            //fixture
            FixtureDef fixture = new FixtureDef();
            fixture.friction = 0.3f;
            fixture.shape = shape;

            body.createFixture(fixture);
            shape.dispose();
         }
      }
   }
}
