package com.dustdawn.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.dustdawn.bos.dao.base.IBaseDao;
import com.dustdawn.bos.utils.PageBean;
/**
 * 持久层实现
 * @author DUSTDAWN
 *
 * @param <T>
 * 继承HibernateDaoSupport获得模板对象
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T>{
	
	private Class<T> clazz;
	//使用注解后需要手动配置sessionFactory
	//根据对象的类型或名称注入
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	//父类(IBaseDaoImpl)的构造方法中动态获得字节码对象
	public BaseDaoImpl() {
		ParameterizedType ptclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class) ptclass.getActualTypeArguments()[0];
	}
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(this.clazz, id);
	}

	public List<T> findAll() {
		String hql = "from " + clazz.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}
	//执行更新
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		//为HQL语句中？赋值
		int i = 0;
		for(Object object : objects) {
			query.setParameter(i++, object);
		}
		
		query.executeUpdate();
	}
	/**
	 * 通用查询信息
	 */
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		
		
		this.getHibernateTemplate().findByCriteria(detachedCriteria);
		//改变查询方式
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long count = countList.get(0);
		pageBean.setTotal(count.intValue());
		
		detachedCriteria.setProjection(null);
		//指定Hibernate框架封装返回对象的方式
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		
		int firstResult = (currentPage-1)*pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		//设置当前页的数据集合
		pageBean.setRows(rows);
		
		
	}
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

}
