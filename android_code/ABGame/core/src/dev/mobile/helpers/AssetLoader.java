package dev.mobile.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Braedyn Kenzie on 2017-03-11.
 *
 */

public class AssetLoader {

    public static Sound ballCollide;
    public static Sound ballStartSound;

    public static void load(){

        ballCollide = Gdx.audio.newSound(Gdx.files.internal("data\\boop.wav"));
        ballStartSound = Gdx.audio.newSound(Gdx.files.internal("data\\ball_start_sound.wav"));

    }

    public static void dispose(){
        ballCollide.dispose();
    }

}
