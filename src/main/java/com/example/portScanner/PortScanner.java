package com.example.portScanner;

import com.example.portScanner.rest.data.PortQuery;
import com.example.portScanner.rest.data.PortQueryResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 11/26/13
 * Time: 7:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class PortScanner {
   private Log logger = LogFactory.getLog(PortScanner.class);
   private List<PortQuery> portQueryList;

   public PortScanner( List<PortQuery> portQueryList ){
       this.portQueryList = portQueryList;
   }
   public List<PortQueryResult> scanPorts(){
       List<PortQueryResult> queryResultList = new ArrayList<PortQueryResult>();
       for( PortQuery p : this.portQueryList){
           queryResultList.add( scanTcpPort(p));
       }
       return queryResultList;
   }
   private PortQueryResult scanTcpPort( PortQuery p ){
       PortQueryResult result = null;
       Socket s = new Socket();
       IpAddressType ipAddressType = IpAddress.addressType(p.getIpAddress());
       try{
           if( ipAddressType == IpAddressType.BROADCAST || ipAddressType == IpAddressType.NETWORK ){
              result = new PortQueryResult( p, ipAddressType, false );
           }
           else{
               s.connect( new InetSocketAddress( p.getIpAddress(), p.getPort()), 200);
               result = new PortQueryResult( p, ipAddressType, true );
           }
       }
       catch (Exception e){
           result = new PortQueryResult(p, ipAddressType, false);
           logger.debug("Failed to connect to " + p.getIpAddress() + " " + p.getPort());
       }
       finally {
           if( s != null && s.isClosed()){
               try{
                   s.close();
               }
               catch( Exception e){
                  logger.error( "Error closing socket...");
               }
           }
       }
       return result;

   }
}
