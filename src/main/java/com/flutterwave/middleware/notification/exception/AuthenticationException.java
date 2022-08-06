package com.flutterwave.middleware.notification.exception;

import lombok.Data;

/**
 * @author: adewaleijalana
 * @email: adewale.ijalana@flutterwavego.com
 * @date: 22/03/2022
 * @time: 8:01 AM
 **/

@Data
public class AuthenticationException extends RuntimeException{

    public AuthenticationException(String message) {
        super(message);
    }
}
