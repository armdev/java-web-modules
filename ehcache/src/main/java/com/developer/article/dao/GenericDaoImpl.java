package com.developer.article.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;


/**
* Generic DAO for Hibernate implementation 
*/
public abstract class GenericDaoImpl<E, PK extends Serializable> implements IGenericDao<E, PK> {

	private static final Logger LOG = Logger.getLogger(GenericDaoImpl.class);
	
	private Class<E> entityClass = null;
	private HibernateTemplate hibernateTemplate = null;
	
	@Autowired public void setDataSource(SessionFactory sf) {
		hibernateTemplate = new HibernateTemplate(sf);
	}
	
	@SuppressWarnings("unchecked") @Transactional 
	public PK save(E newInstance) {
		return (PK) hibernateTemplate.save(newInstance);
	}

	public E findByPrimaryKey(PK primaryKey) {
		return (E) hibernateTemplate.get(getEntityClass(), primaryKey);
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		return hibernateTemplate.findByCriteria(createDetachedCriteria());
	}

	@SuppressWarnings("unchecked")
	protected List<E> findAllByProperty(String propertyName, Object value) {
		DetachedCriteria criteria = createDetachedCriteria().add(Restrictions.eq(propertyName, value));
		return hibernateTemplate.findByCriteria(criteria);
	}
	
	protected E findByUniqueProperty(String propertyName,Object value) {
		List<E> all = findAllByProperty(propertyName, value);
		if (all.size() == 0) {
			return null;
		} else {
			return all.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected List<E> findByCriteria(DetachedCriteria criteria) {
		return hibernateTemplate.findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	protected List<E> find(String queryString, Object...values) {
		return hibernateTemplate.find(queryString, values);
	}
	
	public int bulkUpdate(String queryString, Object...values) {
		return hibernateTemplate.bulkUpdate(queryString, values);
	}
	
	@Transactional
	public void update(E transientObject) {
		hibernateTemplate.update(transientObject);
	}

	@Transactional
	public void saveOrUpdate(E transientObject) {
		hibernateTemplate.saveOrUpdate(transientObject);
	}

	@Transactional()
	public void delete(E persistentObject) {
		if (persistentObject != null) {
			hibernateTemplate.delete(persistentObject);
		} else {
			LOG.warn(getClass().getSimpleName() + ": attempt to delete NULL object");
		}
	}
	
	@Transactional
	public void deleteByPrimaryKey(PK primaryKey) {
		E entity = this.findByPrimaryKey(primaryKey);
		this.delete(entity);
	}
	
	@Transactional
	public void deleteAll() {
		hibernateTemplate.bulkUpdate("DELETE FROM " + getEntityClass().getName());
	}
	
	/**
	 * Locks an object for updating
	 * @param entity Entity
	 */
	@Transactional
	public void lock(E entity) {
		hibernateTemplate.lock(entity, LockMode.PESSIMISTIC_WRITE);
	}

	@SuppressWarnings("unchecked")
	public final Class<E> getEntityClass() {
		//use lazy initialization, obtain via reflection (a one time hit)
		if (this.entityClass == null) {
			ParameterizedType pType = (ParameterizedType) getClass().getGenericSuperclass();
			this.entityClass = (Class<E>) pType.getActualTypeArguments()[0];
		}
		return this.entityClass;
	}

	public DetachedCriteria createDetachedCriteria() {
		return DetachedCriteria.forClass(getEntityClass());
	}
	

}