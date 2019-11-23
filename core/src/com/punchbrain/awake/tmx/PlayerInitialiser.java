package com.punchbrain.awake.tmx;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.punchbrain.awake.model.Player;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.event.GameEventListener;
import de.bitbrain.braingdx.tmx.TiledMapEvents;
import de.bitbrain.braingdx.world.GameObject;

import static com.punchbrain.awake.GameObjectType.COLLISION;
import static com.punchbrain.awake.GameObjectType.PLAYER;

public class PlayerInitialiser implements GameEventListener<TiledMapEvents.OnLoadGameObjectEvent> {

   private final GameContext2D context;
   private Player player;

   public PlayerInitialiser(GameContext2D context) {
      this.context = context;
   }

   public Player getPlayer() {
      return player;
   }

   @Override
   public void onEvent(TiledMapEvents.OnLoadGameObjectEvent event) {
      GameObject object = event.getObject();
      if (PLAYER.isTypeOf(object)) {
         context.getGameCamera().setTrackingTarget(object);
         context.getGameCamera().setTargetTrackingSpeed(0.004f);
         context.getGameCamera().setDefaultZoomFactor(0.1f);
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
      }
   }
}
