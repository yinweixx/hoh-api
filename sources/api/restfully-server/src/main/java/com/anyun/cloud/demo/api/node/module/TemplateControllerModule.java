package com.anyun.cloud.demo.api.node.module;

import com.anyun.common.lang.bean.InjectorsBuilder;
import com.google.inject.*;
import com.google.inject.name.Names;

import java.util.Date;

/**
 * Created by wz on 2017/6/19.
 */
public class TemplateControllerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IGTVGController.class).annotatedWith(Names.named("/")).to(HomeController.class);
//        bind(IGTVGController.class).annotatedWith(Names.named("/product/list")).to(ProductListController.class);
    }


    public static void main(String[] args) {
        System.out.println("Thread : " + Thread.currentThread().getId());
        InjectorsBuilder.getBuilder().build(
                new TemplateControllerModule()
                ,new TestServiceModule());
        System.out.println("Init finish");
        String path = "/";
        Injector injector = InjectorsBuilder.getBuilder().getInjector();
        IGTVGController controller = injector.getInstance(Key.get(IGTVGController.class,Names.named(path)));
        System.out.println("Get controller");
        controller = injector.getInstance(Key.get(IGTVGController.class,Names.named(path)));
        controller.test();
    }

    public static interface IGTVGController {
        void test();
    }

    @Singleton
    public static class HomeController implements IGTVGController ,Runnable{
        private ServiceTest serviceTest;
        private Thread thread;

        @Inject
        public HomeController(ServiceTest serviceTest) {
            System.out.println("HomeController init");
            this.serviceTest = serviceTest;
        }

        @Override
        public void test() {
            this.thread = new Thread(this);
            this.thread.start();
        }

        @Override
        public void run() {
            System.out.println("Thread : " + Thread.currentThread().getId());
            Session session = new Session();
            SessionContext.initCurrentSession(session);
            serviceTest.process();
        }
    }


    public static class TestServiceModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(ServiceTest.class).to(ServiceTestImpl.class);
        }
    }

    public static interface ServiceTest {
        void process();
    }

    public static class ServiceTestImpl implements ServiceTest {

        @Override
        public void process() {
            Session session = SessionContext.getCurrentSession();
            System.out.println("Date " + new Date() + " session=" + session);
        }
    }

    public static class SessionContext {
        private static final ThreadLocal<Session> session = new ThreadLocal<>();

        public static Session getCurrentSession() {
            return session.get();
        }

        public static void initCurrentSession(Session s) {
            session.set(s);
        }
    }

    public static class Session {
        @Override
        public String toString() {
            return "session " + Thread.currentThread().getId();
        }
    }
}
