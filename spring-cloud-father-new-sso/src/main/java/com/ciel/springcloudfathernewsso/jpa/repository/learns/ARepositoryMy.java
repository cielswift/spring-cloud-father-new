//package com.ciel.springcloudfathernewsso.jpa.repository.learns;
//
//import com.ciel.entity.AppEntity;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.Repository;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//@org.springframework.stereotype.Repository
//public interface ARepositoryMy extends Repository<AppEntity, Integer> {
//
//	List<AppEntity> findAll(); //名称遵循驼峰 ,findBy + 属性名称(首大写)  + 查询条件(首大写) Equals 或者Ge 或者 Lt
//	//GE是大于等于号（&gt;=），GT是大于号(&gt;)，LE是小于等于号(<=),LT是小于号(<)
//	List<AppEntity> findByIdAndNameLike(int a, String name);
//
//	AppEntity save(AppEntity p);
//
//	@Query(value="from AppEntity where id = ?1 and name like %?2%")  //hql  参数顺序,like
//	List<AppEntity>  lunima(Integer i, String name);
//
//	@Query(value="select * from AppEntity where id = ? and name = ?",nativeQuery = true)
//	//nativeQuery = true 是一个标准的sql 不需要转换
//	List<AppEntity>  luniquanjianv(Integer i, String name);
//
//
//	@Modifying //当前语句是一个更新语句
//	@Query(value="update AppEntity set name = :name where id = :id")  //jpql  参数绑定 like
//	void cnm(@Param("name") String name, @Param("id") Integer id);
//
//	//原始的接口
//}