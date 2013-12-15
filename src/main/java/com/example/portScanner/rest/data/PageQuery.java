package com.example.portScanner.rest.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/14/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class PageQuery implements Serializable {
    private String startingIpAddress;
    private String endingIpAddress;
    private Integer numPerPage;
    private Integer page;
    private List<Integer> portList;
    private Boolean isSelection;
    private PortRange portRange;

    public PageQuery(){

    }

    public String getStartingIpAddress() {
        return startingIpAddress;
    }

    public void setStartingIpAddress(String startingIpAddress) {
        this.startingIpAddress = startingIpAddress;
    }

    public String getEndingIpAddress() {
        return endingIpAddress;
    }

    public void setEndingIpAddress(String endingIpAddress) {
        this.endingIpAddress = endingIpAddress;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPortList() {
        return portList;
    }

    public void setPortList(List<Integer> portList) {
        this.portList = portList;
    }

    public Boolean isSelection() {
        return isSelection;
    }
    public Boolean getSelection() {
        return isSelection;
    }

    public void setSelection(Boolean selection) {
        isSelection = selection;
    }

    public PortRange getPortRange() {
        return portRange;
    }

    public void setPortRange(PortRange portRange) {
        this.portRange = portRange;
    }
}
