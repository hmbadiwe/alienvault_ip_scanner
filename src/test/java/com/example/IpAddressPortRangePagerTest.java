package com.example;

import com.example.portScanner.data.IpAddressPortTuple;
import com.example.portScanner.pager.IpAddressPortRangePager;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/13/13
 * Time: 9:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class IpAddressPortRangePagerTest {

    @Test(expected = IllegalArgumentException.class )
    public void testInvalidIpAddressPortRangePagerWithInvalidStartingPort(){
        new IpAddressPortRangePager("127.0.0.1", 0, 100);
    }
    @Test(expected = IllegalArgumentException.class )
    public void testInvalidIpAddressPortRangePagerWithInvalidEndingPort(){
        new IpAddressPortRangePager("127.0.0.1", 10000, 65536);
    }
    @Test(expected = IllegalArgumentException.class )
    public void testInvalidIpAddressPortRangePagerWithInvalidAddressRange(){
        new IpAddressPortRangePager("127.0.0.1", "126.0.0.1", 100, 200);
    }
    @Test(expected = IllegalArgumentException.class )
    public void testPageRangeBeforeBoundaries(){
        IpAddressPortRangePager ipPortSelectionPager = new IpAddressPortRangePager("127.0.0.2", "129.0.0.5", 100,200 );
        ipPortSelectionPager.pageInclusive( new IpAddressPortTuple("127.0.0.1", 45), 10);
    }
    @Test(expected = IllegalArgumentException.class )
    public void testPageRangeBeforePortBoundaries(){
        IpAddressPortRangePager ipPortSelectionPager = new IpAddressPortRangePager("127.0.0.2", "129.0.0.5", 100,200 );
        ipPortSelectionPager.pageInclusive( new IpAddressPortTuple("127.0.0.1", 99), 10);
    }
    @Test(expected = IllegalArgumentException.class )
    public void testPageRangeAfterPortBoundaries(){
        IpAddressPortRangePager ipPortSelectionPager = new IpAddressPortRangePager("127.0.0.2", "129.0.0.5", 100,200 );
        ipPortSelectionPager.pageInclusive( new IpAddressPortTuple("127.0.0.1", 201), 10);
    }
    @Test
    public void testPageRangeAfterBoundaries(){
        IpAddressPortRangePager ipPortSelectionPager = new IpAddressPortRangePager("127.0.0.2", "129.0.0.5",  20 , 25);
        List<IpAddressPortTuple> tuples = ipPortSelectionPager.pageInclusive( new IpAddressPortTuple("129.0.0.6", 25), 10);
        assertEquals( 0, tuples.size());

        tuples = ipPortSelectionPager.pageExclusive( new IpAddressPortTuple("129.0.0.5", 25), 10);
        assertEquals( 0, tuples.size());

    }


    @Test
    public void testSingleIpAddressWithPortRange(){

        IpAddressPortRangePager ipPortRangePager = new IpAddressPortRangePager("127.0.0.1", 20, 25);
        assertEquals( 6 , ipPortRangePager.countIpPortTuples());
        List<IpAddressPortTuple> tuples = ipPortRangePager.listTuples();
        assertEquals( 6, tuples.size());
        assertEquals( "127.0.0.1", tuples.get(0).getIpAddress() );
        assertEquals( 20, tuples.get(0).getPort() );

        assertEquals( "127.0.0.1", tuples.get(3).getIpAddress() );
        assertEquals( 23, tuples.get(3).getPort() );

    }

    @Test
    public void testIpAddressRangeWithSameStartingAndEndingPort(){
        IpAddressPortRangePager ipPortRangePager = new IpAddressPortRangePager("127.0.0.1", 50, 50);
        assertEquals( 1 , ipPortRangePager.countIpPortTuples());
        List<IpAddressPortTuple> tuples = ipPortRangePager.listTuples();
        assertEquals( "127.0.0.1", tuples.get(0).getIpAddress() );
        assertEquals( 50, tuples.get(0).getPort() );
    }

    @Test
    public void testIpAddressRange(){
        IpAddressPortRangePager ipPortRangePager = new IpAddressPortRangePager("127.0.0.1", "127.0.0.5", 22, 25);
        assertEquals( 20, ipPortRangePager.countIpPortTuples());
        List<IpAddressPortTuple> tuples = ipPortRangePager.listTuples();
        assertEquals( 20, tuples.size());

        assertEquals( "127.0.0.1", tuples.get(0).getIpAddress() );
        assertEquals( 22, tuples.get(0).getPort());

        assertEquals( "127.0.0.1", tuples.get(1).getIpAddress() );
        assertEquals( 23, tuples.get(1).getPort());

        assertEquals( "127.0.0.2", tuples.get(7).getIpAddress() );
        assertEquals( 25, tuples.get(7).getPort());

        assertEquals( "127.0.0.5", tuples.get(17).getIpAddress() );
        assertEquals( 23, tuples.get(17).getPort());
    }

    @Test
    public void testPagingOfIpAddressRangeInclusive(){
        IpAddressPortRangePager ipPortRangePager = new IpAddressPortRangePager("127.0.0.1", "127.0.0.5", 22, 25);

        List<IpAddressPortTuple> tuples = ipPortRangePager.pageInclusive( new IpAddressPortTuple("127.0.0.2", 25 ), 5 );
        assertEquals( 5, tuples.size());

        assertEquals( "127.0.0.2", tuples.get(0).getIpAddress());
        assertEquals( 25, tuples.get(0).getPort());


        assertEquals( "127.0.0.3", tuples.get(2).getIpAddress());
        assertEquals( 23, tuples.get(2).getPort());

        assertEquals( "127.0.0.3", tuples.get(4).getIpAddress());
        assertEquals( 25, tuples.get(4).getPort());

        tuples = ipPortRangePager.pageInclusive( new IpAddressPortTuple("127.0.0.5", 23 ), 5 );
        assertEquals( 3, tuples.size());

        assertEquals( "127.0.0.5", tuples.get(0).getIpAddress());
        assertEquals( 23, tuples.get(0).getPort());


        assertEquals( "127.0.0.5", tuples.get(2).getIpAddress());
        assertEquals( 25, tuples.get(2).getPort());



    }
    @Test
    public void testPagingOfIpAddressRangeExclusive(){
        IpAddressPortRangePager ipPortRangePager = new IpAddressPortRangePager("127.0.0.1", "127.0.0.5", 22, 25);

        List<IpAddressPortTuple> tuples = ipPortRangePager.pageExclusive( new IpAddressPortTuple("127.0.0.2", 23 ), 5 );
        assertEquals( 5, tuples.size());

        assertEquals( "127.0.0.2", tuples.get(0).getIpAddress());
        assertEquals( 24, tuples.get(0).getPort());


        assertEquals( "127.0.0.3", tuples.get(2).getIpAddress());
        assertEquals( 22, tuples.get(2).getPort());

        assertEquals( "127.0.0.3", tuples.get(4).getIpAddress());
        assertEquals( 24, tuples.get(4).getPort());

        tuples = ipPortRangePager.pageExclusive( new IpAddressPortTuple("127.0.0.5", 23 ), 5 );
        assertEquals( 2, tuples.size());

        assertEquals( "127.0.0.5", tuples.get(0).getIpAddress());
        assertEquals( 24, tuples.get(0).getPort());


        assertEquals( "127.0.0.5", tuples.get(1).getIpAddress());
        assertEquals( 25, tuples.get(1).getPort());

    }

}
