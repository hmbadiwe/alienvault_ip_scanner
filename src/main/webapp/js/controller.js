/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/10/13
 * Time: 8:25 PM
 * To change this template use File | Settings | File Templates.
 */


function IpAddressFormController( $scope, $http ){
   $scope.selectPortStyle = 'Select';
   $scope.useEndingIpAddress = false;
   $scope.startIpAddress = {};
   $scope.endIpAddress = {};
   $scope.range = {};
   $scope.results = [];
   $scope.resultsPresent = false;
   $scope.payload = {};

   var defaultCommonPorts = {
       ssh : 'none',
       telnet : 'none',
       ftp : 'none',
       smtp : 'none',
       http : 'none',
       https : 'none',
       dns : 'none',
       ntp : 'none'
   } ;
   $scope.commonPorts = _(defaultCommonPorts).clone();
   function ipAddress(ipObj){
       var returnAddress = undefined;
       if( ipObj.firstOctet != undefined
           && ipObj.secondOctet != undefined
           && ipObj.thirdOctet != undefined
           && ipObj.lastOctet != undefined){
           returnAddress =  ipObj.firstOctet + "." + ipObj.secondOctet + "." +  ipObj.thirdOctet + "." + ipObj.lastOctet;
       }
       return returnAddress;
   }
   $scope.submitForm = function(){
       $scope.portSelectionInvalid = false;
       $scope.invalidRange = false;
       if( $scope.ipAddressRangeForm.$valid ){
           var payload = $scope.payload;
           if(_.isEmpty(payload.portList) && _.isEmpty(payload.portRange) ){
               $scope.portSelectionInvalid = true;
           }
           else if( $scope.selectPortStyle == 'Range' && payload.portRange && payload.portRange.start >= payload.portRange.end ){
               $scope.invalidRange = true;
           }
           else{
               $http({ method: "POST", url : "/rest/ipAddressPayload/resultList", data: payload })
                   .success( function( data ){
                       $scope.results = data;
                   })
                   .error( function( error){

                   });
               $scope.resultsPresent = true;
           }


       }
   };

   function addRangeToPayload(range){
       var start = range.start || 0;
       var end = range.end || 0;
       if( start && end ){
           $scope.payload.portRange = {};
           $scope.payload.portRange.start = start;
           $scope.payload.portRange.end = end;

       }
       else{
           $scope.payload.portRange = undefined;
       }
   }
    function addPortsToPayload(portsObj){
        if( portsObj ){
            var ports = [];
            _(portsObj).each( function(value,key){
                if( value != "none"){
                    ports.push( Number(value));
                }
            });
            if( ports.length > 0 ){
                $scope.payload.portList = ports;
            }
            else{
                $scope.payload.portList = undefined;
            }
        }
    }

   $scope.$watch('startIpAddress', function(newVal){
      if( newVal ){
         $scope.payload.startIpAddress = ipAddress(newVal);
      }

   }, true);

    $scope.$watch('endIpAddress', function(newVal){
        if( newVal && $scope.useEndingIpAddress){
            $scope.payload.endIpAddress = ipAddress(newVal);
        }
        /*else{
            $scope.payload.endIpAddress = undefined;
        }*/

    }, true);
    $scope.$watch('useEndingIpAddress', function(newVal){
       if( !newVal ){
           $scope.payload.endIpAddress = undefined;
       }
       else{
           var endIpAddress = $scope.endIpAddress;
           $scope.payload.endIpAddress = ipAddress(endIpAddress);
       }
    });
    $scope.$watch( 'range', function(newVal){
        addRangeToPayload( newVal );
    }, true );


    $scope.$watch( 'commonPorts', function(newVal){
       addPortsToPayload(newVal);
    }, true);

    $scope.$watch( 'selectPortStyle', function( newVal){
        if( newVal =='Select'){
            addPortsToPayload( $scope.commonPorts );
            $scope.payload.portRange = undefined;

        }
        else if( newVal == 'Range'){
            addRangeToPayload( newVal );
            $scope.payload.portList = undefined;
        }
    });





}