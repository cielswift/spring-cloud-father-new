//package com.ciel.springcloudfathernewsso.jpa.repository;
//
//import com.ciel.entity.LanguageEntity;
//import com.ciel.entity.UserEntity;
//import com.ciel.service.UserService;
//import com.ciel.springcloudcommon.dao.LangDao;
//import com.ciel.springcloudcommon.dao.UserDao;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@Transactional
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserDao userDao;
//
//    @Autowired
//    private LangDao langDao;
//
//    @Override
//    public List<UserEntity> getAll() {
//        return userDao.findAll();
//    }
//
//    @Override
//    public List<UserEntity> getByWapper() throws JsonProcessingException {
//        List<UserEntity> all = userDao.findAll();
//
//        UserEntity userEntity = all.get(0);
//        List<LanguageEntity> langs = userEntity.getLangs();
//
//        LanguageEntity languageEntity = new LanguageEntity();
//        languageEntity.setName("kotlin");
//
//        langDao.save(languageEntity);
//        langs.add(languageEntity);
//
//        userDao.save(userEntity);
//
//        String string = new ObjectMapper().writeValueAsString(all);
//
//
//        return all;
//    }
//
//    @Override
//    public UserEntity findByName(String userName) {
//        return userDao.findByName(userName);
//    }
//
//    @Override
//    public UserEntity save(UserEntity userEntity) {
//        return userDao.save(userEntity);
//    }
//}
