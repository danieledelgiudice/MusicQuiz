/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mq.songplayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * @author Daniele
 */
public class SongPlayer {
    private static Player audioPlayer;

    public static void play(String songPath) {
        try {
            audioPlayer = new Player(new FileInputStream(songPath));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.err);
        } catch (JavaLayerException ex) {
            ex.printStackTrace(System.err);
        }
        
        new Thread() {
            @Override public void run() {
                try {
                    audioPlayer.play();
                } catch (JavaLayerException ex) {
                }
            }
        }.start();
    }

    public static void stop() {
        if (audioPlayer != null)
            audioPlayer.close();
    }
}
