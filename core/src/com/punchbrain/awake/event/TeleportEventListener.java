package com.punchbrain.awake.event;

import com.punchbrain.awake.model.Player;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.event.GameEventListener;

public class TeleportEventListener implements GameEventListener<TeleportEvent> {

   private final GameContext2D gameContext;
   private final Player player;

   public TeleportEventListener(GameContext2D context, Player player) {
      this.gameContext = context;
      this.player = player;
   }
   @Override
   public void onEvent(TeleportEvent event) {
   }
}
