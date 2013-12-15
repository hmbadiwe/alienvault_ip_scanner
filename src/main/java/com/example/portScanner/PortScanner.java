package com.example.portScanner;

import com.example.portScanner.data.IpAddress;
import com.example.portScanner.data.IpAddressType;
import com.example.portScanner.rest.data.PortQuery;
import com.example.portScanner.rest.data.PortQueryResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.example.portScanner.ScannerResultSet.ScannerResult;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 11/26/13
 * Time: 7:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class PortScanner {
   private Log logger = LogFactory.getLog(PortScanner.class);
   private List<ScannerResult> scannerResultsList;

   public PortScanner( List<ScannerResult> scannerResultsList ){
       this.scannerResultsList = scannerResultsList;
   }
   public List<PortQueryResult> scanPorts(){
       List<PortQueryResult> queryResultList = new ArrayList<PortQueryResult>();
       for( ScannerResult s : this.scannerResultsList){
           queryResultList.add( scanTcpPort(s));
       }
       return queryResultList;
   }
   private PortQueryResult scanTcpPort( ScannerResult scannerResult ){
       PortQueryResult result = null;
       Socket s = new Socket();
       IpAddressType ipAddressType = IpAddress.addressType(scannerResult.getIpAddress());
       try{
           if( ipAddressType == IpAddressType.BROADCAST || ipAddressType == IpAddressType.NETWORK ){
              result = new PortQueryResult( scannerResult, ipAddressType, false );
           }
           else{
               s.connect( new InetSocketAddress( scannerResult.getIpAddress(), scannerResult.getPort()), 200);
               result = new PortQueryResult( scannerResult, ipAddressType, true );
           }
       }
       catch (Exception e){
           result = new PortQueryResult(scannerResult, ipAddressType, false);
           logger.debug("Failed to connect to " + scannerResult.getIpAddress() + " " + scannerResult.getPort());
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
