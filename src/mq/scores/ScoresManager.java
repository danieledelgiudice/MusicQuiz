/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mq.scores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Daniele
 */
public class ScoresManager {
    private ArrayList<Score> personalScores;

    public ScoresManager() {
        if (new File(serializationFilename).exists()) {
            loadScores();
        } else {
            personalScores = new ArrayList<Score>();
            personalScores.add(new Score("D4n13le", 1));
        }
    }

    public void addScore(String name, int points) {
        personalScores.add(new Score(name, points));
        savePersonalScores();

        //upload the new score
        saveOnlineScore(name, points);

    }

    private boolean savePersonalScores() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(
                                        new FileOutputStream(serializationFilename));
            os.writeObject(personalScores);
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    private static boolean saveOnlineScore(String name, int points) {
        try {
            URL url = new URL("http://www.d4n1musicquiz.altervista.org/add.php?A=" + name + "&B=" + points);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String output = in.readLine();
            if (!output.contains("Aggiunto"))
                return false;

            in.close();
        }
        catch (MalformedURLException e) { return false;}
        catch (IOException e) { return false; }
        return true;
    }

    private boolean loadScores() {
         try {
            ObjectInputStream is = new ObjectInputStream(
                                       new FileInputStream(serializationFilename));
            personalScores = (ArrayList<Score>) is.readObject();

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            return false;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    public double getAverage() {
        double sum = 0;
        for (Score score : personalScores) {
            sum += score.getPoints();
        }
        return sum / personalScores.size();
    }

    public int getPlayedGamesCount() {
        return personalScores.size();
    }

    public int getLast() {
        return personalScores.get(personalScores.size() - 1).getPoints();
    }

    public List<Score> getBestPersonalScores() {
        List<Score> bestScores = (List<Score>) personalScores.clone();
        Collections.sort(bestScores);
        
        return bestScores;
    }

    public static List<Score> getBestOnlineScores() {
        List<Score> bestScores = new ArrayList<Score>();
        try {
            URL url = new URL("http://www.d4n1musicquiz.altervista.org/best.php");

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String output = in.readLine();
            for (String line : output.split("<br>"))
            {
                bestScores.add(new Score(line.split(" ")[0], Integer.parseInt(line.split(" ")[1])));
            }
            in.close();
            return bestScores;
        }
        catch (MalformedURLException e) { }
        catch (IOException e) {}
        return null;
    }

    private static final String serializationFilename = "scores.ser";
}
