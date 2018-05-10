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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TwitchGG <ray@proxzone.com>
 * @since 1.0.0 on 8/26/16
 */
public class JndiOptionsUtils {
    public static String getPath(String options) {
        if (!options.contains("?"))
            return options;
        int index = options.indexOf("?");
        String path = options.substring(0, index);
        return path;
    }

    public static Map<String, List<String>> getOptions(String options) {
        if (!options.contains("?")) {
            return new HashMap<>();
        }
        int index = options.indexOf("?");
        options = options.substring(index + 1);
        return getOptionsByOptionString(options);
    }

    public static Map<String, List<String>> getOptionsByOptionString(String options) {
        Map<String, List<String>> map = new HashMap<>();
        if (StringUtils.isEmpty(options))
            return map;
        String[] optionsSplit = StringUtils.getSplitValues(options, "&");
        for (String option : optionsSplit) {
            String key = option.split("=")[0];
            String value = option.split("=")[1];
            if (map.containsKey(key)) {
                map.get(key).add(value);
            } else {
                map.put(key, Arrays.asList(value));
            }
        }
        return map;
    }
}
