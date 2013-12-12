package com.example.portScanner.rest.data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/11/13
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class PortQuery implements Serializable {
    private Integer port;
    private String ipAddress;
    private String protocol;
    private String service;

    public PortQuery(){}

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
