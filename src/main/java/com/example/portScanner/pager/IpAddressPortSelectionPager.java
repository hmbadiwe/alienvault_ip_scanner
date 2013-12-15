package com.example.portScanner.pager;

import com.example.portScanner.data.IpAddress;
import com.example.portScanner.data.IpAddressPortTuple;
import com.example.portScanner.data.IpAddressRange;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/13/13
 * Time: 10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class IpAddressPortSelectionPager extends IpAddressBoundaryPortPager {

    private IpAddress startingIpAddress;
    private IpAddress endingIpAddress;
    private List<Integer> ports;

    public List<Integer> getPorts() {
        return ports;
    }

    public IpAddressPortSelectionPager( IpAddress startingIpAddress, IpAddress endingIpAddress, List<Integer> ports){
        if( endingIpAddress.compareTo(startingIpAddress) < 0 ){
            throw new IllegalArgumentException( "Ending ip address must precede or be the same as starting ip address");
        }
        this.ports = new ArrayList<Integer>( new TreeSet<Integer>(ports) );
        {
            if( this.ports.get(0) <= 0 || this.ports.get( this.ports.size() - 1) > 65535){
                throw new IllegalArgumentException("Must contain a port between 1 and 65535");
            }
        }
        this.startingIpAddress = startingIpAddress;
        this.endingIpAddress = endingIpAddress;


    }
    public IpAddressPortSelectionPager(String startingIpAddressString, String endingIpAddressString, List<Integer> ports){
       this( new IpAddress(startingIpAddressString), new IpAddress(endingIpAddressString), ports);
    }
    public IpAddressPortSelectionPager(String startingIpAddress, List<Integer> ports){
        this( startingIpAddress, startingIpAddress, ports);
    }
    @Override
    public List<IpAddressPortTuple> listTuples() {
        List<IpAddressPortTuple> ipAddressPortTupleList = new ArrayList<IpAddressPortTuple>();
        List<IpAddress> ipAddresses = new IpAddressRange(this.startingIpAddress, this.endingIpAddress).list();
        for( IpAddress ipAddress : ipAddresses ){
            for( Integer port : this.ports ){
                ipAddressPortTupleList.add( new IpAddressPortTuple(ipAddress.toString(), port ) );
            }
        }
        return ipAddressPortTupleList;
    }

    @Override
    public int countIpPortTuples() {
        int difference =  this.endingIpAddress.differenceCount(this.startingIpAddress) + 1;
        return difference * this.ports.size();
    }

    @Override
    public final List<IpAddressPortTuple> pageWithOffSet(IpAddressPortTuple tuple, int count, int offset) {

        int portIndex = this.ports.indexOf(tuple.getPort());
        if( portIndex < 0 ){
            portIndex = 0;
        }
        int portStartIndex = ( portIndex  ) % this.ports.size();

        List<IpAddressPortTuple> returnList = new ArrayList<IpAddressPortTuple>();
        IpAddress pageStartIpAddress = new IpAddress(tuple.getIpAddress());
        if(  pageStartIpAddress.compareTo(this.startingIpAddress) < 0 ){
           throw new IllegalArgumentException("Invalid ip address. Must precede page start");
        }
        IpAddress ipAddress = pageStartIpAddress;
        for( int i = 0; i < ( count + offset ); i++ ){
            if( ipAddress.compareTo(this.endingIpAddress) > 0 ){
                break;
            }
            int rotatingPortIndex = (portStartIndex + i) % this.ports.size();

            if( i >= offset ){
                IpAddressPortTuple tupleToAdd = new IpAddressPortTuple( ipAddress.toString(), this.ports.get( rotatingPortIndex));
                returnList.add( tupleToAdd );
            }

            if( rotatingPortIndex == this.ports.size() - 1 ){
                 ipAddress = ipAddress.add(1);
            }
        }


        return returnList;  //To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    public final List<IpAddressPortTuple> pageWithOffSet(int count, int offset) {
        return pageWithOffSet( new IpAddressPortTuple(this.startingIpAddress.toString(), this.ports.get(0)), count, offset);
    }
    public IpAddress getStartingIpAddress() {
        return startingIpAddress;
    }

    public IpAddress getEndingIpAddress() {
        return endingIpAddress;
    }
}
