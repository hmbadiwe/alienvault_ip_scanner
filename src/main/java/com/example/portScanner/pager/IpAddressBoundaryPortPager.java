package com.example.portScanner.pager;

import com.example.portScanner.data.IpAddressPortTuple;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/13/13
 * Time: 9:58 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class IpAddressBoundaryPortPager extends IpAddressPortPager{
    public List<IpAddressPortTuple> pageInclusive(IpAddressPortTuple tuple, int count){
        return pageWithOffSet(tuple, count, 0 );
    }

    public List<IpAddressPortTuple> pageExclusive(IpAddressPortTuple tuple, int count){
        return pageWithOffSet(tuple, count, 1 );
    }

    public abstract List<IpAddressPortTuple> pageWithOffSet( int count, int offset );

}
