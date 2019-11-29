package com.punchbrain.awake.assets;

public interface Assets {

   interface Textures {
      String PLAYER = "textures/player.png";
      String PLAYER_TILESET = "textures/player-sprites.png";
   }

   interface TiledMaps {
      String BOYS_ROOM = "tmx/boys-room.tmx";
      String HALLWAY = "tmx/hallway.tmx";
      String BATHROOM = "tmx/bathroom.tmx";
   }

   interface Musics {
      String DARK_AMBIENT_001 = "music/dark-ambient-001.ogg";
   }

   interface Sounds {
      String DOOR_OPEN = "sounds/door-open.ogg";
      String DOOR_CLOSE = "sounds/door-shut.ogg";
   }

   interface Particles {
      // TODO
   }
}
