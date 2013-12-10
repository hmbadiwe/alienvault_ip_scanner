package com.example.portScanner;

import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/4/13
 * Time: 12:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class IpAddress implements Comparable<IpAddress>{
    private Integer firstOctet;
    private Integer secondOctet;
    private Integer thirdOctet;
    private Integer lastOctet;

    public IpAddress(String ipAddressString){
        if( InetAddressValidator.getInstance().isValidInet4Address(ipAddressString) ){
          String[] ipAddressBitStrings = ipAddressString.split("\\.");
            firstOctet = Integer.parseInt( ipAddressBitStrings[0]);
            secondOctet = Integer.parseInt( ipAddressBitStrings[1]);
            thirdOctet = Integer.parseInt( ipAddressBitStrings[2]);
            lastOctet = Integer.parseInt( ipAddressBitStrings[3]);
        }
        else{
            throw new IllegalArgumentException("Invalid ip address");
        }
    }
    public IpAddress( int firstOctet, int secondOctet, int thirdOctet, int lastOctet){
        if( validOctet( firstOctet ) && validOctet( secondOctet ) && validOctet(thirdOctet) && validOctet( lastOctet ) ){
            this.firstOctet = firstOctet;
            this.secondOctet = secondOctet;
            this.thirdOctet = thirdOctet;
            this.lastOctet = lastOctet;
        }
        else{
            throw new IllegalArgumentException( "Invalid ip address");
        }
    }
    private boolean validOctet( int octet ){
        if( octet >= 0 && octet <= 255 ){
            return true;
        }
        return false;
    }
    @Override
    public int compareTo(IpAddress ipAddress) {

        int firstFourCompare = this.firstOctet.compareTo(ipAddress.firstOctet);
        if( firstFourCompare != 0  ){
            return firstFourCompare;
        }
        else{
            int secondFourCompare = this.secondOctet.compareTo(ipAddress.secondOctet);
            if( secondFourCompare != 0 ){
                return secondFourCompare;
            }
            else{
                int thirdFourCompare = this.thirdOctet.compareTo(ipAddress.thirdOctet);
                if( thirdFourCompare != 0 ){
                    return thirdFourCompare;
                }
                else{
                    return this.lastOctet.compareTo( ipAddress.lastOctet);
                }
            }
        }
    }

    public Integer getFirstOctet() {
        return firstOctet;
    }

    public Integer getSecondOctet() {
        return secondOctet;
    }

    public Integer getThirdOctet() {
        return thirdOctet;
    }

    public Integer getLastOctet() {
        return lastOctet;
    }
    public IpAddress add(int count){
       int lastOctet = getLastOctet();
       int thirdOctet = getThirdOctet();
       int secondOctet = getSecondOctet();
       int firstOctet = getFirstOctet();

       lastOctet += count;
       if( lastOctet > 255 ){
           lastOctet %= 256;
           thirdOctet += 1;
       }
       if( thirdOctet > 255 ){
           thirdOctet %= 256;
           secondOctet += 1;
       }
       if( secondOctet > 255 ){
           secondOctet %= 256;
           firstOctet += 1;
       }
       return new IpAddress( firstOctet, secondOctet, thirdOctet, lastOctet );
    }

    public String toString(){
        return String.format( "%d.%d.%d.%d", this.firstOctet, this.secondOctet, this.thirdOctet, this.lastOctet);
    }

    public int differenceCount( String ipAddress ){
        return  differenceCount( new IpAddress(ipAddress));
    }
    public int differenceCount( IpAddress ip ){
        int compareDifference = compareTo( ip );
        int returnValue;
        if( compareDifference > 0  ){
           int lastFourDiff = getLastOctet() - ip.getLastOctet();
           int thirdFourDiff = getThirdOctet() - ip.getThirdOctet();
           int secondFourDiff = getSecondOctet() - ip.getSecondOctet();
           int firstFourDiff = getFirstOctet() - ip.getFirstOctet();

           if( lastFourDiff < 0 ){
               lastFourDiff += 256;
               thirdFourDiff -= 1;
           }
           if( thirdFourDiff < 0 ){
               thirdFourDiff += 256;
               secondFourDiff -= 1;
           }
           if( secondFourDiff < 0 ){
               secondFourDiff += 256;
               firstFourDiff -= 1;
           }
           if( firstFourDiff < 0 ){
               throw new IllegalStateException("Error differencing two ip addresses. This should never happen!");
           }
           returnValue = lastFourDiff + 256 * thirdFourDiff + ( 256*256 ) * secondFourDiff + (256 * 256 * 256) * firstFourDiff;
        }
        else if( compareDifference == 0 ){
          returnValue = 0;
        }
        else{
            throw new IllegalArgumentException( "Error comparing ipaddress. IpAddress must be 'less than or equal to' this one");
        }
        return returnValue;
    }

}
