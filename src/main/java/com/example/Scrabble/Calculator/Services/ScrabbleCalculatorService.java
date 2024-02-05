package com.example.Scrabble.Calculator.Services;

import com.example.Scrabble.Calculator.Entities.Score;
import com.example.Scrabble.Calculator.Exceptions.BusinessLogicException;
import com.example.Scrabble.Calculator.Repositories.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ScrabbleCalculatorService {

    private static final Logger logger = LoggerFactory.getLogger(ScrabbleCalculatorService.class);

    private final ScoreRepository scoreRepository;

    private Map<Character, Integer> letterScores = new HashMap<>();

    @Autowired
    public ScrabbleCalculatorService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
        this.letterScores.put('A', 1);
        this.letterScores.put('B', 3);
        this.letterScores.put('C', 3);
        this.letterScores.put('D', 2);
        this.letterScores.put('E', 1);
        this.letterScores.put('F', 4);
        this.letterScores.put('G', 2);
        this.letterScores.put('H', 4);
        this.letterScores.put('I', 1);
        this.letterScores.put('J', 8);
        this.letterScores.put('K', 6);
        this.letterScores.put('L', 1);
        this.letterScores.put('M', 3);
        this.letterScores.put('N', 1);
        this.letterScores.put('O', 1);
        this.letterScores.put('P', 3);
        this.letterScores.put('Q', 10);
        this.letterScores.put('R', 1);
        this.letterScores.put('S', 1);
        this.letterScores.put('T', 1);
        this.letterScores.put('U', 1);
        this.letterScores.put('V', 4);
        this.letterScores.put('W', 4);
        this.letterScores.put('X', 8);
        this.letterScores.put('Y', 4);
        this.letterScores.put('Z', 10);
    }

    public void saveAndUpdateScores(Score requestBody){
        try {
            Score existingScore = scoreRepository.findByWord(requestBody.getWord());

            if(existingScore == null) {
                scoreRepository.save(requestBody);
            }
        } catch (Exception e) {
            logger.info("Error in saving high scores: " + e.getMessage());
            throw e;
        }

    }

    public List<Score> getHighScores() throws Exception{
        try {
            return scoreRepository.findTop10ByOrderByPointsDesc();
        } catch (Exception e) {
            logger.info("Error in fetching high scores: " + e.getMessage());
            throw e;
        }

    }

    public Score getScrabbleWordScore(Score score) throws Exception {
        try {
            Score calculatedWord = calculateScrabbleWordScore(score.getWord());
            if(score.getPoints() != calculatedWord.getPoints()) {
                logger.info("Score Mismatch");
                throw new BusinessLogicException("Score Mismatch");
            } else {
                return  calculatedWord;
            }
        } catch (Exception e){
            throw e;
        }

    }

    private Score calculateScrabbleWordScore(String word) throws Exception {
        int totalPoints = 0;
        try {
            if(!isValidWord(word)) {
                throw new BusinessLogicException("Invalid Word contains illegal characters");
            }

            for (char letter : word.toCharArray()) {
                totalPoints += this.letterScores.get(letter);
            }
            return new Score(totalPoints, word);

        } catch (Exception e) {
            logger.info("Error in calculating score: " + e.getMessage());
            throw e;
        }
    }

    private static boolean isValidWord(String word) {
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

}
