package cn.latiny.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Latiny
 * @since 2021年1月5日
 */
public class JsonUtils {

    public final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //反序列时，忽略未知字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toBean(String content, Class<T> type) {
        try {
            return objectMapper.readValue(content, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toBean(byte[] data, Class<T> type) {
        return toBean(new String(data, StandardCharsets.UTF_8), type);
    }

    public static <T> T toList(byte[] data, TypeReference<T> type) {
        try {
            return objectMapper.readValue(data, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> toList(String data, Class<T> type) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, type);

        return toList(data, javaType);
    }

    public static <T> List<T> toList(String data, JavaType javaType) {
        try {
            return objectMapper.readValue(data, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode readTree(byte[] data) {
        try {
            return objectMapper.readTree(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode readTree(String data) {
        try {
            return objectMapper.readTree(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
