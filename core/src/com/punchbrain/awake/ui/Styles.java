package com.punchbrain.awake.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.punchbrain.awake.assets.Assets;

import static de.bitbrain.braingdx.graphics.BitmapFontBaker.bake;

public class Styles {

   public static final Label.LabelStyle LABEL_TOAST = new Label.LabelStyle();

   public static void init() {
      LABEL_TOAST.font = bake(Assets.Fonts.CODERS_CRUX, Gdx.graphics.getHeight() / 3, true);
      LABEL_TOAST.fontColor = Color.WHITE;

   }
}
