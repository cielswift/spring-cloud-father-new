//package com.ciel.springcloudfathernewsso.jpa.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.NoRepositoryBean;
//
//@NoRepositoryBean
////确保添加了该注解的 repository 接口不会在运行时被创建实例(不要为此接口创建存储库代理bean)
//// 也就是说，使用了该注解的接口不会被单独创建实例，只会作为其他接口的父接口而被使用
//public interface BaseJpaRepository<T, ID> extends JpaRepository<T, ID> {
//
//    @Modifying
//    @Query("UPDATE #{#entityName} SET deleted = 1 WHERE id = :id") //全局逻辑删除
//    void deletedMy(Integer id);
//
//    @Query("select count(1) from #{#entityName} where deleted = 0")
//    Long countMy();
//
//}
