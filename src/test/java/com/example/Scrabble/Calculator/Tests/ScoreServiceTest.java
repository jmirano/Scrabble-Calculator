package com.example.Scrabble.Calculator.Tests;

import com.example.Scrabble.Calculator.Entities.Score;
import com.example.Scrabble.Calculator.Exceptions.BusinessLogicException;
import com.example.Scrabble.Calculator.Repositories.ScoreRepository;
import com.example.Scrabble.Calculator.Services.ScrabbleCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScoreServiceTest {

    @Mock
    private ScoreRepository scoreRepository;

    @InjectMocks
    private ScrabbleCalculatorService scrabbleCalculatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAndUpdateScores_existingScore() {
        Score existingScore = new Score(20, "EXISTING");
        when(scoreRepository.findByWord("EXISTING")).thenReturn(existingScore);

        Score requestBody = new Score(30, "EXISTING");
        assertDoesNotThrow(() -> scrabbleCalculatorService.saveAndUpdateScores(requestBody));

        verify(scoreRepository, never()).save(requestBody);
    }

    @Test
    void saveAndUpdateScores_newScore() {
        when(scoreRepository.findByWord("NEW")).thenReturn(null);

        Score requestBody = new Score(40, "NEW");
        assertDoesNotThrow(() -> scrabbleCalculatorService.saveAndUpdateScores(requestBody));

        verify(scoreRepository, times(1)).save(requestBody);
    }

    @Test
    void getHighScores_successful() {
        List<Score> highScores = new ArrayList<>();
        when(scoreRepository.findTop10ByOrderByPointsDesc()).thenReturn(highScores);

        assertDoesNotThrow(() -> scrabbleCalculatorService.getHighScores());

        verify(scoreRepository, times(1)).findTop10ByOrderByPointsDesc();
    }

    @Test
    void getHighScores_exception() {
        when(scoreRepository.findTop10ByOrderByPointsDesc()).thenThrow(new RuntimeException("Database error"));

        assertThrows(Exception.class, () -> scrabbleCalculatorService.getHighScores());
    }

}
