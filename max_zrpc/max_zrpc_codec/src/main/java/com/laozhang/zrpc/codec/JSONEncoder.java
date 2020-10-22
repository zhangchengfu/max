package com.laozhang.zrpc.codec;

import com.alibaba.fastjson.JSON;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于json的序列化实现
 */
public class JSONEncoder implements Encoder {

    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }

    public static void main(String[] args) {

        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher matcher=pattern.matcher("1");
        if (!matcher.matches()) {
            System.out.println("x");
        }

        pattern = Pattern.compile("^\\d{4}\\-\\d{2}\\-\\d{2}$");
        matcher = pattern.matcher("20#20-10-10");
        if (!matcher.matches()) {
            System.out.println("1");
        }

        pattern = Pattern.compile("^\\d{4}\\-\\d{2}$");
        matcher = pattern.matcher("202@0-10");
        if (!matcher.matches()) {
            System.out.println("2");
        }
    }
}
