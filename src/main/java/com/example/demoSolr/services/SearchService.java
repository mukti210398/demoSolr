package com.example.demoSolr.services;

import com.example.demoSolr.entity.KafkaEntity;
import com.example.demoSolr.entity.Search;

import java.util.List;

public interface SearchService {
    Iterable<Search> findAll();
    List<Search> findByUserName(String searchName);
    Search save(Search searchItem);
    List<Search> findBySearchString(String searchTerm);
    Search searchAdd(KafkaEntity kafkaEntity);
    void deleteAll();
}
