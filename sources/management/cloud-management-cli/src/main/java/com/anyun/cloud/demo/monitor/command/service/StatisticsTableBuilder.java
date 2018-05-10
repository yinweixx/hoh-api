package com.anyun.cloud.demo.monitor.command.service;

import com.anyun.common.lang.bean.InjectorsBuilder;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 07/06/2017
 */
public interface StatisticsTableBuilder {

    /**
     *
     * @param args
     * @return
     * @throws Exception
     */
    String build(String... args) throws Exception;

    /**
     *
     * @param clazz
     * @return
     */
    static StatisticsTableBuilder getTableBuilderByClass(Class<? extends StatisticsTableBuilder> clazz) {
        Injector injector = InjectorsBuilder.getBuilder().getInjector();
        Key<StatisticsTableBuilder> key = Key.get(StatisticsTableBuilder.class, Names.named(clazz.getSimpleName()));
        return injector.getInstance(key);
    }
}
