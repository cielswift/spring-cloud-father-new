//package com.ciel.springcloudfathernewsso.jpa;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Data;
//import org.hibernate.annotations.Where;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Data
//@Entity
//@Table(name="ssh_user")
////@SQLDelete(sql = "update ssh_user set deleted = 1 where id = ?") //逻辑删除
//@Where(clause = "deleted = 0")
//public class UserEntity extends BaseEntity {
//
//    @Column(name = "name")
//    private String name;
//    @Column(name = "password")
//    private String password;
//
//    @JsonIgnoreProperties(value = {"user"}) //不序列化AppEntity的user属性,防止溢出
//    @OneToMany(mappedBy = "user",fetch =FetchType.LAZY,cascade = {CascadeType.REMOVE})
//    //mappedBy对方维护关联关系, cascade级联删除,删除多端的所有数据
//    @OrderBy("createDate ASC")
//    private List<AppEntity> apps = new ArrayList<>();
//
//    @Column(name = "birthday")
//    private Date birthday;
//
//    @Column(name = "sex")
//    private Byte sex;
//
//    @Column(name = "head_Sculpture_path")
//    private String headSculpturePath;
//
//    @JsonIgnoreProperties(value = {"users"})  //不序列化LanguageEntity的users属性,防止溢出
//    @OrderBy("createDate desc")
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name="ssh_user_lanhuager",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name="language_id",referencedColumnName = "id"))
//    //joinColumns 此表在中间表中对应的外键 ; referencedColumnName这个外键指向本表(或对应表)的主键,不写也可以
//    // inverseJoinColumns 另一个表对应的中间表的外键;
//    List<LanguageEntity> langs = new ArrayList<>();
//
//
//    @JsonIgnoreProperties(value = {"users"})  //不序列化LanguageEntity的users属性,防止溢出
//    @OrderBy("createDate desc")
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name="ssh_user_role",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
//    List<RoleEntity> roles = new ArrayList<>();
//
//
////    ackson中的@JsonBackReference和@JsonManagedReference，
////    以及@JsonIgnore均是为了解决对象中存在双向引用导致的无限递归（infinite recursion）问题。这些标注均可用在属性或对应的get、set方法中。
////
////    @JsonBackReference和@JsonManagedReference：这两个标注通常配对使用，通常用在父子关系中。
////    @JsonBackReference标注的属性在序列化（serialization，即将对象转换为json数据）时，会被忽略（即结果中的json数据不包含该属性的内容）。
////    @JsonManagedReference标注的属性则会被序列化。在序列化时，@JsonBackReference的作用相当于@JsonIgnore，此时可以没有@JsonManagedReference。
////    但在反序列化（deserialization，即json数据转换为对象）时，如果没有@JsonManagedReference，则不会自动注入@JsonBackReference标注的属性（被忽略的父或子）；
////    如果有@JsonManagedReference，则会自动注入自动注入@JsonBackReference标注的属性。
////
////    @JsonIgnore：直接忽略某个属性，以断开无限递归，序列化或反序列化均忽略。当然如果标注在get、set方法中，则可以分开控制，序列化对应的是get方法，
////    反序列化对应的是set方法。在父子关系中，当反序列化时，@JsonIgnore不会自动注入被忽略的属性值（父或子），
////    这是它跟@JsonBackReference和@JsonManagedReference最大的区别。
//
//
////    总结 ;@JsonBackReference和 不参与序列化, 但是参与反序列化;
////
////    @JsonManagedReference:参与序列化, 如果没有@JsonManagedReference不会参与反序列化;
//
//}
