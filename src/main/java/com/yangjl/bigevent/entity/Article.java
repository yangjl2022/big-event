package com.yangjl.bigevent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yangjl.bigevent.annotation.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer id;

    @NotEmpty
    @Pattern(regexp = "\\S{1,10}")
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    @URL
    private String coverImg;

//    @NotEmpty
//    @Pattern(regexp = "^(草稿|已发布)$")
    @State
    private String state;

    @NotNull
    private Integer categoryId;

    private Integer userId;
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
