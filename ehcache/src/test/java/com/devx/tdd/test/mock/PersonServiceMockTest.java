package com.devx.tdd.test.mock;

import static org.mockito.Mockito.*;
import static junit.framework.Assert.*;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.developer.article.dao.PersonDao;
import com.developer.article.model.Person;
import com.developer.article.service.PersonService;

public class PersonServiceMockTest {

	private PersonService service;
	private PersonDao dao;
	
	@Before
	public void setup() {
		service = new PersonService();
		dao = mock(PersonDao.class);
		service.setPersonDao(dao);
	}
	
	@Test
	public void testFindAll() {
		List<Person> all = new LinkedList<Person>();
		all.add(new Person(1,"John","Doe",null));
		all.add(new Person(2,"Jane","Doe",null));
		
		//return mocked result set on find
		when(dao.findAll()).thenReturn(all);
		
		//call the main method we want to test
		List<Person> result = service.findAll();
		
		//verify the method was called
		verify(dao).findAll();
		
		//verify correct result returned
		assertEquals(2,result.size());
		
		assertEquals(new Integer(1),result.get(0).getId());
		assertEquals("John",result.get(0).getFirstName());
		assertEquals("Doe",result.get(0).getLastName());
		
		assertEquals(new Integer(2),result.get(1).getId());
		assertEquals("Jane",result.get(1).getFirstName());
		assertEquals("Doe",result.get(1).getLastName());
	}
	
	@Test
	public void testNullReturnIfNoDataFound() {
		List<Person> all = new LinkedList<Person>();
		
		//return mocked result set on find
		when(dao.findAll()).thenReturn(all);
		
		//call the main method we want to test
		List<Person> result = service.findAll();
		
		//verify the method was called
		verify(dao).findAll();
		
		//verify null result was returned
		assertEquals(null,result);
	}
	
	@Test
	public void testDaoCalledOnlyOnce() {
		List<Person> all = new LinkedList<Person>();
		
		//return mocked result set on find
		when(dao.findAll()).thenReturn(all);
		
		//call the main method we want to test
		service.findAll();
		
		//verify the method was called exactly once
		verify(dao,times(1)).findAll();
		verify(dao, atMost(1)).findAll();
		verify(dao, atLeast(1)).findAll();
	}
	
	@Test
	public void testServerErrorReturnedOnFailedUpdate() {
		when(dao.findByPrimaryKey(1)).thenThrow(new RuntimeException("DB Failed"));
		
		Response r = service.update(1, "John","Doe","john.doe@gmail.com");
		
		assertEquals(500,r.getStatus());
	}
	
	@Test
	public void testResponsesOnFind() {
		//simulate Person ID = 1 in the DB
		when(dao.findByPrimaryKey(1)).thenReturn(new Person(1,"John","Doe",null));
		
		//test response when ID exists
		Response found = service.find(1);
		assertEquals(200, found.getStatus());
		
		//test response when ID does not exist in DB
		Response notFound = service.find(999);
		assertEquals(404, notFound.getStatus());
	}
}
