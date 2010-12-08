
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mq.questionmanager;

import java.util.ArrayList;
import mq.librarymanager.Fields;
import mq.librarymanager.Library;
import mq.librarymanager.Song;

/**
 * @author Daniele
 */
public class QuestionBuilder {
    private static final int CHOICES_NUMBER = 4;
    private Library library;

    public QuestionBuilder(Library library) {
        this.library = library;
    }

    public Question makeQuestion() {
        Fields knownField, guessField;
        Song questionSong, guessSong;
        String questionText = null;
        String songPath = null;
        ArrayList<String> choices = new ArrayList<String>();

        knownField = Fields.values()[((int)(Math.random() * 5)) % 4];
        do {
            guessField = Fields.values()[(int)(Math.random() * 4)];
        } while (knownField.equals(guessField));
        do {
            questionSong = library.getRandomSong();
        } while (questionSong.getProperty(knownField).equals("") ||
                 questionSong.getProperty(guessField).equals(""));
        switch(knownField) {
            case TITLE:
                songPath = questionSong.getProperty(Fields.PATH);
                switch(guessField) {
                    case ALBUM:
                        questionText = "A quale album appartiene il celebre brano " +
                                        questionSong.getProperty(knownField) + "?";
                        break;

                    case YEAR:
                        questionText = "In quale anno è stata pubblicata " +
                                        questionSong.getProperty(knownField) + "?";
                        break;

                    case ARTIST:
                        questionText = "Chi canta la stupenda " +
                                        questionSong.getProperty(knownField) + "?";
                        break;
                }
                break;

            case ALBUM:
                switch(guessField) {
                    case TITLE:
                        questionText = "Quale di queste canzoni appartiene all'album " +
                                        questionSong.getProperty(knownField) + "?";
                        break;

                    case YEAR:
                        questionText = "In quale anno è stato creato l'album " +
                                        questionSong.getProperty(knownField) + "?";
                        break;

                    case ARTIST:
                        questionText = "Chi ha creato l'album " +
                                        questionSong.getProperty(knownField) + "?";
                        break;
                }
                break;

            case YEAR:
                switch(guessField) {
                    case TITLE:
                        questionText = "Quale di queste canzoni è stata rilasciata nel " +
                                        questionSong.getProperty(knownField) + "?";
                        break;

                    case ALBUM:
                        questionText = "Quale album ha visto la luce nel " +
                                        questionSong.getProperty(knownField) + "?";
                        break;

                    case ARTIST:
                        return makeQuestion();
                }
                break;

            case ARTIST:
                switch(guessField) {
                    case TITLE:
                        questionText = "Quale di questi brani è interpetato da " +
                                        questionSong.getProperty(knownField) + "?";
                        break;

                    case ALBUM:
                        questionText = "Quale di questi album è stato creato da " +
                                        questionSong.getProperty(knownField) + "?";
                        break;

                    case YEAR:
                        return makeQuestion();
                }
                break;

            default:
                return makeQuestion();
        }
        
        choices.add(questionSong.getProperty(guessField));

        for (int i = 0; i < CHOICES_NUMBER - 1; i++) {
            do {
                do {
                    guessSong = library.getRandomSong();
                } while (guessSong.getProperty(knownField).equals(questionSong.getProperty(knownField)));
            } while (choices.contains(guessSong.getProperty(guessField)) || guessSong.getProperty(guessField).equals(""));
            choices.add(guessSong.getProperty(guessField));
        }

        return new Question(questionText, choices, songPath);
    }



}
