package com.example;


import com.example.portScanner.data.IpAddressRange;
import com.example.portScanner.ScannerResultSet;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.example.portScanner.Protocol.*;
import com.example.portScanner.ScannerResultSet.ScannerResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/11/13
 * Time: 12:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScannerResultsTest {

    @Test
    public void testScannerResultsWithSingleIpAddress(){
        List<Integer> portList = new ArrayList<Integer>();
        portList.add(21);
        portList.add(22);
        portList.add(100);
        portList.add(1000);
        portList.add(600);
        ScannerResultSet resultSet = new ScannerResultSet( new IpAddressRange("127.0.0.1"), portList);
        assertEquals( BOTH, resultSet.getProtocol());
        List<ScannerResult> dataSet = resultSet.data();
        assertEquals( 10, dataSet.size());

        assertEquals(21, dataSet.get(0).getPort().intValue());
        assertEquals("File Transfer Protocol", dataSet.get(0).getService());
        assertEquals( "TCP", dataSet.get(0).getProtocol());
        assertEquals("127.0.0.1", dataSet.get(0).getIpAddress());

        assertEquals("UDP", dataSet.get(1).getProtocol());

        assertEquals("Secure Shell", dataSet.get(3).getService());
        assertEquals("UDP", dataSet.get(3).getProtocol());

        assertEquals( 100, dataSet.get(5).getPort().intValue());
        assertNull(dataSet.get(5).getService());
    }

    @Test
    public void testScannerWithMultipleIpAddresses(){
        List<Integer> portList = new ArrayList<Integer>();
        portList.add(21);
        portList.add(22);
        portList.add(100);
        portList.add(1000);
        portList.add(600);
        ScannerResultSet resultSet = new ScannerResultSet( new IpAddressRange("127.0.0.1", "127.0.0.3"), portList, TCP_ONLY);
        assertEquals( TCP_ONLY, resultSet.getProtocol());
        List<ScannerResult> dataSet = resultSet.data();
        assertEquals( 15, dataSet.size());
    }

}
