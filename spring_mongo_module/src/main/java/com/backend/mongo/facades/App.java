package com.backend.mongo.facades;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.backend.mongo.config.SpringMongoConfig;
import com.backend.mongo.entities.UserEntity;
import org.springframework.context.support.GenericXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        // For XML
        // ApplicationContext ctx = new GenericXmlApplicationContext("mongo-config.xml");



        // For Annotation
        ApplicationContext ctx = new AnnotationConfigApplicationContext(
                SpringMongoConfig.class);

        MongoOperations mongoOperation = (MongoOperations) ctx
                .getBean("mongoTemplate");

        UserEntity user = new UserEntity();
        user.setPassword("bublik");
        user.setUsername("kubrik");

        // save
        mongoOperation.save(user, "users");

        // find
        UserEntity savedUser = mongoOperation.findOne(
                new Query(Criteria.where("username").is("armdev")), UserEntity.class,
                "users");

        System.out.println("savedUser : " + savedUser);

        // update
        mongoOperation.updateMulti(
                new Query(Criteria.where("username").is("mkyong")),
                Update.update("password", "passik"), "users");

        // find
        UserEntity updatedUser = mongoOperation.findOne(
                new Query(Criteria.where("username").is("armdev")), UserEntity.class,
                "users");

        System.out.println("updatedUser : " + updatedUser);

        // delete
        mongoOperation.remove(
                new Query(Criteria.where("username").is("mkyong")), "users");

        // List
        List<UserEntity> listUser = mongoOperation.findAll(UserEntity.class, "users");
        System.out.println("Number of user = " + listUser.size());

    }
}
