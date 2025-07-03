package com.demo.pojo.resume;

import lombok.*;

import java.io.Serializable;

/*
 * Author  : libin
   Time    : 2022/07/11
   Function:
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResumeDetailDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;

    private String develop_period;

    private String develop_summary;

    private String develop_language;

    private String develop_db;

    private String develop_os;

    private String responsible;

    private String requirements_flg;

    private String basic_flg;

    private String specific_flg;

    private String coding_flg;

    private String unit_test_flg;

    private String interface_test_flg;

    private String system_test_flg;

}
