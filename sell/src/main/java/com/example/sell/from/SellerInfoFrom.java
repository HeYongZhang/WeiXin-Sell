package com.example.sell.from;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class SellerInfoFrom {

    private String uuid;

    private String username;

    private String password;

    private String openid;

}
