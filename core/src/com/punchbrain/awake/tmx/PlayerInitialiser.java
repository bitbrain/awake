package com.punchbrain.awake.tmx;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.model.Player;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.event.GameEventListener;
import de.bitbrain.braingdx.tmx.TiledMapEvents;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.world.GameObject;

import static com.punchbrain.awake.GameObjectType.PLAYER;
import static com.punchbrain.awake.GameObjectType.TELEPORT;

public class PlayerInitialiser implements GameEventListener<TiledMapEvents.OnLoadGameObjectEvent> {

   private final GameContext2D context;
   private Player player;
   private final String targetTeleportId;

   private GameObject targetTeleport;
   private GameObject playerObject;

   public PlayerInitialiser(GameContext2D context, String targetTeleportId) {
      this.context = context;
      this.targetTeleportId = targetTeleportId;
   }

   public Player getPlayer() {
      return player;
   }

   @Override
   public void onEvent(TiledMapEvents.OnLoadGameObjectEvent event) {
      final GameObject object = event.getObject();
      if (PLAYER.isTypeOf(object)) {
         context.getGameCamera().setTrackingTarget(object);
         context.getGameCamera().setTargetTrackingSpeed(0.02f);
         context.getGameCamera().setDefaultZoomFactor(0.2f);
         object.setDimensions(32, 64);
         BodyDef playerBodyDef = new BodyDef();
         playerBodyDef.type = BodyDef.BodyType.DynamicBody;
         playerBodyDef.position.set(object.getLeft() + object.getWidth() / 2f, object.getTop() + object.getHeight() / 2f);
         playerBodyDef.fixedRotation = true;
         FixtureDef fixtureDef = new FixtureDef();
         PolygonShape shape = new PolygonShape();
         shape.setAsBox(object.getWidth() / 2f, object.getHeight() / 2f);
         fixtureDef.shape = shape;
         fixtureDef.density = 0.00001f;
         fixtureDef.friction = 0f;
         fixtureDef.restitution = 0f;

         Body body = context.getPhysicsManager().attachBody(playerBodyDef, fixtureDef, object);
         this.player = new Player(object, body);
         this.playerObject = object;
         if (targetTeleport != null) {
            Vector2 pos = getTargetTeleportPosition(targetTeleport, object);
            player.setPosition(pos.x, pos.y);
            context.getGameCamera().focusCentered(playerObject);
            context.setPaused(true);
         }
      } else if (targetTeleportId != null && TELEPORT.isTypeOf(object) && object.getAttribute("id", String.class).equals(targetTeleportId)) {
         this.targetTeleport = object;
         Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
               context.getAudioManager().spawnSound(Assets.Sounds.DOOR_CLOSE, object.getLeft(), object.getTop(), 1f, 1f, 358f);
            }
         }).delay(0.2f).start(SharedTweenManager.getInstance());
         if (player != null) {
            Vector2 pos = getTargetTeleportPosition(targetTeleport, playerObject);
            player.setPosition(pos.x, pos.y);
            context.getGameCamera().focusCentered(playerObject);
            context.setPaused(true);
         }
      }
   }

   private Vector2 getTargetTeleportPosition(GameObject teleport, GameObject target) {
      String id = teleport.getAttribute("id", String.class);
      if (id.contains("left")) {
         return teleport.getPosition().add(target.getWidth(), 0);
      } else if (id.contains("right")) {
         return teleport.getPosition().sub(target.getWidth(), 0);
      }
      return teleport.getPosition().add(target.getWidth() / 2f - target.getWidth() / 2f, 0);
   }
}

