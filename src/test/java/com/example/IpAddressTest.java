package com.example;
import static org.junit.Assert.*;

import com.example.portScanner.IpAddress;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/3/13
 * Time: 11:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class IpAddressTest {

    @Test
    public void testIpAddressCreation(){
        IpAddress i = new IpAddress( "10.0.0.1");
        assertEquals( i.getFirstFourBits(), 10);
        assertEquals( i.getSecondFourBits(), 0);
        assertEquals( i.getThirdFourBits(), 0);
        assertEquals( i.getLastFourBits(), 1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIpAddressCreation0(){
         new IpAddress( "FEAR");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIpAddressCreation1(){
        new IpAddress( "10.0.0.256");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIpAddressCreation2(){
        new IpAddress( "10.0.0.0");
    }

    @Test
    public void testIpAddressCompare(){
        assertEquals( new IpAddress("10.0.0.1").compareTo( new IpAddress("10.0.0.1") ), 0);
        assertEquals( new IpAddress("10.0.0.1").compareTo( new IpAddress("10.0.0.2") ), -1);
        assertEquals( new IpAddress("10.0.0.1").compareTo( new IpAddress( "9.255.255.255") ), 1);
    }
    @Test
    public void testIncrementIpAddress(){
        IpAddress testIpAddress = new IpAddress("127.0.0.1");
        IpAddress incrementedIpAddress = testIpAddress.increment(10);

        assertEquals( testIpAddress.compareTo(new IpAddress("127.0.0.1")), 0  );
        assertEquals( incrementedIpAddress.compareTo( new IpAddress("127.0.0.11") ), 0  );

        IpAddress borderIpAddress = new IpAddress( "127.0.0.254");
        incrementedIpAddress = borderIpAddress.increment( 200 );
        assertEquals( incrementedIpAddress.compareTo( new IpAddress("127.0.1.199") ), 0  );
    }

    @Test
    public void testCountUntilEnd(){
        IpAddress testIpAddress = new IpAddress("255.255.255.1");
        assertEquals( testIpAddress.countUntilEnd(), 253 );
    }
}
