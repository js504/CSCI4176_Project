package in.geobullet.csci_4176_project.Utils;

import android.content.Context;
import android.media.MediaPlayer;

import in.geobullet.csci_4176_project.R;

/**
 *
 * Singleton design that represents a sound manager for playing sound effects throughout the application
 *
 */

public class SoundManager {

    private static SoundManager instance = null;
    private MediaPlayer mediaPlayer;

    /**
     * private constructor to nullify instantiation
     */
    private SoundManager(){

    }

    /**
     * Get an instance of the sound manager.  If null creates one first and returns it.
     *
     * @return  The instance of the sound manager.
     */
    public static SoundManager getInstance(){
        if(instance == null){
            instance = new SoundManager();
        }

        return instance;
    }


    /**
     * Play the delete item sound effect
     *
     * @param context  The calling activities context
     */
    public void playDeleteItem(Context context){

        resetMediaPlayer();

        mediaPlayer = MediaPlayer.create(context, R.raw.delete_item);
        mediaPlayer.start();


    }

    /**
     * Play the error sound effect
     *
     * @param context The calling activities context
     */
    public void playError(Context context){

        resetMediaPlayer();

        mediaPlayer = MediaPlayer.create(context, R.raw.selection_wrong);
        mediaPlayer.start();
    }


    /**
     * Play the success sound effect
     *
     * @param context  The calling activities context
     */
    public void playSuccess(Context context){

        resetMediaPlayer();

        mediaPlayer = MediaPlayer.create(context, R.raw.selection_correct);
        mediaPlayer.start();
    }



    /**
     *
     * Method resets and releases the media player after the sound has been played.
     *
     * Should be called before playing a sound in order to stop multiple media player instances being
     * run in the application and causing an error where no sounds are played anymore.
     *
     * This fixes the issue encountered.
     *
     */
    public void resetMediaPlayer(){
        if(mediaPlayer != null) {

            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }

            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
