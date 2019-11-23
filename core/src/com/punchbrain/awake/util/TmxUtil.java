package com.punchbrain.awake.util;

import com.badlogic.gdx.maps.tiled.TiledMap;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.tmx.TiledMapContext;

public class TmxUtil {

   public static TiledMapContext loadTiledMap(String tiledMapId, GameContext2D context) {
      TiledMap tiledMap = SharedAssetManager.getInstance().get(tiledMapId);
      return context.getTiledMapManager().load(tiledMap, context.getGameCamera().getInternalCamera());
   }
}
