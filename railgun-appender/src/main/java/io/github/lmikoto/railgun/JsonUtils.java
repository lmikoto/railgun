package io.github.lmikoto.railgun;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

/**
 * @author liuyang
 * 2021/2/4 2:15 下午
 */
public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static String toPrettyJson(Object o){
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }

    @SneakyThrows
    public static <T> T fromJson(String json, TypeReference<T> typeReference){
        return MAPPER.readValue(json, typeReference);
    }

    @SneakyThrows
    public static String toJson(Object param) {
        return MAPPER.writeValueAsString(param);
    }

    public static abstract class TypeReference<T> extends com.fasterxml.jackson.core.type.TypeReference<T> {}
}
