package com.ciel.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class EntityFather implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type =IdType.ASSIGN_ID )
    private Long id;

    @TableField(value = "CREATE_DATE",fill= FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd:HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createDate;

    @TableField(value = "UPDATE_DATE",fill= FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd:HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateDate;

    @TableLogic
    @TableField(value = "DELETED",fill= FieldFill.INSERT)
    private Integer deleted;

}
