/*
 * Copyright (c)
 * All rights reserved.
 */

package cn.latiny.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * 数字转换工具类
 *
 * @author Latiny
 * @date 2018年1月24日
 * @since 1.0.0
 */
public class NumberUtils {

    public static final int MILLION_TO_NUMBERL_UNIT = 1000000;

    private NumberUtils() {
        throw new IllegalStateException("Utils class.");
    }

    /**
     * 百万比数转换为数字
     * 前端系统对20%的值做了20*10000操作。
     * 故要将百分数转换为数字，算法为20/10000/100。
     *
     * @param val val
     * @return double
     */
    public static double millionToNumber(Integer val) {
        BigDecimal source = new BigDecimal(val);
        BigDecimal bigDecimal = new BigDecimal(MILLION_TO_NUMBERL_UNIT);
        return source.divide(bigDecimal).doubleValue();
    }

    /**
     * 分  -->  元
     * 500 --> 5.00
     *
     * @param amount amount
     * @return String
     */
    public static String toYuan(Long amount) {
        BigDecimal source = new BigDecimal(amount);
        BigDecimal way = new BigDecimal(100);
        return source.divide(way).setScale(2).toString();
    }

    /**
     * 字符串 ----》数字，单位不变
     * 5.00 --> 5
     *
     * @param amount amount
     * @return static
     */
    public static long toAmount(String amount) {
        NumberFormat format = NumberFormat.getInstance();
        long longValue = 0;
        try {
            longValue = format.parse(amount).longValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longValue;
    }

    public static void main(String[] args) {
    }

}
