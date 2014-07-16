package com.media.controllers;

import com.media.entities.NewsEntity;
import com.media.interfaces.NewsService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 * @author armdev
 *
 */
//@Named("newsController")
//@SessionScoped
public class NewsController implements NewsService, Serializable {

   //@PersistenceUnit(unitName="jpa_module")
   private EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public NewsController() {
        emf = Persistence.createEntityManagerFactory("jpa_module");
    }

    @Override
    public List<NewsEntity> getNewsList() {
         EntityManager em = emf.createEntityManager();
        List<NewsEntity> contacts = em.createQuery("SELECT o FROM NewsEntity o WHERE o.id > 0 ").getResultList();
        return contacts;
    }

    @Override
    public void createNews(NewsEntity newsEntity) {
       EntityManager em = emf.createEntityManager();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(newsEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
