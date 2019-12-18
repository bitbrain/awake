package com.punchbrain.awake.event;

import com.punchbrain.awake.GameObjectType;
import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.screens.LevelScreen;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.event.GameEventListener;
import de.bitbrain.braingdx.world.GameObject;

public class TeleportEventListener implements GameEventListener<TeleportEvent> {

   private final GameContext2D gameContext;
   private boolean fired = false;
   private final LevelScreen screen;

   public TeleportEventListener(GameContext2D context,  LevelScreen screen) {
      this.gameContext = context;
      this.screen = screen;
   }

   @Override
   public void onEvent(TeleportEvent event) {
      if (!fired && GameObjectType.PLAYER.isTypeOf(event.getProducer())) {
         String file = event.getFile();
         String id = event.getTarget();
         gameContext.setPaused(true);
         GameObject o = event.getProducer();
         gameContext.getScreenTransitions().out(new LevelScreen(screen.getGame(), file, id), 0.5f);
         fired = true;
         gameContext.getAudioManager().spawnSound(Assets.Sounds.DOOR_OPEN, o.getLeft(), o.getTop(), (float) (0.8f + Math.random() * 0.3f), 1f, 358f);
      }
   }
}
