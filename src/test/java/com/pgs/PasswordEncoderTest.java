package com.pgs;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Created by mmalek on 2/14/2017.
 */
public class PasswordEncoderTest {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
        System.out.println(new BCryptPasswordEncoder().matches("admin", "$2a$10$TXwK4iA7TlN3dIjejiS/LuwL9VQR1WolUt2/7pUbixvSUZo9O8Diu"));
    }
}
