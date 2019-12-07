package com.punchbrain.awake.ui;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.punchbrain.awake.Colors;
import de.bitbrain.braingdx.tweens.ActorTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;

public class Toast {

   private static final Toast INSTANCE = new Toast();

   private static final TweenManager tweenManager = SharedTweenManager.getInstance();

   private Label toast;

   private Stage stage;

   public static Toast getInstance() {
      return INSTANCE;
   }

   public void init(Stage stage) {
      this.toast = null;
      this.stage = stage;
   }

   public void doToast(String text) {
      if (stage == null) {
         Gdx.app.log("WARN", "Unable to create toast! Stage not defined.");
         return;
      }
      if (this.toast != null) {
         tweenManager.killTarget(this.toast);
      }
      if (toast == null) {
         toast = new Label(text, Styles.LABEL_TOAST);
         toast.setColor(new Color(0f, 0f, 0f, 0f));
         stage.addActor(toast);
      }
      toast.setAlignment(Align.left | Align.bottom);
      toast.setPosition(96f, 64f);
      toast.setColor(Colors.BACKGROUND.cpy());
      toast.setWidth(Gdx.graphics.getWidth());
      toast.setHeight(Gdx.graphics.getHeight());
      toast.getColor().a = 0.0f;
      toast.setText(text);
      Tween.to(toast, ActorTween.ALPHA, 1.7f)
            .target(0.8f)
            .repeatYoyo(1, 0f)
            .ease(TweenEquations.easeInQuad)
            .start(tweenManager);
      Tween.to(toast, ActorTween.X, 3.4f)
            .target(toast.getX() + 128f)
            .ease(TweenEquations.easeInQuad)
            .start(tweenManager);
   }
}