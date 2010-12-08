/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mq.scores;

import java.io.Serializable;

/**
 * @author Daniele
 */
public class Score implements Comparable<Score>, Serializable {
    
    public Score(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
    
    private String name;
    private int points;

    public int compareTo(Score o) {
        if (points > o.points) return -1;
        if (points < o.points) return 1;
        
        return name.compareTo(o.name);
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": ").append(points);
        return sb.toString();
    }
}
