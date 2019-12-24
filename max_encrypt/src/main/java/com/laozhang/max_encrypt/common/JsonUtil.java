package com.laozhang.max_encrypt.common;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 将json字符串转为目标对象
     *
     * @param json
     *            待转换字符串
     * @param obj
     *            目标对象class
     * @return 目标对象实例
     */
    public static <T> T toBean(final String json, final Class<T> obj) {

        try {
            return JsonFormatter.toObject(json, obj);
        } catch (final Exception e) {
            LOGGER.error("JsonUtil.toBean error: ", e);
        }
        return null;
    }

    /**
     * 将目标对象转为json字符串
     *
     * @param obj
     *            目标对象
     * @return String json串
     */
    public static String toString(final Object obj) {

        try {
            return JsonFormatter.toString(obj);
        } catch (final Exception e) {
            LOGGER.error("JsonUtil.toString error: ", e);
        }
        return null;
    }

    /**
     * 将json字符串转为list
     *
     * @param jsonStr
     *            json字符串
     * @param clazz
     *            目标对象class
     * @return
     */
    public static <T> List<T> toList(final String jsonStr, final Class<T> clazz) {

        try {
            return JsonFormatter.toList(jsonStr, clazz);
        } catch (final Exception e) {
            LOGGER.error("JsonUtil.toList error: ", e);
        }
        return null;
    }

    /**
     * 将字符串转换成JsonNode
     *
     * @param jsonStr
     *            json字符串
     * @return
     */
    public static JsonNode toJsonNode(final String jsonStr) {

        try {
            return JsonFormatter.toJsonNode(jsonStr);
        } catch (final Exception e) {
            LOGGER.error("JsonUtil.toJsonNode error: ", e);
        }
        return null;
    }

    static class JsonFormatter {

        /**
         * 通用mapper
         */
        private static ObjectMapper mapper = new ObjectMapper();

        static {
            // json->bean ,忽略bean中不存在的属性
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // bean->json ,忽略为null的属性
            mapper.setSerializationInclusion(Include.NON_NULL);
            // map->json ,忽略为null的key
            mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        }

        /**
         * @param obj
         *            待转换的对象
         * @return String 字符串
         * @throws JsonProcessingException
         */
        public static String toString(final Object obj) throws JsonProcessingException {

            if (obj instanceof String) {
                return (String) obj;
            } else if (obj == null) {
                return null;
            } else {
                return mapper.writeValueAsString(obj);
            }
        }

        /**
         * @param obj
         *            待转换的对象
         * @param clazz
         *            目标对象class
         * @return Object 目标对象实例
         * @throws JsonParseException
         * @throws JsonMappingException
         * @throws IOException
         */
        public static <T> T toObject(final Object obj, final Class<T> clazz)
                throws JsonParseException, JsonMappingException, IOException {

            if (obj == null) {
                return null;
            } else {
                return mapper.readValue(toString(obj), clazz);
            }
        }

        /**
         * @param jsonStr
         *            带转换的字符串
         * @param clazz
         *            目标对象
         * @return List list集合
         * @throws JsonParseException
         * @throws JsonMappingException
         * @throws IOException
         */
        @SuppressWarnings("unchecked")
        public static <T> List<T> toList(final String jsonStr, final Class<T> clazz)
                throws JsonParseException, JsonMappingException, IOException {

            final List<T> lists = new LinkedList<T>();
            final List<Object> list = toObject(jsonStr, List.class);
            if (null != list) {
                for (final Object object : list) {
                    final T t = toObject(object, clazz);
                    if (null != t) {
                        lists.add(t);
                    }
                }
            }
            return lists;
        }

        public static <T> List<T> toList(final String jsonStr, final TypeReference<List<T>> typeReference)
                throws JsonParseException, JsonMappingException, IOException {

            final List<T> lists = mapper.readValue(jsonStr, typeReference);
            return lists;
        }

        public static JsonNode toJsonNode(final String jsonStr) throws JsonProcessingException, IOException {

            return mapper.readTree(jsonStr);
        }
    }

}
