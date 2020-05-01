//package com.ciel.springcloudfathernewsso.jpa.repository;
//
//import com.ciel.entity.AppEntity;
//import com.ciel.entity.UserEntity;
//import com.ciel.service.AppService;
//import com.ciel.springcloudcommon.dao.AppDao;
//import com.ciel.springcloudcommon.dao.UserDao;
//import com.ciel.springcloudcommon.dao.learns.ARepositoryMy;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class AppServiceImpl implements AppService {
//
//    @Override
//    public void test() {
//        Long aLong = appDao.countMy();
//        appDao.deletedMy(1);
//    }
//
//    @Override
//    public void test2() {
//
//        List<AppEntity> all = appDao.findAll();
//
//        Optional<UserEntity> byId = userDao.findById(1);
//        UserEntity one = userDao.getOne(1);
//
//       // List<AppEntity> apps = byId.getApps();
//
//        List<AppEntity> apps1 = one.getApps();
//    }
//
//
//    @Override
//    public void test3(){
//
//        UserEntity userEntity = userDao.findById(2).get();
//
//        AppEntity appEntity = new AppEntity();
//        appEntity.setName("印度神油");
//        appEntity.setUser(userEntity);
//
//        appDao.save(appEntity);
//    }
//
//
//    @Override
//    public List<AppEntity> getByWapper() throws JsonProcessingException {
//
//        Sort.Order o1 = new Sort.Order(Sort.Direction.DESC,"id");
//        Sort.Order o2 = new Sort.Order(Sort.Direction.ASC,"name");
//
//        Sort s2 = new Sort(o1,o2);
//
//        //Sort s2 = new Sort(Sort.Direction.DESC,"id","name");
//
//        List<AppEntity> all = appDao.findAll(s2); //排序
//
//        Page<AppEntity> findAll = appDao.findAll(PageRequest.of(2, 10,s2)); //排序分页
//        System.err.println(findAll.getTotalElements()); //总条数
//		System.err.println(findAll.getTotalPages()); //总页
//		System.err.println(findAll.getNumber());  //当前页
//        findAll.getContent(); //结果
//        findAll.getSize(); //每页数量
//
//        	Specification<AppEntity> sp = new Specification<AppEntity>() {
//			/**
//			 * Root<Users> root:查询对象的属性的封装。
//			 * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
//			 * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
//			 */
//			@Override
//			public Predicate toPredicate(Root<AppEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//				//Predicate 查询条件;
//
//				List<Predicate> ls = new LinkedList<>();
//				ls.add(criteriaBuilder.like(root.get("name"), "夏%"));
//				ls.add(criteriaBuilder.gt(root.get("id"), 3));
//
//				Predicate [] ps = new Predicate[ls.size()];
//				Predicate and = criteriaBuilder.and(ls.toArray(ps));
//
//				return and;
//				//return criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "夏%"),criteriaBuilder.gt(root.get("id"), 3));
//				//return criteriaBuilder.like(root.get("name").as(String.class), "夏%");
//			}
//		};
//
//        Page<AppEntity> findAll1 = appDao.findAll(sp, PageRequest.of(2,10,s2)); //条件+ 分页 + 排序
//
//        ExampleMatcher matcher = ExampleMatcher.matching()
//		 .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())//模糊查询匹配开头，即{username}%
//		 .withMatcher("address" , ExampleMatcher.GenericPropertyMatchers.contains())//全部模糊查询，即%{address}% ,还有endsWith();
//		.withIgnorePaths("id");//忽略字段，即不管password是什么值都不加入查询条件
//
//        AppEntity appEntity = new AppEntity();
//        appEntity.setName("aaa");
//        Example<AppEntity> example = Example.of(appEntity,matcher);
//
//        appDao.findAll(example);
//
//
//        aRepositoryMy.lunima(1,"避").get(0).getUser();
//        aRepositoryMy.cnm("xia",1);
//
//        List<AppEntity> all1 = appDao.findAll();
//
//        List<UserEntity> all2 = userService.getByWapper();
//
//        return null;
//    }
//
//    @Autowired
//    private AppDao appDao;
//
//    @Autowired
//    private ARepositoryMy aRepositoryMy;
//
//    @Autowired
//    private UserDao userDao;
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    @Override
//    public List<AppEntity> getAll() {
//
//        List<AppEntity> all = appDao.findAll();
//        return all;
//    }
//    @Override
//    public AppEntity byId(Integer id) {
//        Optional<AppEntity> byId = appDao.findById(id);
//        return byId.get();
//    }
//}
