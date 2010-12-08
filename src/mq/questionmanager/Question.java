/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mq.questionmanager;
import java.util.ArrayList;

/**
 * @author Daniele
 */
public class Question {
    public Question(String text, ArrayList<String> choices, String songPath) {
        this.text = text;
        this.choices = choices;
        this.songPath = songPath;
    }

    public ArrayList<String> getChoices() {
        return (ArrayList<String>) choices.clone();
    }

    public boolean isRight(String guess) {
        return guess.equals(choices.get(0));
    }

    public String getText() {
        return text;
    }

    public String getSongPath() {
        return songPath;
    }
    
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(text).append("\n");
        int j = 1;
        for (String choice : choices) {
            sb.append("  ").append(j++).append(") ").append(choice);
        }
        sb.append("\n\n");
        return sb.toString();
    }

    private String text;
    private ArrayList<String> choices;
    private String songPath;
}
