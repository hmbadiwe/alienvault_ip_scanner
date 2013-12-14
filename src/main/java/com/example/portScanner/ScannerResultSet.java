package com.example.portScanner;

import com.example.portScanner.data.IpAddress;
import com.example.portScanner.data.IpAddressRange;

import java.io.Serializable;
import java.util.*;

import static com.example.portScanner.Protocol.*;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/11/13
 * Time: 1:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScannerResultSet {
    public static class ScannerResult implements Serializable{
        public ScannerResult(){

        }
        Integer port;
        String ipAddress;
        String protocol;
        String service;

        public Integer getPort() {
            return port;
        }

        void setPort(Integer port) {
            this.port = port;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getProtocol() {
            return protocol;
        }

        void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getService() {
            return service;
        }

        void setService(String service) {
            this.service = service;
        }
    }
    private List<Integer> arrayOfPorts;
    private IpAddressRange range;
    private Protocol protocol;
    private HashMap<Integer, String> commonServicesMap = defaultServicesMap();


    public ScannerResultSet(IpAddressRange range, List<Integer> arrayOfPorts, Protocol protocol){
       this.range = range;
       this.arrayOfPorts = arrayOfPorts;
       this.protocol = protocol;
    }
    public ScannerResultSet(IpAddressRange range, List<Integer> arrayOfPorts ){
        this(range, arrayOfPorts, BOTH);
    }

    protected final HashMap<Integer, String> defaultServicesMap(){
        HashMap<Integer, String> returnMap = new HashMap<Integer, String>();
        returnMap.put( 7, "Echo Protocol");
        returnMap.put( 9, "Discard Protocol");
        returnMap.put( 21, "File Transfer Protocol");
        returnMap.put( 22, "Secure Shell");
        returnMap.put( 23, "Telnet");
        returnMap.put( 25, "Simple Mail Transfer Protocol");
        returnMap.put( 37, "Network Time Protocol");
        returnMap.put( 42, "Windows Internet Naming Service");
        returnMap.put( 43, "WHOIS");
        returnMap.put( 53, "Domain Naming Service");
        returnMap.put( 80, "Hypertext Transfer Protocol");
        returnMap.put( 109, "POP2");
        returnMap.put( 110, "POP3");
        returnMap.put( 143, "IMAP");
        returnMap.put( 443, "Secure Hypertext Transfer Protocol");

        return returnMap;
    }
    private List<String> getProtocolSet(){
        List<String> protocolStringSet = new ArrayList<String>();
        switch (this.protocol){
            case TCP_ONLY: {
                protocolStringSet.add("TCP");
                break;
            }
            case UDP_ONLY: {
                protocolStringSet.add("UDP");
                break;
            }
            case BOTH: {
                protocolStringSet.add("TCP");
                protocolStringSet.add("UDP");
                break;
            }
        }
        return protocolStringSet;
    }
    public List<ScannerResult> data(){
        List<ScannerResult> returnData = new ArrayList<ScannerResult>();
        List<String> protocolList = getProtocolSet();
        List<IpAddress> ipAddressList = this.range.list();
        for( IpAddress ipaddress : ipAddressList){
           for( Integer port : this.arrayOfPorts ){
               for( String protString : protocolList){
                   ScannerResult result = new ScannerResult();
                   result.setPort(port);
                   result.setProtocol(protString);
                   result.setIpAddress(ipaddress.toString());
                   result.setService(commonServicesMap.get( port ));
                   returnData.add( result);
               }
           }
        }
        return returnData;
    }
    public Protocol getProtocol(){
        return protocol;
    }
}
