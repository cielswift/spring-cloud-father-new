//package com.ciel.springcloudfathernewsso.jpa.repository.learns;
//
//import com.ciel.entity.AppEntity;
//
//import javax.persistence.EntityManager;
//
////@RepositoryDefinition(domainClass =AppEntity.class, idClass = Integer.class) //不通过接口
//public class ZCustomRepositoryImpl {
//
//	//@PersistenceContext() //通过工厂对象来创建并注入
//	private EntityManager em;
//
//	public AppEntity getById(Integer id) {
//		return em.find(AppEntity.class, 2);
//	}
//}