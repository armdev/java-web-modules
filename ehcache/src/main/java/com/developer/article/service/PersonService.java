package com.developer.article.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.article.dao.PersonDao;
import com.developer.article.model.Person;

@Service("personService")
@Path(PersonService.PATH)
public class PersonService {

    public static final String PATH = "/person";
    private PersonDao dao;

    @Autowired
    public void setPersonDao(PersonDao dao) {
        this.dao = dao;
    }

    @GET
    public List<Person> findAll() {
        List<Person> people = dao.findAll();
        return (people.size() > 0) ? people : null;
    }

    @GET
    @Path("{id}")
    public Response find(@PathParam("id") Integer id) {
        Person p = dao.findByPrimaryKey(id);
        if (p != null) {
            return Response.ok(p).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response add(@FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("email") String email) {

        try {

            Person person = new Person();
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setEmail(email);
            Integer id = dao.save(person);

            return Response.created(UriBuilder.fromPath(PATH + "/{0}").build(id)).entity(person).build();

        } catch (Exception ex) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("email") String email) {

        try {
            Person person = dao.findByPrimaryKey(id);
            if (person != null) {
                if (firstName != null) {
                    person.setFirstName(firstName);
                }
                if (lastName != null) {
                    person.setLastName(lastName);
                }
                if (email != null) {
                    person.setEmail(email);
                }
                dao.update(person);
                return Response.ok(person).build();
            } else {
                return Response.noContent().build();
            }
        } catch (Exception e) {
            return Response.serverError().build();
        }

    }

    @DELETE
    public Response deleteAll() {
        try {
            dao.deleteAll();
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        try {
            dao.deleteByPrimaryKey(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
