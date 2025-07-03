package com.demo.pojo.common;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AvatarDTO {

    public String companyCode;

    public String userId;

    MultipartFile image;

    public String img64Str;

    public String img64contentType;

    public String contentType;
}
