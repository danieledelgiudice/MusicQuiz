/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mq;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mq.graphics.QuestionDialog.GameListener;
import mq.librarymanager.Library;
import mq.questionmanager.Question;
import mq.questionmanager.QuestionBuilder;

/**
 * @author Daniele
 */
public class Game {
    private QuestionBuilder questionBuilder;
    private Question currentQuestion;
    private GameListener listener;
    private int score = 0;
    private int lifes = 3;

    public Game(GameListener listener) {
        this.listener = listener;
        Library library = new Library();
        library.loadLibrary();
        questionBuilder = new QuestionBuilder(library);

        askQuestion();
    }

    public void answer(String guess) {
        if (currentQuestion.isRight(guess)) {
            score = score + (score * lifes / 5) + 10;
            listener.correctAnswer(score);
        } else {
            if (--lifes > 0) {
                listener.wrongAnswer(currentQuestion.getChoices().get(0), lifes);
            } else {
                listener.gameOver(score);
                return;
            }
        }

        new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    askQuestion();
                }
            }).start();
    }

    private void askQuestion() {
        currentQuestion = questionBuilder.makeQuestion();
        ArrayList<String> choices = currentQuestion.getChoices();
        for (int i = 0; i < choices.size(); i++ ) {
            String tmp;
            int r = (int) (Math.random() * 4);
            tmp = choices.get(i);
            choices.set(i, choices.get(r));
            choices.set(r, tmp);
        }
        listener.askQuestion(currentQuestion.getText(), choices , currentQuestion.getSongPath());
    }

}
