package com.example;

import static org.junit.Assert.*;

import com.example.portScanner.data.IpAddressPortTuple;
import com.example.portScanner.pager.IpAddressPortSelectionPager;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/13/13
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class IpAddressPortSelectionPagerTest {

    @Test(expected = IllegalArgumentException.class )
    public void testInvalidIpAddressPortSelectionPagerWithInvalidStartingPort(){
        new IpAddressPortSelectionPager("127.0.0.1", new ArrayList<Integer>( Arrays.asList(new Integer[]{40,45,20,21, 0 })));
    }
    @Test(expected = IllegalArgumentException.class )
    public void testInvalidIpAddressPortSelectionPagerWithInvalidEndingPort(){
        new IpAddressPortSelectionPager("127.0.0.1", new ArrayList<Integer>( Arrays.asList(new Integer[]{40,45,20,21, 65536 })));
    }
    @Test(expected = IllegalArgumentException.class )
    public void testInvalidIpAddressPortSelectionPagerWithInvalidAddressRange(){
        new IpAddressPortSelectionPager("127.0.0.1", "126.0.0.1", new ArrayList<Integer>( Arrays.asList(new Integer[]{40,45,20,21 })));
    }
    @Test(expected = IllegalArgumentException.class )
    public void testPageRangeBeforeBoundaries(){
        IpAddressPortSelectionPager ipPortSelectionPager = new IpAddressPortSelectionPager("127.0.0.2", "129.0.0.5", new ArrayList<Integer>( Arrays.asList(new Integer[]{40,45,20,21 })));
        ipPortSelectionPager.pageInclusive( new IpAddressPortTuple("127.0.0.1", 45), 10);
    }
    @Test
    public void testPageRangeAfterBoundaries(){
        IpAddressPortSelectionPager ipPortSelectionPager = new IpAddressPortSelectionPager("127.0.0.2", "129.0.0.5", new ArrayList<Integer>( Arrays.asList(new Integer[]{40,45,20,21 })));
        List<IpAddressPortTuple> tuples = ipPortSelectionPager.pageInclusive( new IpAddressPortTuple("129.0.0.6", 45), 10);
        assertEquals( 0, tuples.size());

        tuples = ipPortSelectionPager.pageExclusive( new IpAddressPortTuple("129.0.0.5", 45), 10);
        assertEquals( 0, tuples.size());

    }


    @Test
    public void testSingleIpAddressWithMultiplePorts(){

        IpAddressPortSelectionPager ipPortRangePager = new IpAddressPortSelectionPager("127.0.0.1", new ArrayList<Integer>( Arrays.asList(new Integer[]{40,45,20,21})));
        assertEquals(4, ipPortRangePager.countIpPortTuples());
        List<IpAddressPortTuple> tuples = ipPortRangePager.listTuples();
        assertEquals( 4, tuples.size());
        assertEquals( "127.0.0.1", tuples.get(0).getIpAddress() );
        assertEquals( 20, tuples.get(0).getPort() );

        assertEquals( "127.0.0.1", tuples.get(3).getIpAddress() );
        assertEquals( 45, tuples.get(3).getPort() );

    }

    @Test
    public void testIpAddressRangeWithDuplicatePorts(){
        IpAddressPortSelectionPager ipPortRangePager = new IpAddressPortSelectionPager("127.0.0.1", new ArrayList<Integer>( Arrays.asList(new Integer[]{40,45,20,21,45})));
        List<Integer> ports = ipPortRangePager.getPorts();
        assertEquals( 4, ports.size() );
        assertEquals( 20, ports.get(0).intValue());
        assertEquals( 45, ports.get(3).intValue());
    }

    @Test
    public void testIpAddressRangeWithMultiplePorts(){
        IpAddressPortSelectionPager ipPortRangePager = new IpAddressPortSelectionPager("127.0.0.1", "127.0.0.5", new ArrayList<Integer>( Arrays.asList(new Integer[]{40,22})));
        assertEquals( 10, ipPortRangePager.countIpPortTuples());
        List<IpAddressPortTuple> tuples = ipPortRangePager.listTuples();
        assertEquals( 10, tuples.size());

        assertEquals( "127.0.0.1", tuples.get(0).getIpAddress() );
        assertEquals( 22, tuples.get(0).getPort());

        assertEquals( "127.0.0.1", tuples.get(1).getIpAddress() );
        assertEquals( 40, tuples.get(1).getPort());

        assertEquals( "127.0.0.4", tuples.get(7).getIpAddress() );
        assertEquals( 40, tuples.get(7).getPort());
    }

    @Test
    public void testPagingOfIpAddressRangeWithMultiplePortsInclusive(){
        IpAddressPortSelectionPager ipPortRangePager = new IpAddressPortSelectionPager("127.0.0.1", "127.0.0.5", new ArrayList<Integer>( Arrays.asList(new Integer[]{23, 37, 15})));

        List<IpAddressPortTuple> tuples = ipPortRangePager.pageInclusive( new IpAddressPortTuple("127.0.0.2", 37 ), 5 );
        assertEquals( 5, tuples.size());

        assertEquals( "127.0.0.2", tuples.get(0).getIpAddress());
        assertEquals( 37, tuples.get(0).getPort());


        assertEquals( "127.0.0.3", tuples.get(2).getIpAddress());
        assertEquals( 23, tuples.get(2).getPort());

        assertEquals( "127.0.0.4", tuples.get(4).getIpAddress());
        assertEquals( 15, tuples.get(4).getPort());

        tuples = ipPortRangePager.pageInclusive( new IpAddressPortTuple("127.0.0.5", 15 ), 5 );
        assertEquals( 3, tuples.size());

        assertEquals( "127.0.0.5", tuples.get(0).getIpAddress());
        assertEquals( 15, tuples.get(0).getPort());


        assertEquals( "127.0.0.5", tuples.get(2).getIpAddress());
        assertEquals( 37, tuples.get(2).getPort());



    }
    @Test
    public void testPagingOfIpAddressRangeWithMultiplePortsExclusive(){
        IpAddressPortSelectionPager ipPortRangePager = new IpAddressPortSelectionPager("127.0.0.1", "127.0.0.5", new ArrayList<Integer>( Arrays.asList(new Integer[]{23, 37, 15})));

        List<IpAddressPortTuple> tuples = ipPortRangePager.pageInclusive( new IpAddressPortTuple("127.0.0.2", 37 ), 5 );
        assertEquals( 5, tuples.size());

        assertEquals( "127.0.0.2", tuples.get(0).getIpAddress());
        assertEquals( 37, tuples.get(0).getPort());


        assertEquals( "127.0.0.3", tuples.get(2).getIpAddress());
        assertEquals( 23, tuples.get(2).getPort());

        assertEquals( "127.0.0.4", tuples.get(4).getIpAddress());
        assertEquals( 15, tuples.get(4).getPort());

        tuples = ipPortRangePager.pageExclusive( new IpAddressPortTuple("127.0.0.5", 15 ), 5 );
        assertEquals( 2, tuples.size());

        assertEquals( "127.0.0.5", tuples.get(0).getIpAddress());
        assertEquals( 23, tuples.get(0).getPort());


        assertEquals( "127.0.0.5", tuples.get(1).getIpAddress());
        assertEquals( 37, tuples.get(1).getPort());

    }



}
