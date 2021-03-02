package com.example.demoSolr.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "pbsearch")
public class Search {
    @Id
    @Indexed(name="userId",type="String")
    private String userId;
    @Indexed(name="username",type="String")
    private String username;
    @Indexed(name="profileImage",type="String")
    private String profileImage;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    @Override
    public String toString() {
        return "Search{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }
}
