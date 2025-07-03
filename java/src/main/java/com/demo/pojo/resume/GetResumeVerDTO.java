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
public class GetResumeVerDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;

    // resumeVerName
    private String resumeVerKbn;

}
