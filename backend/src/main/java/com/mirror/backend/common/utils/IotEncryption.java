package com.mirror.backend.common.utils;

import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;


public class IotEncryption {

    @Value("${iot.N}")
    static int N;

    @Value("${iot.PNA}")
    static int PNA;

    @Value("${iot.PNB}")
    static int PNB;

    public static String encryption(String Sentence, int n, int e) {
        return new String(Base64.getEncoder().encode(Sentence.getBytes()));
    }

    public static String decryption(String Sentence, int n, int d) {
        return new String(Base64.getDecoder().decode(Sentence.getBytes()));
    }

    public static String encrytionText(String input){
        int N = 1517;
        int PNB = 1061;

        String output = encryption(input, N, PNB);
        System.out.println(output);

        return output;
    }

    public static String decryptionText(String input){
        int N = 1517;
        int PNA = 1421;

        String output = decryption(input, N, PNA);
        System.out.println(output);

        return output;
    }
}