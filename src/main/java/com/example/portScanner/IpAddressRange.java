package com.example.portScanner;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/9/13
 * Time: 7:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class IpAddressRange {
    private IpAddress startIpAddress = null;
    private IpAddress endIpAddress = null;
    private int pagingSize = 10;
    public IpAddressRange( IpAddress startIpAddress, IpAddress endIpAddress){
       if( startIpAddress.compareTo(endIpAddress) > 0 ){
           throw new IllegalArgumentException("Start ip Address must precede End ip Address");
       }
       this.startIpAddress = startIpAddress;
       this.endIpAddress = endIpAddress;
    }
    public IpAddressRange( IpAddress startIpAddress){
        this.startIpAddress = startIpAddress;
        this.endIpAddress = startIpAddress;
    }
}
