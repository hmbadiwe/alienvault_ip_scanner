package com.example.portScanner.rest;

import com.example.portScanner.data.IpAddressPortTuple;
import com.example.portScanner.data.IpAddressRange;
import com.example.portScanner.PortScanner;
import com.example.portScanner.Protocol;
import com.example.portScanner.ScannerResultSet;
import com.example.portScanner.pager.IpAddressBoundaryPortPager;
import com.example.portScanner.pager.IpAddressPortRangePager;
import com.example.portScanner.pager.IpAddressPortSelectionPager;
import com.example.portScanner.rest.data.PageQuery;
import com.example.portScanner.rest.data.Payload;
import com.example.portScanner.rest.data.PortQueryResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/11/13
 * Time: 2:17 AM
 * To change this template use File | Settings | File Templates.
 */

@Path("/ip-range")
public class IpAddressRestResource {

    @Path("/portScan")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<PortQueryResult> portScan(PageQuery query){
        int page = query.getPage() == null ? 0 : query.getPage().intValue();
        int numPerPage = query.getNumPerPage() == null ? 10 : query.getNumPerPage().intValue();
        IpAddressBoundaryPortPager pager = null;
        if( query.isSelection() ){
           pager = new IpAddressPortSelectionPager(
                   query.getStartingIpAddress(),
                   query.getEndingIpAddress(),
                   query.getPortList()
            );
        }
        else{
            pager = new IpAddressPortRangePager(
                query.getStartingIpAddress(),
                query.getEndingIpAddress(),
                query.getPortRange().getStart(),
                query.getPortRange().getEnd()
            );
        }
        int offset = page * numPerPage;
        List<IpAddressPortTuple> tuples = pager.pageWithOffSet( query.getNumPerPage(), offset );
        ScannerResultSet resultSet = new ScannerResultSet(tuples);
        PortScanner scanner = new PortScanner( resultSet.data() );
        return scanner.scanPorts();
    }

    @Path("/count")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public HashMap<String, Integer> count(PageQuery p){
        Integer count = null;
        if( p.getPortRange() == null && p.getPortList() == null ){
            throw new IllegalArgumentException("A port range must be specified");
        }
        String startIpAddress = p.getStartingIpAddress();
        String endIpAddress = p.getEndingIpAddress();
        if( endIpAddress == null || endIpAddress.trim().length() == 0 ){
            endIpAddress = startIpAddress;
        }
        if( p.getPortRange() != null ){
            IpAddressPortRangePager pager =
                    new IpAddressPortRangePager( startIpAddress, endIpAddress, p.getPortRange().getStart(), p.getPortRange().getEnd());
            count = pager.countIpPortTuples();
        }
        else{
            IpAddressPortSelectionPager pager = new IpAddressPortSelectionPager( startIpAddress, endIpAddress, p.getPortList());
            count = pager.countIpPortTuples();
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put( "count", count );
        return map;
    }
}
