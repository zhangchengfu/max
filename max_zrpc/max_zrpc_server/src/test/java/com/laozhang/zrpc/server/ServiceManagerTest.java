package com.laozhang.zrpc.server;

class ServiceManagerTest {

    /*ServiceManager sm;

    @Before
    public void init() {

        sm = new ServiceManager();
        TestInterface bean = new TestClass();
        sm.register(TestInterface.class, bean);
    }

    @Test
    public void register() {

        TestInterface bean = new TestClass();
        sm.register(TestInterface.class, bean);
    }

    @Test
    public void lookup() {

        Method method = ReflectionUtils.getPublicMethods(TestInterface.class)[0];
        ServiceDescriptor sdp = ServiceDescriptor.from(TestInterface.class, method);

        Request request = new Request();
        request.setService(sdp);

        if (null == sm) {
            init();
        }
        ServiceInstance sis = sm.lookup(request);
        assertNotNull(sis);
    }*/
}