package com.example.portScanner;


import java.util.ArrayList;
import java.util.List;

public class IpAddressRange {
    private IpAddress startIpAddress = null;
    private IpAddress endIpAddress = null;
    public IpAddressRange( IpAddress startIpAddress, IpAddress endIpAddress){
       if( startIpAddress.compareTo(endIpAddress) > 0 ){
           throw new IllegalArgumentException("Start ip Address must precede End ip Address");
       }
       this.startIpAddress = startIpAddress;
       this.endIpAddress = endIpAddress;;
    }
    public IpAddressRange( IpAddress startIpAddress){
        this.startIpAddress = startIpAddress;
        this.endIpAddress = startIpAddress;
    }
    public IpAddressRange( String startIpAddress ){
        this( new IpAddress(startIpAddress), new IpAddress(startIpAddress));
    }

    public IpAddressRange( String startIpAddressString, String endIpAddressString){
        this( new IpAddress(startIpAddressString), new IpAddress(endIpAddressString));
    }

    public IpAddress getStartIpAddress() {
        return startIpAddress;
    }

    public IpAddress getEndIpAddress() {
        return endIpAddress;
    }

    public List<IpAddress> list(){
        List<IpAddress> returnList = new ArrayList<IpAddress>();
        IpAddress ip = startIpAddress;
        returnList.add( startIpAddress );
        while( ip.compareTo(endIpAddress) < 0  ){
           ip = ip.add(1);
           returnList.add(ip);
        }
        return returnList;
    }





}
