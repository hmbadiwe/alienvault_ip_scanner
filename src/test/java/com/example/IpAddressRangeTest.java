package com.example;

import com.example.portScanner.IpAddress;
import com.example.portScanner.IpAddressRange;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/9/13
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class IpAddressRangeTest {
    @Test
    public void testValidIpAddressRanges(){
        IpAddressRange range = new IpAddressRange("127.0.0.1", "127.0.0.20");
        assertEquals( range.getStartIpAddress(), new IpAddress("127.0.0.1") );
        assertEquals( range.getEndIpAddress(), new IpAddress("127.0.0.20") );

        range = new IpAddressRange("127.0.0.1");
        assertEquals( range.getStartIpAddress(), new IpAddress("127.0.0.1") );
        assertEquals( range.getEndIpAddress(), new IpAddress("127.0.0.1") );

        range = new IpAddressRange( new IpAddress("127.0.0.1") );
        assertEquals( range.getStartIpAddress(), new IpAddress("127.0.0.1") );
        assertEquals( range.getEndIpAddress(), new IpAddress("127.0.0.1") );

        range = new IpAddressRange( new IpAddress("127.0.0.1"), null );
        assertEquals( range.getStartIpAddress(), new IpAddress("127.0.0.1") );
        assertEquals( range.getEndIpAddress(), new IpAddress("127.0.0.1") );

        range = new IpAddressRange( "127.0.0.1", null );
        assertEquals( range.getStartIpAddress(), new IpAddress("127.0.0.1") );
        assertEquals( range.getEndIpAddress(), new IpAddress("127.0.0.1") );
    }


    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIpAddressRange(){
        new IpAddressRange("127.0.0.1", "126.0.0.20");
    }

    @Test
    public void testRetrieveIpAddresses(){
        IpAddressRange ipRange = new IpAddressRange("127.0.0.250", "127.0.1.5");
        List<IpAddress> ipList = ipRange.list();

        assertEquals( 12, ipList.size());
        assertEquals( new IpAddress("127.0.0.250"), ipList.get(0));
        assertEquals( new IpAddress("127.0.1.1"), ipList.get(7));
        assertEquals( new IpAddress("127.0.1.5"), ipList.get(11));
    }




}
