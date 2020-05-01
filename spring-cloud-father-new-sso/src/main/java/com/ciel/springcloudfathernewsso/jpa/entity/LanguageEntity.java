//package com.ciel.springcloudfathernewsso.jpa;
//
//import lombok.Data;
//import org.hibernate.annotations.Where;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.ManyToMany;
//import javax.persistence.Table;
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@Entity
//@Table(name="ssh_language")
////@SQLDelete(sql = "update ssh_language set deleted = 1 where id = ?") //逻辑删除
//@Where(clause = "deleted = 0")
//public class LanguageEntity extends BaseEntity {
//
//    @Column(name = "name")
//    private String name;
//
//    @ManyToMany(mappedBy = "langs") //mappedBy 是Person的langs属性,(不是表字段);对方维护
//    private List<UserEntity> users = new ArrayList<>();
//}
