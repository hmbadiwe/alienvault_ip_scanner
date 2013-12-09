package com.example.portScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 11/26/13
 * Time: 7:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Scanner {
    Pattern p = Pattern.compile( "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})(\\.\\d{1,3})" );
    private List<String> addressList;
    public Scanner( String start, String finish){


    }

    public Scanner( String start ){
        Matcher m = p.matcher( start );
        if( m.find() && ( m.groupCount() == 4 || m.groupCount() == 5 ) ){
            if( m.groupCount() == 4){
               String ipSubnet = createIpAddressFromMatcher(m);
               setAddressList( createIpAddressRange(ipSubnet, 1));
            }
            else{
                List<String> aList = new ArrayList<String>();
                aList.add( createIpAddressFromMatcher(m));

                setAddressList( aList );
            }
        }
        else{
            throw new IllegalArgumentException("Error parsing ip address");
        }
    }

    private String createIpAddressFromMatcher(Matcher m){
        StringBuilder s = new StringBuilder();
        for( int i = 1; i < m.groupCount(); i++){
           s.append(m.group(i));
           if( i != ( m.groupCount() - 1) ){
               s.append( ".");
           }
        }
        return s.toString();
    }
    private List<String> createIpAddressRange(String subnet, Integer start){
        List<String> ipAddresses = new ArrayList<String>();

        for( int i = start; i < 255; i++ ){
           ipAddresses.add( subnet + "." + i );
        }
        return ipAddresses;
    }

    public List<String> getAddressList(){
        return addressList;
    }
    private void setAddressList(List<String> addressList){
        this.addressList = addressList;
    }
}
