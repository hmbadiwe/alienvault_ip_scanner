package com.example.portScanner.pager;

import com.example.portScanner.data.IpAddressPortTuple;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/13/13
 * Time: 9:52 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class IpAddressPortPager {

    public abstract List<IpAddressPortTuple> listTuples();

    public abstract int countIpPortTuples();

    protected abstract List<IpAddressPortTuple> pageWithOffSet( IpAddressPortTuple tuple, int count, int offset );
}
