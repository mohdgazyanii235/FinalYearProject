package com.fyp.erpapi.erpapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Utility class for data conversion operations.
 * Contains methods for converting objects to Base64 encoded strings, primarily for
 * encoding data into a format suitable for transport or storage where text data is required.
 */
public class DataConversionUtil {

    /**
     * Converts an object to a Base64 encoded JSON string.
     * This method serializes the object into its JSON representation and then encodes
     * this JSON string into a Base64 encoded string.
     *
     * @param object The object to be converted to Base64 encoded string.
     * @return A Base64 encoded string representing the object, or null if conversion fails.
     */
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
