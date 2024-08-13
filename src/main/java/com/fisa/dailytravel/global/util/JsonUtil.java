package com.fisa.dailytravel.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper getInstance(){
        return mapper;
    }
}
