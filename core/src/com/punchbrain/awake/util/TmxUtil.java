package com.punchbrain.awake.util;

import com.badlogic.gdx.maps.tiled.TiledMap;
import de.bitbrain.braingdx.GameContext;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.tmx.TiledMapType;

public class TmxUtil {

   public static void loadTiledMap(String tiledMapId, GameContext context) {
      TiledMap tiledMap = SharedAssetManager.getInstance().get(tiledMapId);
      context.getTiledMapManager().load(tiledMap, context.getGameCamera().getInternalCamera(), TiledMapType.ORTHOGONAL);
   }
}
