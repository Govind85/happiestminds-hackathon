package com.happiestminds.dictionary.controller;

import com.happiestminds.dictionary.entitity.Dictionary;
import com.happiestminds.dictionary.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping("/wordExistInDictionary")
    public ResponseEntity<?> wordExistInDictionary(@RequestParam String word) {
        try {
            if(null != dictionaryService.wordExistInDictionary(word.toLowerCase())) {
                return new ResponseEntity<>("Word exists in dictionary",HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Word does not exist in dictionary",HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/wordContainsInDictionary")
    public ResponseEntity<?> wordContainsInDictionary(@RequestParam String word) {
        try {
            List<Dictionary> words = new ArrayList<>();
            words = dictionaryService.wordContainsInDictionary(word.toLowerCase());
            return new ResponseEntity<>(words,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getAllWords")
    public ResponseEntity<?> wordExistInDictionary() {
        try {
            return new ResponseEntity<>(dictionaryService.getAllWords(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/saveWordInDictionary")
    public ResponseEntity<?> saveWordInDictionary(@RequestBody Dictionary word) {
        try {
            dictionaryService.saveWordInDictionary(word);
            return new ResponseEntity<>("Word saved successfully",HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/saveWordsInDictionary")
    public ResponseEntity<?> saveWordsInDictionary(@RequestBody List<Dictionary> words) {
        try {
            dictionaryService.saveWordsInDictionary(words);
            return new ResponseEntity<>("Words saved successfully",HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveWordsInDictionaryUsingTextFile")
    public ResponseEntity<?> saveWordsInDictionaryUsingTextFile(@RequestParam("words") MultipartFile multipartFile) {
        BufferedReader br;
        Set<String> wordTexts = new HashSet<>();
        String line;
        InputStream is;
        try {
            Set<String> fetchedWords = dictionaryService.getAllWords().stream().map(word -> new String(word.getWord())).collect(Collectors.toSet());
            is = multipartFile.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                List<String> wordTextList = Arrays.asList(line.split(" "));
                wordTexts.addAll(wordTextList);
            }
            wordTexts.removeAll(fetchedWords);
            List<Dictionary> words= wordTexts.stream().map(word -> new Dictionary(0,word)).collect(Collectors.toList());
            is.close();
            br.close();
            dictionaryService.saveWordsInDictionary(words);
            return new ResponseEntity<>("Words saved successfully",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
