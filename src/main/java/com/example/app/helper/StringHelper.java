package com.example.app.helper;

import org.springframework.stereotype.Component;

@Component
public class StringHelper {
    public boolean checkIfStringLengthLessThan(int expectedLength, int strLen) {
        return strLen <= expectedLength;
    }
}
