package com.example.demoSolr.services;

import org.springframework.data.solr.core.query.FilterQuery;
import org.springframework.data.solr.core.query.Query;

public interface EdismaxQuery extends Query, FilterQuery {
    String getQueryField();

    void addQueryField(String fieldName);

    void addQueryField(String fieldName, double boost);

}
