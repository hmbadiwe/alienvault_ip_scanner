package com.example;


import com.example.portScanner.data.IpAddressPortTuple;
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
    public void testScannerResults(){
        List<IpAddressPortTuple> tuples = new ArrayList<IpAddressPortTuple>();
        tuples.add( new IpAddressPortTuple("127.0.0.1", 21 ) );
        tuples.add( new IpAddressPortTuple("127.0.0.1", 22 ) );
        tuples.add( new IpAddressPortTuple("127.0.0.1", 100 ) );
        tuples.add( new IpAddressPortTuple("127.0.0.1", 600 ) );
        tuples.add( new IpAddressPortTuple("127.0.0.1", 1000 ) );

        ScannerResultSet resultSet = new ScannerResultSet( tuples );
        assertEquals( TCP_ONLY, resultSet.getProtocol());
        List<ScannerResult> dataSet = resultSet.data();
        assertEquals( 5, dataSet.size());

        assertEquals(21, dataSet.get(0).getPort().intValue());
        assertEquals("File Transfer Protocol", dataSet.get(0).getService());
        assertEquals( "TCP", dataSet.get(0).getProtocol());
        assertEquals("127.0.0.1", dataSet.get(0).getIpAddress());

        assertEquals("TCP", dataSet.get(1).getProtocol());

        assertEquals("Secure Shell", dataSet.get(1).getService());
        assertEquals("TCP", dataSet.get(1).getProtocol());

        assertEquals( 1000, dataSet.get(4).getPort().intValue());
        assertNull(dataSet.get(4).getService());
    }

}
