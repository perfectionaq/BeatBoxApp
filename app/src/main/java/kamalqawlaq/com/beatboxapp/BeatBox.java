package kamalqawlaq.com.beatboxapp;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * Created by tauta on 6/8/18.
 */

public class BeatBox {
    public static final String TAG = "beatBox";

    private static final String SOUND_FOLDER = "sample_sounds";

    private SoundPool mSoundPool;
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();

    public BeatBox(Context context){
        mAssets = context.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);

        loadSounds();
    }

    private void loadSounds(){
        String[] soundNames = new String[0];

        try{
            soundNames = mAssets.list(SOUND_FOLDER);
            Log.i(TAG, "found: " + soundNames.length + " sounds");
        } catch(IOException e){
            Log.e(TAG, "Could not list assets", e);
        }

        for(String name: soundNames){
            try{
                String assetPath = SOUND_FOLDER + "/" + name;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch(Exception e){
                Log.e(TAG, "could not load sound " + name, e);
            }

        }
    }

    public List<Sound> getSounds(){
        return mSounds;
    }

    private void load(Sound sound) throws IOException{
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public void play(Sound sound){
        Integer soundId = sound.getSoundId();
        if(soundId == null){
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 2.0f);
    }

    public void release() {
        mSoundPool.release();
    }
}
