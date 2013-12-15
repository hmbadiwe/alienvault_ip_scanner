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
   $scope.resultsPresent = false;
   $scope.query = {
       page : 0,
       queryCount : 0
   };


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
           var query = $scope.query;
           if(_.isEmpty(query.portList) && _.isEmpty(query.portRange) ){
               $scope.portSelectionInvalid = true;
           }
           else if( $scope.selectPortStyle == 'Range' && query.portRange && query.portRange.start >= query.portRange.end ){
               $scope.invalidRange = true;
           }
           else{
               $http({ method: "POST", url : "/rest/ip-range/count", data: query })
                   .success( function( data ){
                       $scope.query.queryCount = data.count;
                       $scope.query.totalItems = data.count;
                       /*var count = $scope.query.queryCount;
                       if( count == 1000){
                           count = 0;
                       }
                       else{
                           count += 1;
                       }
                       $scope.query.queryCount = count;*/
                   })
                   .error( function( error){

                   });
               $scope.resultsPresent = true;
           }


       }
   };

   function addRangeToQuery(range){
       var start = range.start || 0;
       var end = range.end || 0;
       if( start && end ){
           $scope.query.portRange = {};
           $scope.query.portRange.start = start;
           $scope.query.portRange.end = end;

       }
       else{
           $scope.query.portRange = undefined;
       }
   }
    function addPortsToQuery(portsObj){
        if( portsObj ){
            var ports = [];
            _(portsObj).each( function(value,key){
                if( value != "none"){
                    ports.push( Number(value));
                }
            });
            if( ports.length > 0 ){
                $scope.query.portList = ports;
            }
            else{
                $scope.query.portList = undefined;
            }
        }
    }

   $scope.$watch('startIpAddress', function(newVal){
      if( newVal ){
         $scope.query.startingIpAddress = ipAddress(newVal);
         if( $scope.useEndingIpAddress == false ){
            $scope.query.endingIpAddress = $scope.query.startingIpAddress;
         }
      }

   }, true);

    $scope.$watch('endIpAddress', function(newVal){
        if( newVal && $scope.useEndingIpAddress){
            $scope.query.endingIpAddress = ipAddress(newVal);
        }
        /*else{
            $scope.payload.endIpAddress = undefined;
        }*/

    }, true);
    $scope.$watch('useEndingIpAddress', function(newVal){
       if( !newVal ){
           $scope.query.endingIpAddress = $scope.query.startingIpAddress;
       }
       else{
           var endIpAddress = $scope.endIpAddress;
           $scope.query.endingIpAddress = ipAddress(endIpAddress);
       }
    });
    $scope.$watch( 'range', function(newVal){
        addRangeToQuery( newVal );
    }, true );


    $scope.$watch( 'commonPorts', function(newVal){
       addPortsToQuery(newVal);
    }, true);

    $scope.$watch( 'selectPortStyle', function( newVal){
        if( newVal =='Select'){
            addPortsToQuery( $scope.commonPorts );
            $scope.query.portRange = undefined;
            $scope.query.selection = true;

        }
        else if( newVal == 'Range'){
            addRangeToQuery( newVal );
            $scope.query.portList = undefined;
            $scope.query.selection = false;
        }
    });





}