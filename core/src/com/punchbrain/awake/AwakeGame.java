package com.punchbrain.awake;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.screens.LevelScreen;
import de.bitbrain.braingdx.BrainGdxGame;
import de.bitbrain.braingdx.GameSettings;
import de.bitbrain.braingdx.assets.GameAssetLoader;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.assets.SmartAssetLoader;
import de.bitbrain.braingdx.event.GameEventManagerImpl;
import de.bitbrain.braingdx.graphics.GraphicsSettings;
import de.bitbrain.braingdx.graphics.postprocessing.filters.RadialBlur;
import de.bitbrain.braingdx.screens.AbstractScreen;
import org.apache.commons.lang.SystemUtils;

import static com.punchbrain.awake.assets.Assets.Musics.DARK_AMBIENT_001;

public class AwakeGame extends BrainGdxGame {

   private boolean devMode;
   private boolean debugMode;
   private boolean gifMode;

   public AwakeGame(String ... args) {
      for (String arg : args) {
         if ("dev".equalsIgnoreCase(arg)) {
            devMode = true;
         }
         if ("debug".equalsIgnoreCase(arg)) {
            debugMode = true;
         }
         if ("gif".equalsIgnoreCase(arg)) {
            gifMode = true;
         }
      }
   }

   @Override
   protected GameAssetLoader getAssetLoader() {
      return new SmartAssetLoader(Assets.class);
   }

   @Override
   protected AbstractScreen<?, ?> getInitialScreen() {
      configureSettings();
      SharedAssetManager.getInstance().get(DARK_AMBIENT_001, Music.class).setLooping(true);
      SharedAssetManager.getInstance().get(DARK_AMBIENT_001, Music.class).setVolume(0.01f);
      SharedAssetManager.getInstance().get(DARK_AMBIENT_001, Music.class).play();
      return new LevelScreen(this, Assets.TiledMaps.BOYS_ROOM);
   }

   private void configureSettings() {
      GameSettings settings = new GameSettings(new GameEventManagerImpl());
      GraphicsSettings graphicsSettings = settings.getGraphics();
      if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS) {
         graphicsSettings.setRadialBlurQuality(RadialBlur.Quality.Low);
         Gdx.input.setCatchBackKey(true);
         graphicsSettings.setRenderScale(0.05f);
         graphicsSettings.setParticleMultiplier(0.1f);
         graphicsSettings.save();
      } else if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
         if (gifMode) {
            Gdx.graphics.setWindowedMode(1248, 770);
         } else if (SystemUtils.IS_OS_WINDOWS) {
            Gdx.graphics.setUndecorated(true);
            Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
         } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
         }
         graphicsSettings.setRadialBlurQuality(RadialBlur.Quality.High);
         graphicsSettings.setRenderScale(0.3f);
         graphicsSettings.setParticleMultiplier(0.5f);
         graphicsSettings.save();
      }
   }

}
