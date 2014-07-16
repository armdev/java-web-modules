package com.backend.mongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;

@Configuration
public class SpringMongoConfig2 {

	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new Mongo(), "mkyong");
	}

	public @Bean
	MongoTemplate mongoTemplate() throws Exception {
		
		//remove _class
		//MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext());
		//converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		//MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), converter);
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
				
		return mongoTemplate;
		
	}
	
	

}