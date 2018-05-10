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

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author TwitchGG <ray@proxzone.com>
 * @since 1.0.0 on 16-8-24
 */
public class StringUtils {

    private StringUtils() {

    }

    /**
     * @param str
     * @return
     */
    public final static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        str = str.trim();
        return str.length() == 0;
    }

    /**
     * @param str
     * @return
     */
    public final static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * @param str
     * @return
     */
    public final static long getIgnoreCaseLength(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        return str.trim().length();
    }

    /**
     * @param str
     * @param segment
     * @return
     */
    public final static String[] getSplitValues(String str, String segment) {
        if (str == null || str.trim().length() == 0) {
            return new String[]{};
        }
        if (segment == null || segment.length() == 0) {
            return new String[]{};
        }
        if (!str.contains(segment))
            return new String[]{str};
        StringTokenizer stringTokenizer = new StringTokenizer(str, segment);
        List<String> segs = new LinkedList<>();
        while (stringTokenizer.hasMoreTokens()) {
            String _token = stringTokenizer.nextToken();
            segs.add(_token);
        }
        String[] returns = new String[segs.size()];
        for (int i = 0; i < segs.size(); i++) {
            returns[i] = segs.get(i);
        }
        return returns;
    }

    /**
     * @param date
     * @param expression
     * @return
     */
    public static final String formatDate(Date date, String expression) {
        if (isEmpty(expression)) {
            expression = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(expression);
        return sdf.format(date);
    }

    /**
     * @param date
     * @param expression
     * @return
     */
    public static final Date parseDate(String date, String expression) {
        try {
            if (isEmpty(expression)) {
                expression = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(expression);
            return sdf.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param str
     * @return
     */
    public final static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * @param str
     * @return
     */
    public final static boolean onlyEngAndNumeric(String str) {
        return str.matches("[a-zA-Z0-9|\\.]*");
    }

    /**
     * @param str
     * @param regex
     * @return
     */
    public final static boolean regex(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(str);
        return m.find();
    }

    /**
     * @param result
     * @return
     */
    public final static List<String> readLines(String result) {
        List<String> values = new LinkedList<>();
        if (StringUtils.isEmpty(result)) {
            return values;
        }
        BufferedReader reader = new BufferedReader(new StringReader(result));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                values.add(line.trim());
            }
            return values;
        } catch (Exception e) {
            return new LinkedList<>();
        }
    }
}
