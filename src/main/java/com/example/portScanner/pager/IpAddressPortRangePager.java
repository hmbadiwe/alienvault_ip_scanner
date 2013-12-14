package com.example.portScanner.pager;

import com.example.portScanner.data.IpAddress;
import com.example.portScanner.data.IpAddressPortTuple;
import com.example.portScanner.data.IpAddressRange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/13/13
 * Time: 11:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class IpAddressPortRangePager extends IpAddressBoundaryPortPager{
    private int startPort;
    private int endPort;
    private IpAddress startIpAddress;
    private IpAddress endIpAddress;

    public IpAddressPortRangePager(IpAddress startIpAddress, IpAddress endIpAddress, int startPort, int endPort){
      if( endIpAddress.compareTo(startIpAddress) < 0 ){
           throw new IllegalArgumentException("Starting ip address must precede or be the same as ending ip address");
      }
      if( startPort < 1  || endPort > 65535 ){
          throw new IllegalArgumentException("starting point must be between 0 and 65535");
      }
      this.startIpAddress = startIpAddress;
      if( endIpAddress == null ){
          this.endIpAddress = startIpAddress;
      }
      else{
          this.endIpAddress = endIpAddress;
      }

      this.startPort = startPort;
      this.endPort = endPort;
    }
    public IpAddressPortRangePager( IpAddress startIpAddress, int startPort, int endPort ){
        this( startIpAddress, startIpAddress, startPort, endPort );
    }
    public IpAddressPortRangePager( String startingIpAddressString, int startPort, int endPort ){
        this( new IpAddress(startingIpAddressString), startPort, endPort );
    }
    public IpAddressPortRangePager( String startingIpAddressString, String endingIpAddressString, int startPort, int endPort ){
        this( new IpAddress(startingIpAddressString), new IpAddress(endingIpAddressString), startPort, endPort );
    }
    @Override
    public List<IpAddressPortTuple> listTuples() {
        List<IpAddressPortTuple> ipAddressPortTupleList = new ArrayList<IpAddressPortTuple>();
        IpAddressRange range = new IpAddressRange(this.startIpAddress, this.endIpAddress);
        List<IpAddress> listIpAddresses = range.list();
        for( IpAddress ipAddress : listIpAddresses ){
            for( int port = this.startPort; port <= this.endPort; port++ ){
                IpAddressPortTuple tuple = new IpAddressPortTuple( ipAddress.toString(), port );
                ipAddressPortTupleList.add( tuple );
            }
        }
        return ipAddressPortTupleList;
    }

    @Override
    public int countIpPortTuples() {
        int numberPorts = endPort - startPort + 1;
        int ipAddressDifference = this.getEndIpAddress().differenceCount(this.getStartIpAddress()) + 1;
        return numberPorts * ipAddressDifference;
    }

    @Override
    protected List<IpAddressPortTuple> pageWithOffSet(IpAddressPortTuple tuple, int count, int offset) {

        if( tuple.getPort() > this.getEndPort() || tuple.getPort() < this.getStartPort()){
            throw new IllegalArgumentException("Port tuple is out of bounds");
        }

        IpAddress ipAddress = new IpAddress( tuple.getIpAddress() );

        if( ipAddress.compareTo(this.startIpAddress) < 0 ){
            throw new IllegalArgumentException("Ip address precedes page range than this one");
        }
        int port  = tuple.getPort();
        List<IpAddressPortTuple> returnList = new ArrayList<IpAddressPortTuple>();
        for( int index = 0; index < (offset + count ); index++ ){
           if( ipAddress.compareTo(this.endIpAddress) > 0 ){
               break;
           }

           if( index >= offset ){
               IpAddressPortTuple tupleToAdd = new IpAddressPortTuple(ipAddress.toString(), port );
               returnList.add(tupleToAdd);
           }
           port += 1;
           if( port > this.endPort ){
               ipAddress = ipAddress.add(1);
               port = this.getStartPort();
           }
        }

        return returnList;
    }

    public int getStartPort() {
        return startPort;
    }

    public int getEndPort() {
        return endPort;
    }

    public IpAddress getStartIpAddress() {
        return startIpAddress;
    }

    public IpAddress getEndIpAddress() {
        return endIpAddress;
    }
}
