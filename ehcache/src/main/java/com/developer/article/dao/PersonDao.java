package com.developer.article.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.developer.article.model.Person;

/**
 * Person DAO
 * @author Jacek Furmankiewicz
 */
@Repository
public class PersonDao extends GenericDaoImpl<Person,Integer>{

	private static final Logger LOG = Logger.getLogger(PersonDao.class);
	
}
