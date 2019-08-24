package com.happiestminds.dictionary.service;

import com.happiestminds.dictionary.entitity.Dictionary;
import com.happiestminds.dictionary.respository.DictionaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Override
    public List<Dictionary> saveWordsInDictionary(List<Dictionary> words) throws Exception{
            dictionaryRepository.saveAll(words);
            return words;
    }

    @Override
    public Dictionary saveWordInDictionary(Dictionary word) throws Exception{
        dictionaryRepository.save(word);
        return word;
    }

    @Override
    public Dictionary wordExistInDictionary(String word) throws Exception{
        return dictionaryRepository.findByWord(word);
    }

    @Override
    public List<Dictionary> wordContainsInDictionary(String word) throws Exception{
        return dictionaryRepository.findByWordContains(word);
    }

    @Override
    public List<Dictionary> getAllWords() throws Exception {
        return (List<Dictionary>)dictionaryRepository.findAll();
    }
}
