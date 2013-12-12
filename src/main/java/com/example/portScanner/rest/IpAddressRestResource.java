package com.example.portScanner.rest;

import com.example.portScanner.IpAddressRange;
import com.example.portScanner.PortScanner;
import com.example.portScanner.Protocol;
import com.example.portScanner.ScannerResultSet;
import com.example.portScanner.rest.data.Payload;
import com.example.portScanner.rest.data.PortQuery;
import com.example.portScanner.rest.data.PortQueryResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/11/13
 * Time: 2:17 AM
 * To change this template use File | Settings | File Templates.
 */

@Path("/ipAddressPayload")
public class IpAddressRestResource {


    @Path("/resultList")
    @POST()
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<ScannerResultSet.ScannerResult> payload(Payload p){
        List<Integer> listOfPorts = null;
        if( p.getPortRange() == null && p.getPortList() == null ){
           throw new IllegalArgumentException("A port range must be specified");
        }
        if( p.getPortRange() != null ){
            listOfPorts = p.getPortRange().generatePortRange();
        }
        else{
            listOfPorts = p.getPortList();
        }

        ScannerResultSet resultSet = new ScannerResultSet(
                new IpAddressRange( p.getStartIpAddress(), p.getEndIpAddress() ),
                listOfPorts, Protocol.TCP_ONLY);

        return resultSet.data();
    }

    @Path("/portScan")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<PortQueryResult> portScan(List<PortQuery> portQueryList){
        PortScanner scanner = new PortScanner( portQueryList );
        return scanner.scanPorts();
    }
}