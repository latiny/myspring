/*
 * Copyright
 * All rights reserved.
 */

package cn.latiny.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * bean 拷贝
 *
 * @author Latiny
 * @since 1.0.0 2018/1/5
 */
public class BeanUtils {

    private BeanUtils() {
        throw new IllegalStateException("Utils class");
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = beanWrapper.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 忽略 null 属性拷贝
     *
     * @param source source
     * @param target target
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * 默认 不会忽略null 属性拷贝
     *
     * @param source source
     * @param target target
     */
    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }


    /**
     * 将List<Object>转换成List<Map<String, Object>>
     * 其中的map，key为Object的属性名，value为Ojbect的属性值
     *
     * @param list          list
     * @param dateFormatStr 若属性有java.util.Date类型，可统一格式化为dateFormatStr，为null则不进行格式化
     * @return List<Map < String, Object>>
     * @author wangjingru
     */

    public static List<Map<String, Object>> objectCommonConvertToMapList(List<?>  list, String dateFormatStr) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Map<String, Object>> exportData = new ArrayList<>();
        for (Object domain : list) {
            exportData.add(objectCommonConvertToMap(domain, dateFormatStr, Boolean.FALSE));
        }
        return exportData;
    }


    /**
     * 将实体转换成Map<String   ,   Object> key为属性名，value是属性值
     *
     * @param object             object
     * @param dateFormatStr      若属性有java.util.Date类型，可统一格式化为dateFormatStr，为null则不进行格式化
     * @param longConvertToMoney 是否将所有Long类型的数字都当做金额转换：分转换成元，保留小数点后两位
     * @return Map<String, Object>
     * @author wangjingru
     */
    public static Map<String, Object> objectCommonConvertToMap(Object object, String dateFormatStr, Boolean longConvertToMoney) {
        Map<String, Object> result = new HashMap<>();
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object eleContent;
                try {
                    eleContent = field.get(object);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
                if (eleContent == null) {
                    //为null
                    result.put(field.getName(), null);
                    continue;
                }
                if (eleContent instanceof Date && !StringUtils.isEmpty(dateFormatStr)) {
                    //如果是时间类型并且需要格式化
                    result.put(field.getName(),(new SimpleDateFormat(dateFormatStr)).format(eleContent));
                    continue;
                }
                if (eleContent instanceof Long && longConvertToMoney) {
                    //如果是Long类型并且希望进行分->元的转换
                    result.put(field.getName(), NumberUtils.toYuan((Long) eleContent));
                    continue;
                }
                result.put(field.getName(), eleContent);
            }
        }
        return result;
    }

}
