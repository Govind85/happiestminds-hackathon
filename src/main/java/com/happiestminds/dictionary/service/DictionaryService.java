package com.happiestminds.dictionary.service;

import com.happiestminds.dictionary.entitity.Dictionary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DictionaryService {

    public List<Dictionary> saveWordsInDictionary(List<Dictionary> words) throws Exception;

    public Dictionary saveWordInDictionary(Dictionary word) throws Exception;

    public Dictionary wordExistInDictionary(String word) throws Exception;
    public List<Dictionary> wordContainsInDictionary(String word) throws Exception;
    public List<Dictionary> getAllWords() throws Exception;
}
