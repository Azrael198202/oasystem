package com.demo.pojo.wf;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateWfDTO {

    public String companyCode;
    public String requestNumber;
    public Byte reqAppOrder;
    public String reqAppComment;
    public String updateUser;
}
