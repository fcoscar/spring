package com.example.curso.codificador;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Bcrypt {

     public static void main(String[] args) {
         var password = "4321";
            System.out.println(password);

            password = encriptar(password);
            System.out.println(password);
        }
        public static String encriptar(String password) {
            BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();

            return codificador.encode(password);

        }
    }

