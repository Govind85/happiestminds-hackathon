package com.happiestminds.dictionary.entitity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dictionary_words")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "word_id")
    private long wordId;
    @Column(name="word")
    private String word;


}
