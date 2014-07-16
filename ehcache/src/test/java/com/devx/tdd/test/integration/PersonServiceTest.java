package com.devx.tdd.test.integration;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.httpclient.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class PersonServiceTest {

	public static final String URI = "http://localhost:8080/services/person";

	@Before
	public void setup() throws HttpException, IOException {
		//delete all Person data
		Client client = new Client();
		WebResource wr = client.resource(URI);
		wr.delete();
	}

	@Test
	public void testCRUD() throws HttpException, IOException, JSONException {

		Client client = new Client();
		WebResource wr = client.resource(URI);

		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("firstName", "John");
		formData.add("lastName", "Doe");
		formData.add("email", "john.doe@gmail.com");
		ClientResponse response = wr.type("application/x-www-form-urlencoded")
				.post(ClientResponse.class, formData);
		
		assertEquals(201, response.getStatus()); // 201 = Created
		String uri = response.getHeaders().getFirst("Location");

		//verify it exists
		WebResource personResource = client.resource(uri);
		response = personResource.get(ClientResponse.class);
		assertEquals(200, response.getStatus()); // 200 = OK
		String json = response.getEntity(String.class);
		
		JSONObject jo = new JSONObject(json).getJSONObject("Person");
		assertEquals("John", jo.get("firstName"));
		assertEquals("Doe", jo.get("lastName"));
		assertEquals("john.doe@gmail.com", jo.get("email"));
		
		//perform an update, one field at a time and test it
		formData.clear();
		formData.add("email","john.doe@hotmail.com");
		response = personResource.type("application/x-www-form-urlencoded").put(ClientResponse.class, formData);
		assertEquals(200, response.getStatus()); 
		
		//verify it got updated
		response = personResource.get(ClientResponse.class);
		assertEquals(200, response.getStatus()); 
		json = response.getEntity(String.class);
		jo = new JSONObject(json).getJSONObject("Person");
		assertEquals("John", jo.get("firstName"));
		assertEquals("Doe", jo.get("lastName"));
		assertEquals("john.doe@hotmail.com", jo.get("email"));
		
		//delete it
		response = personResource.delete(ClientResponse.class);
		assertEquals(200, response.getStatus());
		
		//verify it does not exist
		response = personResource.get(ClientResponse.class);
		assertEquals(404, response.getStatus()); //204 = no content
	}

}
