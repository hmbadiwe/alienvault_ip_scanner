package com.example.portScanner.rest.data;

import com.example.portScanner.data.IpAddressType;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/11/13
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class PortQueryResult implements Serializable {
    private String ipAddress;
    private Integer port;
    private String protocol;
    private String service;
    private boolean isConnected;
    private String ipAddressType;

    public PortQueryResult(){

    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getIpAddressType() {
        return ipAddressType;
    }

    public void setIpAddressType(String ipAddressType) {
        this.ipAddressType = ipAddressType;
    }

    public PortQueryResult(PortQuery p, IpAddressType ipAddressType, boolean isConnected ){
        this.ipAddressType = ipAddressType.toString();
        if( ipAddressType == IpAddressType.NORMAL ){
            this.ipAddress = p.getIpAddress();
            this.port = p.getPort();
            this.protocol = p.getProtocol();
            this.service = p.getService();
            this.isConnected = isConnected;

        }
    }
}
