/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.anyun.common.lang.pool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author TwitchGG <ray@proxzone.com>
 * @since 1.0.0 on 8/29/16
 */
public abstract class PoolBuilder<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoolBuilder.class);
    public static final String CONF_MAX_TOTAL = "anyun.cloud.pool.crate.max_total";
    public static final String CONF_MIN_IDEL = "anyun.cloud.pool.crate.min_idel";
    public static final String CONF_MAX_WAIT_MILLIS = "anyun.cloud.pool.crate.max_wait_millis";
    public static final String CONF_MAX_IDEL = "anyun.cloud.pool.crate.max_idel";
    public static final String CONF_TEST_ON_BORROW = "anyun.cloud.pool.crate.test_on_borrow";
    public static final String CONF_TEST_ON_CREATE = "anyun.cloud.pool.crate.test_on_create";
    public static final String CONF_TEST_ON_RETURN = "anyun.cloud.pool.crate.test_on_return";

    private Properties properties;
    private GenericObjectPoolConfig config;

    public PoolBuilder() {

    }

    public PoolBuilder withProperties(Properties properties) {
        this.properties = properties;
        return this;
    }

    public GenericObjectPool<T> build() throws Exception {
        config = new GenericObjectPoolConfig();
        config.setMinIdle(getOption(CONF_MIN_IDEL, 300));
        config.setMaxWaitMillis(getOption(CONF_MAX_WAIT_MILLIS, 5000));
        config.setMaxTotal(getOption(CONF_MAX_TOTAL, 5));
        config.setMaxIdle(getOption(CONF_MAX_IDEL, 5000));
        config.setTestOnBorrow(getOption(CONF_TEST_ON_BORROW, false));
        config.setTestOnCreate(getOption(CONF_TEST_ON_CREATE, false));
        config.setTestOnCreate(getOption(CONF_TEST_ON_RETURN, false));
        return _build(properties,config);
    }

    protected abstract GenericObjectPool<T> _build(Properties properties,GenericObjectPoolConfig config) throws Exception;

    private <T> T getOption(String key, T defaultValue) throws Exception {
        if (properties.containsKey(key)) {
            String value = properties.getProperty(key);
            if (defaultValue instanceof String)
                return (T) value;
            if (defaultValue instanceof Integer)
                return (T) Integer.valueOf(value);
            if (defaultValue instanceof Long)
                return (T) Long.valueOf(value);
            if (defaultValue instanceof Boolean)
                return (T) Boolean.valueOf(value.toLowerCase());
            return (T) value;
        } else {
            return defaultValue;
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public GenericObjectPoolConfig getConfig() {
        return config;
    }
}
