package com.happiestminds.dictionary.respository;

import com.happiestminds.dictionary.entitity.Dictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryRepository extends CrudRepository<Dictionary, Long> {
    public List<Dictionary> findByWordContains(String word);
    public Dictionary findByWord(String word);
}
