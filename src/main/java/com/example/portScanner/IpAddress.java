package com.example.portScanner;

import org.apache.commons.validator.routines.InetAddressValidator;

import java.net.Inet4Address;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/4/13
 * Time: 12:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class IpAddress implements Comparable<IpAddress>{
    private Integer firstFourBits;
    private Integer secondFourBits;
    private Integer thirdFourBits;
    private Integer lastFourBits;

    public IpAddress(String ipAddressString){
        if( InetAddressValidator.getInstance().isValidInet4Address(ipAddressString) ){
          String[] ipAddressBitStrings = ipAddressString.split("\\.");
            firstFourBits   = Integer.parseInt( ipAddressBitStrings[0]);
            secondFourBits  = Integer.parseInt( ipAddressBitStrings[1]);
            thirdFourBits   = Integer.parseInt( ipAddressBitStrings[2]);
            lastFourBits   = Integer.parseInt( ipAddressBitStrings[3]);
        }
        else{
            throw new IllegalArgumentException("Invalid ip address");
        }
    }
    @Override
    public int compareTo(IpAddress ipAddress) {

        int firstFourCompare = this.firstFourBits.compareTo(ipAddress.firstFourBits);
        if( firstFourCompare != 0  ){
            return firstFourCompare;
        }
        else{
            int secondFourCompare = this.secondFourBits.compareTo(ipAddress.secondFourBits);
            if( secondFourCompare != 0 ){
                return secondFourCompare;
            }
            else{
                int thirdFourCompare = this.thirdFourBits.compareTo(ipAddress.thirdFourBits);
                if( thirdFourCompare != 0 ){
                    return thirdFourCompare;
                }
                else{
                    return this.lastFourBits.compareTo( ipAddress.lastFourBits );
                }
            }
        }
    }

    public Integer getFirstFourBits() {
        return firstFourBits;
    }

    public Integer getSecondFourBits() {
        return secondFourBits;
    }

    public Integer getThirdFourBits() {
        return thirdFourBits;
    }

    public Integer getLastFourBits() {
        return lastFourBits;
    }

    public String toString(){
        return String.format( "%d.%d.%d.%d", this.firstFourBits, this.secondFourBits, this.thirdFourBits, this.lastFourBits );
    }
}
