package com.example.sell.util;

import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一key
     *
     * @return
     */
    public static synchronized String GetUniquekey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
