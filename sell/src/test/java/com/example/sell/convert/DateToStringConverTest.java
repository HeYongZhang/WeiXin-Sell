package com.example.sell.convert;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateToStringConverTest {

    @Test
    public void yesterday() {
        String yesterday = DateToStringConver.Yesterday(new Date());
        System.out.println(yesterday);
    }
}