package mq.librarymanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;
import mq.graphics.LoadLibraryDialog.LoadLibraryListener;
import mq.tagmanager.TagScanner;
/**
 * @author Daniele
 */
public class Library {
    public Library() {
        songs = new ArrayList<Song>();
    }

    public Song getRandomSong() {
        return songs.get((int)(Math.random() * songs.size()));
    }

    public boolean loadLibrary() {
        try {
            ObjectInputStream is = new ObjectInputStream(
                                       new FileInputStream(serializationFilename));
            songs = (ArrayList<Song>) is.readObject();

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            return false;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    public boolean saveLibrary() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(
                                        new FileOutputStream(serializationFilename));
            os.writeObject(songs);
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    public int loadLibrary(final File musicDirectory) {
        int songsCount = countSongs(musicDirectory);
        new Thread(new Runnable() {
            public void run() {
                loadDirectory(musicDirectory);
            }
        }).start();
        return songsCount;
    }

    private void loadDirectory(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                try {
                    loadDirectory(file);
                } catch (Exception ex) {}
                continue;
            }
            if (file.getName().toLowerCase().endsWith(".mp3")) {
                try {
                    songs.add(TagScanner.analyze(file));
                } catch ( Exception ex ) {
                    ex.printStackTrace(System.err);
                }
                listener.update(++songsProcessed);
            }
        }
    }
    
    public void addLoadLibraryListener(LoadLibraryListener listener) {
        this.listener = listener;
    }

    private int countSongs(File directory) {
        int count = 0;
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                System.out.println(file.getPath());
                try {
                    count += countSongs(file);
                } catch (Exception ex) {}
                continue;
            }
            if (file.getName().toLowerCase().endsWith(".mp3")) {
                count++;
            }
        }

        return count;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Library object contains:\n");
        for (Song song : songs) {
            sb.append(song).append("\n");
        }
        return sb.toString();
    }

    private List<Song> songs;
    private static final String serializationFilename = "library.ser";
    private LoadLibraryListener listener;
    private int songsProcessed = 0;
}
