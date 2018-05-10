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

import com.anyun.common.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import java.util.Hashtable;

/**
 *
 * @author TwitchGG <ray@proxzone.com>
 * @since 1.0.0 on 8/29/16
 */
public abstract class AbstractContextFactory implements InitialContextFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractContextFactory.class);

    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        if (!environment.containsKey(Context.PROVIDER_URL))
            throw new NamingException("Property [" + Context.PROVIDER_URL + "] is not set");
        String providerUrl = environment.get(Context.PROVIDER_URL).toString();
        LOGGER.debug("Context.PROVIDER_URL [{}]", providerUrl);
        if (!providerUrl.contains("://"))
            throw new NamingException(Context.PROVIDER_URL + " format [type://options]");
        String[] providerInfo = StringUtils.getSplitValues(providerUrl, "://");
        if (providerInfo.length != 2)
            throw new NamingException(Context.PROVIDER_URL + " format [type://options]");
        String providerType = providerInfo[0];
        String providerOptions = providerInfo[1];
        LOGGER.debug("Provider type [{}] options [{}]", providerType, providerOptions);
        return getInitialContext(providerType, providerOptions);
    }

    public abstract Context getInitialContext(String providerType, String options) throws NamingException;
}
