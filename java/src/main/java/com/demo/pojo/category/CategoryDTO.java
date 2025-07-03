package com.demo.pojo.category;

import lombok.*;

import java.io.Serializable;

/*
 * Author  : libin
   Time    : 2021/07/15
   Function:
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;

    private String companyCode;

    private String categoryClassNumber;

}
