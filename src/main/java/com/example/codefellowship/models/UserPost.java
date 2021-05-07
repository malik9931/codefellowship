package com.example.codefellowship.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String body;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt")
    private Date createdAt;

//    private String timestamp;
    @ManyToOne
//    @JoinColumn(name = "application_user_id")
    private ApplicationUser user;


    public UserPost(){};
//
//    public UserPost(String body) {
//        this.body = body;
//        this.createdAt = getCreatedAt() ;
//    }

    public UserPost(String body, ApplicationUser applicationUser) {
        this.body = body;
        this.user = applicationUser;
    }


    //-------------Getter & Setter--------------
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}
