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

import javax.naming.*;
import java.util.Hashtable;
import java.util.List;

/**
 * @author TwitchGG <ray@proxzone.com>
 * @since 1.0.0 on 8/29/16
 */
public class AbstractJndiContext implements Context {
    protected Hashtable<String, List<String>> optionsMap = new Hashtable<>();

    @Override
    public Object lookup(Name name) throws NamingException {
        return null;
    }

    @Override
    public Object lookup(String name) throws NamingException {
        return null;
    }

    @Override
    public void bind(Name name, Object obj) throws NamingException {

    }

    @Override
    public void bind(String name, Object obj) throws NamingException {

    }

    @Override
    public void rebind(Name name, Object obj) throws NamingException {

    }

    @Override
    public void rebind(String name, Object obj) throws NamingException {

    }

    @Override
    public void unbind(Name name) throws NamingException {

    }

    @Override
    public void unbind(String name) throws NamingException {

    }

    @Override
    public void rename(Name oldName, Name newName) throws NamingException {

    }

    @Override
    public void rename(String oldName, String newName) throws NamingException {

    }

    @Override
    public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
        return null;
    }

    @Override
    public void destroySubcontext(Name name) throws NamingException {

    }

    @Override
    public void destroySubcontext(String name) throws NamingException {

    }

    @Override
    public Context createSubcontext(Name name) throws NamingException {
        return null;
    }

    @Override
    public Context createSubcontext(String name) throws NamingException {
        return null;
    }

    @Override
    public Object lookupLink(Name name) throws NamingException {
        return null;
    }

    @Override
    public Object lookupLink(String name) throws NamingException {
        return null;
    }

    @Override
    public NameParser getNameParser(Name name) throws NamingException {
        return null;
    }

    @Override
    public NameParser getNameParser(String name) throws NamingException {
        return null;
    }

    @Override
    public Name composeName(Name name, Name prefix) throws NamingException {
        return null;
    }

    @Override
    public String composeName(String name, String prefix) throws NamingException {
        return null;
    }

    @Override
    public Object addToEnvironment(String propName, Object propVal) throws NamingException {
        if (!(propVal instanceof List))
            throw new NamingException("Unsupported parameter value type ["
                    + propVal.getClass().getCanonicalName() + "]");
        List<String> value = (List<String>) propVal;
        if (optionsMap.get(propName) == null) {
            optionsMap.put(propName, value);
        } else {
            optionsMap.get(propName).addAll(value);
        }
        return this;
    }

    @Override
    public Object removeFromEnvironment(String propName) throws NamingException {
        if (!optionsMap.containsKey(propName))
            throw new NamingException("Not find property name [" + propName + "]");
        optionsMap.remove(propName);
        return this;
    }

    @Override
    public Hashtable<?, ?> getEnvironment() throws NamingException {
        return optionsMap;
    }

    @Override
    public void close() throws NamingException {
        optionsMap = new Hashtable<>();
    }

    @Override
    public String getNameInNamespace() throws NamingException {
        return null;
    }
}
