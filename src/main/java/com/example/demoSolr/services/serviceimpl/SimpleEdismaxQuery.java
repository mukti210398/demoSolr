package com.example.demoSolr.services.serviceimpl;

import com.example.demoSolr.services.EdismaxQuery;
import org.springframework.data.solr.core.query.SimpleQuery;

public class SimpleEdismaxQuery extends SimpleQuery implements EdismaxQuery {

    private String queryField = "";

    private String minimumMatch;

    public String getQueryField() {
        return queryField;
    }

    public String getMinimumMatch() {
        return minimumMatch;
    }

    public void addQueryField(String fieldName) {
        addQueryField(fieldName, -1);
    }

    public void addQueryField(String fieldName, double boost) {
        String qf = fieldName;
        if (queryField.isEmpty())
            queryField = qf;
        else
            queryField += " " + qf;
    }
}


