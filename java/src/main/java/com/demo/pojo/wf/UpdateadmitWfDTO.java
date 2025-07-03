package com.demo.pojo.wf;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.util.Date;

@Data
public class UpdateadmitWfDTO {

    public String companyCode;
    public String requestNumber;
    public String reqAppKbn;
    public Byte reqAppOrder;
    public String updateUser;
}
