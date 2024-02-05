package com.example.Scrabble.Calculator.Entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//model Containing Name and points for high scores
@Entity
public class Score {
    //id in scoreboard
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Points in Scoreboard
    private int points;

    //Name of Scorer
    private String word;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getWord() {
        return word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Score(int points, String word) {
        this.points = points;
        this.word = word;
    }

    public Score() {
    }
}
