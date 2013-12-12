package com.example.portScanner.rest;

import com.example.portScanner.rest.data.Payload;
import com.example.portScanner.rest.data.PortQuery;
import com.example.portScanner.rest.data.PortQueryResult;
import com.example.portScanner.rest.data.PortRange;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/11/13
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class IpAddressApplication extends ResourceConfig {
    public IpAddressApplication(){
        super(
            IpAddressRestResource.class,
            IpAddressRangeObjectMapperProvider.class,
            Payload.class,
            PortQuery.class,
            PortQueryResult.class,
            PortRange.class,
            JacksonFeature.class
        );
    }
}
