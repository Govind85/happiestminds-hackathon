package com.happiestminds.dictionary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiestminds.dictionary.controller.DictionaryController;
import com.happiestminds.dictionary.entitity.Dictionary;
import com.happiestminds.dictionary.service.DictionaryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.hamcrest.core.Is;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DictionaryController.class)
public class DictionaryApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DictionaryService dictionaryService;

    @Test
    public void getAllWordsTest() throws Exception {
        // given
        Dictionary dictionary = new Dictionary();
        dictionary.setWordId(1L);
        dictionary.setWord("Hi");
        List<Dictionary> words = new ArrayList<>();
        words.add(dictionary);
        Dictionary dictionary1 = new Dictionary();
        dictionary1.setWordId(2L);
        dictionary1.setWord("Hello");
        words.add(dictionary1);
        given(dictionaryService.getAllWords()).willReturn(words);

        // when + then
        this.mockMvc.perform(get("/api/dictionary/getAllWords"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].word", Is.is(dictionary.getWord())));
    }
    @Test
    public void wordContainsInDictionaryTest() throws Exception {
        // given
        List<Dictionary> words = new ArrayList<>();
        Dictionary dictionary = new Dictionary();
        dictionary.setWordId(1L);
        dictionary.setWord("Hello");
        words.add(dictionary);
        given(dictionaryService.wordContainsInDictionary("hel")).willReturn(words);

        // when + then
        this.mockMvc.perform(get("/api/dictionary/wordContainsInDictionary?word=hel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].word", Is.is(dictionary.getWord())));
    }
    @Test
    public void wordExistsInDictionaryTest() throws Exception {
        // given
        Dictionary dictionary = new Dictionary();
        dictionary.setWordId(1L);
        dictionary.setWord("Hi");
        List<Dictionary> words = new ArrayList<>();
        words.add(dictionary);
        Dictionary dictionary1 = new Dictionary();
        dictionary1.setWordId(2L);
        dictionary1.setWord("Hello");
        words.add(dictionary1);
        given(dictionaryService.wordExistInDictionary("hello")).willReturn(words.get(1));

        // when + then
        this.mockMvc.perform(get("/api/dictionary/wordExistInDictionary?word=hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word", Is.is(words.get(1).getWord())));
    }
    @Test
    public void saveWordInDictionaryBadRequestTest() throws Exception {
        // given
        Dictionary dictionary = new Dictionary();
        dictionary.setWordId(1L);
        dictionary.setWord("Hi");
        given(dictionaryService.saveWordInDictionary(dictionary)).willReturn(dictionary);
        // when + then
        this.mockMvc.perform(post("/api/dictionary/saveWordInDictionary"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void saveWordInDictionaryTest() throws Exception {
        // given
        Dictionary dictionary = new Dictionary();
        dictionary.setWordId(1L);
        dictionary.setWord("Hi");
        given(dictionaryService.saveWordInDictionary(dictionary)).willReturn(dictionary);
        // when + then
        this.mockMvc.perform(post("/api/dictionary/saveWordInDictionary").content(getJsonString(dictionary)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", Is.is("Word saved successfully")));
    }
    @Test
    public void saveWordsInDictionary() throws Exception {
        // given
        Dictionary dictionary = new Dictionary();
        dictionary.setWordId(1L);
        dictionary.setWord("Hi");
        List<Dictionary> words = new ArrayList<>();
        words.add(dictionary);
        Dictionary dictionary1 = new Dictionary();
        dictionary1.setWordId(2L);
        dictionary1.setWord("Hello");
        words.add(dictionary1);
        given(dictionaryService.saveWordsInDictionary(words)).willReturn(words);
        // when + then
        this.mockMvc.perform(post("/api/dictionary/saveWordsInDictionary").content(getJsonString(words)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", Is.is("Words saved successfully")));
    }
    public static String getJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonInput = objectMapper.writeValueAsString(object);
            return jsonInput;
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        return "";
    }

}
