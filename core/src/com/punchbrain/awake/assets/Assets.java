package com.punchbrain.awake.assets;

public interface Assets {

   interface Textures {
      String PLAYER = "textures/player.png";
      String PLAYER_TILESET = "textures/player-sprites.png";
      String LAMP_TILESET = "textures/lamp-sprites.png";
      String FLIP_SWITCH_TILESET = "textures/flip-switch-sprites.png";
   }

   interface TiledMaps {
      String BOYS_ROOM = "tmx/boys-room.tmx";
      String HALLWAY = "tmx/hallway.tmx";
      String BATHROOM = "tmx/bathroom.tmx";
      String LEVEL_01 = "tmx/level-01.tmx";
   }

   interface Musics {
      String DARK_AMBIENT_001 = "music/dark-ambient-001.ogg";
   }

   interface Sounds {
      String DOOR_OPEN = "sounds/door-open.ogg";
      String DOOR_CLOSE = "sounds/door-shut.ogg";
      String SWITCH_ON = "sounds/switch-on.ogg";
      String SWITCH_OFF = "sounds/switch-off.ogg";
   }

   interface Particles {
      // TODO
   }

   interface Fonts {
      String CODERS_CRUX = "fonts/coderscrux.ttf";
   }
}
