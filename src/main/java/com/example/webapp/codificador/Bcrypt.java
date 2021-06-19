package com.example.webapp.codificador;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Bcrypt {

    public static void main(String ars[]){

        String clave = "1234";
        System.out.println(encriptar(clave));
    }
    public static String encriptar(String clave){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(clave);
    }

}




