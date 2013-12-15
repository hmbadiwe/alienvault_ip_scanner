package com.example.portScanner.rest;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import javax.ws.rs.ext.ContextResolver;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/11/13
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class IpAddressRangeObjectMapperProvider implements ContextResolver<ObjectMapper>{
    @Override
    public ObjectMapper getContext(Class<?> type) {
        ObjectMapper result = new ObjectMapper();
        result.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        result.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return result;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
