package com.punchbrain.awake.screens;

import com.badlogic.gdx.physics.box2d.*;
import com.punchbrain.awake.AwakeGame;
import com.punchbrain.awake.Colors;
import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.input.intro.IntroControllerInputAdapter;
import com.punchbrain.awake.input.intro.IntroKeyboardInputAdapter;
import com.punchbrain.awake.model.Player;
import de.bitbrain.braingdx.GameContext;
import de.bitbrain.braingdx.graphics.GameObjectRenderManager;
import de.bitbrain.braingdx.graphics.lighting.PointLightBehavior;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.graphics.renderer.SpriteRenderer;
import de.bitbrain.braingdx.input.InputManager;
import de.bitbrain.braingdx.physics.PhysicsManager;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.braingdx.world.GameObject;

import static com.punchbrain.awake.GameObjectType.COLLISION;
import static com.punchbrain.awake.GameObjectType.PLAYER;
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
      context.getLightingManager().setAmbientLight(Colors.BACKGROUND);
      context.getBehaviorManager().apply(new PointLightBehavior(Colors.FOREGROUND, 256f, context.getLightingManager()), playerObject);

      context.getRenderPipeline().addEffects(RenderPipeIds.WORLD_UI, context.getShaderManager().createBloomEffect());

   }

   private void setupPhysics(GameContext context) {
      context.getPhysicsManager().setGravity(0f, -1);
      BodyDef playerBodyDef = new BodyDef();
      playerBodyDef.type = BodyDef.BodyType.DynamicBody;
      playerBodyDef.position.set(playerObject.getLeft(), playerObject.getTop());
      FixtureDef fixtureDef = new FixtureDef();
      PolygonShape shape = new PolygonShape();
      shape.setAsBox(32, 64f);
      fixtureDef.shape = shape;
      fixtureDef.density = 100f;
      Body body = context.getPhysicsManager().attachBody(playerBodyDef, fixtureDef, playerObject);
      this.player = new Player(playerObject, body);
   }

   private void setupWorld(GameContext context) {
      loadTiledMap(Assets.TiledMaps.BOYS_ROOM, context);

      for (GameObject object : context.getGameWorld().getObjects()) {
         if (PLAYER.isTypeOf(object)) {
            context.getGameCamera().setTrackingTarget(object);
            object.setDimensions(32, 64);
            this.playerObject = object;
         } else if (COLLISION.isTypeOf(object)) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(object.getLeft(), object.getTop());
            FixtureDef fixtureDef = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(object.getWidth(), object.getHeight());
            fixtureDef.shape = shape;
            fixtureDef.density = 1f;
            Body body = context.getBox2DWorld().createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
         }
      }
   }
}
