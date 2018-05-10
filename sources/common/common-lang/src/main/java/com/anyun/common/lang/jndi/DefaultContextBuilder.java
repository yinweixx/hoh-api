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

package com.anyun.common.lang.jndi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TwitchGG <ray@proxzone.com>
 * @since 1.0.0 on 8/29/16
 */
public class DefaultContextBuilder extends AbstractContextBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultContextBuilder.class);
    private Map<String, Class<? extends Context>> contextMap = new HashMap<>();

    public void addContext(String name,Class<? extends Context> contextClass) {
        contextMap.put(name,contextClass);
    }

    @Override
    public Context build() throws NamingException {
        LOGGER.debug("Context options [{}]", options);
        String path = JndiOptionsUtils.getPath(options);
        LOGGER.debug("Path [{}]", path);
        Map<String, List<String>> optionsMap = JndiOptionsUtils.getOptions(options);
        LOGGER.debug("Options map[{}]", optionsMap);
        Class<? extends Context> contextClass = contextMap.get(path);
        if (contextClass == null)
            throw new NamingException("Unsupported context path [" + path + "]");
        try {
            Object object = contextClass.newInstance();
            if (!(object instanceof Context))
                throw new Exception("Bad context type [" + object.getClass().getName() + "]");
            LOGGER.debug("Create context [{}]", object);
            Context context = (Context) object;
            for (Map.Entry<String, List<String>> entry : optionsMap.entrySet()) {
                context.addToEnvironment(entry.getKey(), entry.getValue());
            }
            return context;
        } catch (Exception ex) {
            throw new NamingException("Conntext [" + path + "] create error [" + ex.getMessage() + "]");
        }
    }

    public Map<String, Class<? extends Context>> getContextMap() {
        return contextMap;
    }
}
