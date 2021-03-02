package com.example.demoSolr.repository;

import com.example.demoSolr.entity.Search;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends SolrCrudRepository<Search,String> {
    @Query("?0")
    List<Search> findByUserName(String userName);
    @Query("?0")
    List<Search> findBySearchString(String searchTerm);
}
