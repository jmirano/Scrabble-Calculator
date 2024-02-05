package com.example.Scrabble.Calculator.Repositories;

import com.example.Scrabble.Calculator.Entities.Score;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface ScoreRepository extends CrudRepository<Score, Long> {
    // for getting scores
    public List<Score> findTop10ByOrderByPointsDesc();

    //for removing unnecessary scores.
    public ArrayList<Score> findAllByOrderByPointsDesc();

    public Score findByWord(String word);

}
