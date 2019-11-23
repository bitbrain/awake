package com.punchbrain.awake.event;

import com.punchbrain.awake.GameObjectType;
import com.punchbrain.awake.model.Player;
import com.punchbrain.awake.screens.LevelScreen;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.event.GameEventListener;

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
         screen.getGame().setScreen(new LevelScreen(screen.getGame(), file, id));
         fired = true;
      }
   }
}
