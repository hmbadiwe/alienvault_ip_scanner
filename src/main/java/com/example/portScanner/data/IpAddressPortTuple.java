package com.example.portScanner.data;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/13/13
 * Time: 10:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class IpAddressPortTuple {
    String ipAddress;
    int port;

    public String getIpAddress() {
        return ipAddress;
    }
    public int getPort() {
        return port;
    }

    public IpAddressPortTuple(String ipAddress, int port){
        this.ipAddress = ipAddress;
        this.port = port;

    }

    public String toString(){
        return this.ipAddress + ":" + this.port;
    }


}
