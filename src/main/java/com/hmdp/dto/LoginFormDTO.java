package com.hmdp.dto;

import lombok.Data;

/**
 * @author Program Monkey
 */
@Data
public class LoginFormDTO {
    private String phone;
    private String code;
    private String password;
}
