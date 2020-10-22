package com.laozhang.zrpc.common.utils;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

class ReflectionUtilsTest {

    @Test
    void newInstance() {

        TestClass t = ReflectionUtils.newInstance(TestClass.class);
        assertNotNull(t);
    }

    @Test
    void getPublicMethods() {

        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        assertEquals(1, methods.length);

        String mname = methods[0].getName();
        assertEquals("b", mname);
    }

    @Test
    void invoke() {

        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        Method method = methods[0];

        TestClass t = new TestClass();
        Object o = ReflectionUtils.invoke(t, method);
        assertEquals("b", o);
    }
}