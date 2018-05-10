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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

/**
 * @author TwitchGG <ray@proxzone.com>
 * @since 1.0.0 on 8/25/16
 */
public class SerializationUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerializationUtils.class);

    public static String getSerialNumber(String componentName) {
        StringBuilder sb = new StringBuilder(componentName);
        Enumeration<NetworkInterface> nets = null;
        try {
            nets = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            return "serial_number_error";
        }
        for (NetworkInterface netint : Collections.list(nets)) {
            if (netint.getDisplayName().equals("lo") || netint.getName().equals("lo"))
                continue;
            LOGGER.debug("Display name: {}", netint.getDisplayName());
            sb.append(netint.getDisplayName());
            Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                if (inetAddress instanceof Inet6Address)
                    continue;
                sb.append(":").append(inetAddress.getHostAddress());
                LOGGER.debug("InetAddress: [{}] [{}]", inetAddress, inetAddress.getHostAddress());
            }
        }
        return EncryptUtils.getMD5ofStr(sb.toString()).toLowerCase();
    }
}
