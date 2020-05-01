//package com.ciel.springcloudfathernewsso.jpa;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Data;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Date;
//
//@Data
//@MappedSuperclass
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer"}) //json 序列化报错
//@EntityListeners(AuditingEntityListener.class) //自动填充
//
//public class BaseEntity implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    @Column(name="ID",nullable=false)
//    private Integer id;
//
//    @JsonIgnore
//    @Column(name="deleted",length=1,nullable=false)
//    private Integer deleted = 0; //默认值
//
//    @CreatedDate //自动赋值
//    @Column(name="create_Date")
//    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd:HH:mm:ss",timezone = "GMT+8")
//    private Date createDate;
//
//    @LastModifiedDate //自动赋值
//    @Column(name="update_Date")
//    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd:HH:mm:ss",timezone = "GMT+8")
//    private Date updateDate;
//
//}
