package com.example.project;

import java.util.Random;

public class RandomCode {

    public static String getString() {

        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";


        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;


        StringBuilder sb = new StringBuilder();


        Random random = new Random();

        int length = 15;

        for(int i = 0; i < length; i++) {


            int index = random.nextInt(alphaNumeric.length());


            char randomChar = alphaNumeric.charAt(index);

            sb.append(randomChar);
        }

        String randomString = sb.toString();

        return randomString;
    }
}