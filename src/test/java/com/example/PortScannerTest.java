package com.example;


import com.example.portScanner.Scanner;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 11/26/13
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class PortScannerTest {

    @Test
    public void testPortScannerInitForAddressRange(){
        Scanner s = new Scanner("192.168.100.32", "192.168.100.255");
        List<String> ipAddressList = s.getAddressList();
        assertEquals( ipAddressList.size(), 223);
        assertEquals( ipAddressList.get(0), "192.168.100.32");
        assertEquals( ipAddressList.get(222), "192.168.100.255");

    }
    @Test
    public void testPortScannerInitForSubnetAddressRange(){
        Scanner s = new Scanner("192.168.100", "192.168.100.255");
        List<String> ipAddressList = s.getAddressList();
        assertEquals( ipAddressList.size(), 223);
        assertEquals( ipAddressList.get(0), "192.168.100.32");
        assertEquals( ipAddressList.get(222), "192.168.100.255");

    }
    @Test
    public void testPortScannerInitForSubnetSubnetRange(){
        Scanner s = new Scanner("192.168.100", "192.168.104");
        List<String> ipAddressList = s.getAddressList();
        assertEquals( ipAddressList.size(), 1016);
        assertEquals( ipAddressList.get(0), "192.168.100.1");
        assertEquals( ipAddressList.get(1015), "192.168.104.254");

    }
    @Test
    public void testPortScannerInitForSubnet(){
      Scanner s = new Scanner( "192.168.100" );
        List<String> ipAddressList = s.getAddressList();
        assertEquals( ipAddressList.size(), 254 );
        assertEquals( ipAddressList.get(0), "192.168.100.1");
        assertEquals( ipAddressList.get(253), "192.168.100.254");
    }
    @Test
    public void testPortScannerInitForSingleAddress(){
        Scanner s = new Scanner( "192.168.100.154" );
        List<String> ipAddressList = s.getAddressList();
        assertEquals( ipAddressList.size(), 1 );
        assertEquals( ipAddressList.get(0), "192.168.100.154");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testPortScannerInitForInvalidSubnet(){
        Scanner s = new Scanner( "192.168" );
    }
    @Test(expected = IllegalArgumentException.class)
    public void testPortScannerInitForInvalidAddress(){
        Scanner s = new Scanner( "192.168.100.257" );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPortScannerInitForInvalidBroadcastAddress(){
        Scanner s = new Scanner( "192.168.100.255");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPortScannerInitForInvalidNetworkAddress(){
        Scanner s = new Scanner( "192.168.100.0");
    }


}
