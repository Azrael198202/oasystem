package com.demo.pojo.wf;

import lombok.Data;

import java.util.Date;

@Data
public class InsertWfDTO {

        // 会社コード
        public String companyCode;
        public String requestNumber;
        public Byte reqAppOrder;
        public String reqAppUserId;
        public String wfStatusKbn;
        public String reqAppComment;
        public String reqAppKbn;
        public String reqAppUpUserId;
    }
