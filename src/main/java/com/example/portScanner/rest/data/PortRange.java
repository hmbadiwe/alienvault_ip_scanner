package com.example.portScanner.rest.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/11/13
 * Time: 9:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class PortRange {
    public PortRange(){

    }
    private Integer start;
    private Integer end;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public List<Integer> generatePortRange(){
        List<Integer> returnList = new ArrayList<Integer>();
        for(int i = start; i <= end; i++ ){
            returnList.add( i );
        }
        return returnList;
    }
}
