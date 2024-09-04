package com.yangjl.bigevent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalTime;

@Data
public class Category {
    private Integer id;
    @NotEmpty
    private String categoryName;
    @NotEmpty
    private String categoryAlias;

    @JsonIgnore
    private Integer createUserId;
    private LocalTime createTime;
    private LocalTime updateTime;
}
