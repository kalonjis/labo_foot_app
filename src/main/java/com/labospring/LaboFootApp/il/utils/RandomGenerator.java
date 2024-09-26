package com.labospring.LaboFootApp.il.utils;

import java.security.SecureRandom;


public class RandomGenerator {
    private static SecureRandom secureRandom = new SecureRandom();

    public static int randomize(int bound){
        return secureRandom.nextInt(bound);
    }
}
