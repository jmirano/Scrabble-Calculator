package com.example.Scrabble.Calculator.Controllers;

import com.example.Scrabble.Calculator.Entities.Score;
import com.example.Scrabble.Calculator.Exceptions.BusinessLogicException;
import com.example.Scrabble.Calculator.Services.ScrabbleCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ScrabbleCalculatorController {

    private static final Logger logger = LoggerFactory.getLogger(ScrabbleCalculatorController.class);

    private final ScrabbleCalculatorService scrabbleCalculatorService;

    @Autowired
    public ScrabbleCalculatorController(ScrabbleCalculatorService scrabbleCalculatorService) {
        this.scrabbleCalculatorService = scrabbleCalculatorService;
    }

    @PostMapping("/CalculateScore")
    public ResponseEntity<?> CalculateScore(@RequestBody Score requestBody) throws Exception {
        try {
            return ResponseEntity.ok(scrabbleCalculatorService.getScrabbleWordScore(requestBody));
        } catch (BusinessLogicException e) {
            logger.error("Business logic exception:", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            // Log other exceptions and rethrow for global exception handling
            logger.error("An unexpected error occurred:", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }


    @PostMapping("/InsertScore")
    public ResponseEntity<?> InsertScore(@RequestBody Score requestBody) {

        try {
            scrabbleCalculatorService.saveAndUpdateScores(requestBody);
            return ResponseEntity.ok("Score Saved");
        } catch (Exception e) {
            logger.error("An unexpected error occurred:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/GetScores")
    public ResponseEntity<?> GetScores() throws Exception {
        try {
            return ResponseEntity.ok(scrabbleCalculatorService.getHighScores());
        } catch (Exception e) {
            logger.error("An unexpected error occurred:", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
