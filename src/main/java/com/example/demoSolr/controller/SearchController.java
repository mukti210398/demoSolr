package com.example.demoSolr.controller;

import com.example.demoSolr.entity.KafkaEntity;
import com.example.demoSolr.entity.Search;
import com.example.demoSolr.services.SearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @DeleteMapping("/deleteAll")
    void deleteAll()
    {
        searchService.deleteAll();
    }
    @GetMapping("/findAll")
    Iterable<Search> findAll()
    {
        return searchService.findAll();
    }

    @GetMapping("/find/{username}")
    Iterable<Search> findUser(@PathVariable String username)
    {
        return searchService.findByUserName(username);
    }

    @PostMapping("/save")
    Search save(@RequestBody Search search)
    {
        return searchService.save(search);
    }

    @PostMapping(value="/searchString/{string}")
    public List<Search> findByFirstName(@PathVariable("string") String searchTerm){
        return searchService.findBySearchString(searchTerm);
    }

    @PostMapping("/addUser")
    public Search addUser(@RequestBody KafkaEntity search)
    {
        return searchService.searchAdd(search);
    }


    @KafkaListener(topics = "newuser",groupId = "group_id")
    public void getNewUser(String string)
    {

        ObjectMapper objectMapper = new ObjectMapper();
        Search search = null;
        try {
            search=objectMapper.readValue(string.getBytes(),Search.class);
            System.out.println(search.toString());
        }catch (Exception e)
        {

            System.out.println(e.toString());
        }

        searchService.save(search);
    }

}
