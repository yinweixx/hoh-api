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

package com.anyun.common.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author TwitchGG <ray@proxzone.com>
 * @since 1.0.0 on 9/5/16
 */
public class Environment {
    private final static Map<Object, Object> env = new HashMap();

    public static void init(Properties properties) throws Exception {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            export(entry.getKey(), entry.getValue());
        }
    }

    public static void export(Object key, Object value) {
        env.put(key, value);
    }

    public static void unset(String key) {
        env.remove(key);
    }

    public static <T> void unset(Class<T> type) {
        env.remove(type);
    }

    public static void set(Object key, Object value) {
        export(key, value);
    }

    public static String env(String key) {
        return env(String.class, key);
    }

    public static <T> T env(Class<T> type) {
        Object obj = env.get(type);
        if (obj == null)
            return null;
        return (T) obj;
    }

    public static <T> T env(Class<T> type, String key) {
        Object value = env.get(key);
        if (value == null)
            return null;
        if (type.equals(String.class))
            return (T) String.valueOf(value);
        if (type.equals(Long.class))
            return (T) Long.valueOf(value.toString());
        if (type.equals(Integer.class))
            return (T) Integer.valueOf(value.toString());
        return (T) value;
    }
}

