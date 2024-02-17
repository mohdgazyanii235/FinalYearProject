package com.fyp.erpapi.erpapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DataConversionUtil {

    public static String convertToBase64(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(object);

            return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("conversion error");
            return null;
        }
    }
}
