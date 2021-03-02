package com.example.demoSolr.services.serviceimpl;

import com.example.demoSolr.entity.KafkaEntity;
import com.example.demoSolr.entity.Search;
import com.example.demoSolr.repository.SearchRepository;
import com.example.demoSolr.services.EdismaxQuery;
import com.example.demoSolr.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@EnableKafka
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    SearchRepository searchRepository;
    @Autowired
    private SolrTemplate solrTemplate;
    private String solrCoreName="pbsearch";

    @Override
    public Iterable<Search> findAll() {
        return  searchRepository.findAll();
    }

    @Override
    public List<Search> findByUserName(String searchName) {
        return searchRepository.findByUserName(searchName) ;
    }

    @Override
    public Search save(Search search) {
        return searchRepository.save(search);
    }

    @Override
    public List<Search> findBySearchString(String searchTerm) {
        if(searchTerm.isEmpty())
            return new ArrayList<Search>();
        searchTerm=searchTerm.replaceAll("[^a-zA-Z0-9]"," ");
        searchTerm="*"+searchTerm+"*";
        if(searchTerm.length()>3)
            searchTerm=searchTerm+"~";
        EdismaxQuery edismaxQuery = new SimpleEdismaxQuery();
        edismaxQuery.addCriteria(new SimpleStringCriteria(searchTerm));
        edismaxQuery.setPageRequest(PageRequest.of(0, 10));
        edismaxQuery.addQueryField("username");
        Page<Search> results = solrTemplate.query(solrCoreName, edismaxQuery, Search.class);
        List<Search> searchResults=new ArrayList<>();
        for (Search product : results) {
            searchResults.add(product);
        }
        return searchResults;
    }
 //   @KafkaListener(topics="SearchDelete", groupId = "${spring.kafka.consumer.group-id}")
    public void removeSearchItem(KafkaEntity kafkaEntity){
        if(searchRepository.findById(kafkaEntity.getUserId())!=null) {
            searchRepository.deleteById(kafkaEntity.getUserId());
        }
    }

 //   @KafkaListener(topics="SearchUpdate", groupId = "${spring.kafka.consumer.group-id}")
    public void searchUpdate(KafkaEntity kafkaEntity){
        List<Search> searchItems=searchRepository.findBySearchString(kafkaEntity.getUserId());
        if(!searchItems.isEmpty()){
            Search searchItem=searchItems.get(0);
            searchRepository.save(searchItem);
        }
    }

//    @KafkaListener(topics="SearchAdd", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public Search searchAdd(KafkaEntity kafkaEntity){
        System.out.println("In add");
        Search searchItem=new Search();
        searchItem.setUserId(kafkaEntity.getUserId());
        searchItem.setUserName(kafkaEntity.getUsername());
        searchItem.setProfileImage(kafkaEntity.getProfileImage());
        return searchRepository.save(searchItem);
    }

    @Override
    public void deleteAll() {
        System.out.println("Delete all");
        searchRepository.deleteAll();
    }

}

