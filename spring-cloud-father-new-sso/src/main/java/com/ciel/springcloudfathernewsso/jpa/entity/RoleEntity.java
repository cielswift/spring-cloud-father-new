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
//@Table(name="ssh_role")
////@SQLDelete(sql = "update ssh_language set deleted = 1 where id = ?") //逻辑删除
//@Where(clause = "deleted = 0")
//public class RoleEntity extends BaseEntity {
//
//    @Column(name = "name")
//    private String name;
//
//    @ManyToMany(mappedBy = "roles")
//    private List<UserEntity> users = new ArrayList<>();
//
//}
