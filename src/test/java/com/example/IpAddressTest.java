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
        assertEquals( i.getFirstOctet().intValue(), 10);
        assertEquals( i.getSecondOctet().intValue(), 0);
        assertEquals( i.getThirdOctet().intValue(), 0);
        assertEquals( i.getLastOctet().intValue(), 1);
    }
    @Test public void testValidIpAddressCreationWithNumbers(){
        IpAddress i = new IpAddress( 10, 100, 21, 5);
        assertEquals( i.getFirstOctet().intValue(), 10);
        assertEquals( i.getSecondOctet().intValue(), 100);
        assertEquals( i.getThirdOctet().intValue(), 21);
        assertEquals( i.getLastOctet().intValue(), 5);

        i = new IpAddress( 0, 1, 0, 255);
        assertEquals( i.getFirstOctet().intValue(), 0);
        assertEquals( i.getSecondOctet().intValue(), 1);
        assertEquals( i.getThirdOctet().intValue(), 0);
        assertEquals( i.getLastOctet().intValue(), 255);
    }
    @Test
    public void testIpAddressDuplication(){
        IpAddress i = new IpAddress( "10.0.0.1");

        i = new IpAddress(i);
        assertEquals( i.getFirstOctet().intValue(), 10);
        assertEquals( i.getSecondOctet().intValue(), 0);
        assertEquals( i.getThirdOctet().intValue(), 0);
        assertEquals( i.getLastOctet().intValue(), 1);
    }
    @Test(expected = IllegalArgumentException.class) public void testInvalidIpAddressCreationWithNumbers1(){
        new IpAddress( 256, 1, 1, 1);
    }
    @Test(expected = IllegalArgumentException.class) public void testInvalidIpAddressCreationWithNumbers2(){
        new IpAddress( 255, 1, 1, -1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIpAddressCreation0(){
         new IpAddress( "FEAR");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIpAddressCreation1(){
        new IpAddress( "10.0.0.256");
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
        IpAddress incrementedIpAddress = testIpAddress.add(10);

        assertEquals( 0, testIpAddress.compareTo(new IpAddress("127.0.0.1"))  );
        assertEquals( 0, incrementedIpAddress.compareTo( new IpAddress("127.0.0.11") )  );

        IpAddress borderIpAddress = new IpAddress( "127.0.0.254");
        incrementedIpAddress = borderIpAddress.add( 200 );
        assertEquals( 0, incrementedIpAddress.compareTo( new IpAddress("127.0.1.198") ) );
    }
    @Test
    public void testIpAddressDifference(){
       IpAddress testIpAddress = new IpAddress( "127.0.0.50");
       int difference = testIpAddress.differenceCount( new IpAddress("127.0.0.1") );
       assertEquals( 49, difference );

       int boundaryDifference = testIpAddress.differenceCount( new IpAddress("126.255.255.250"));
       assertEquals( 56, boundaryDifference );

    }

    @Test(expected = IllegalArgumentException.class )
    public void testInvalidIpAddressDifference(){
        IpAddress testIpAddress = new IpAddress( "127.0.0.50");
        testIpAddress.differenceCount( new IpAddress("127.0.0.51") );
    }

}
