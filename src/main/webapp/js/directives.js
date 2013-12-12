/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/10/13
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */

ipAddressRange.directive( 'pageresults', function(){
      return {
           restrict : 'A',
           templateUrl : '/templates/pageResults.html',
           scope : {
               numPerPage : '='
           },
           controller : function( $scope, $http ){

              $scope.queryResults = function(page){
                   console.log( "Which Page: " + page );
              };
              $scope.nav = {
                  currentPage : 0
              };


             $scope.queriedResults = [];



             $scope.$watch( '$parent.results', function(newVal){
                 console.log("Results changed");
                 if( newVal != undefined || newVal.length > 0 ){
                     var sliceOfResults = $scope.$parent.results.slice( 0, $scope.numberPerPage );
                     $http({ method: "POST", url : "/rest/ipAddressPayload/portScan", data: sliceOfResults })
                         .success( function( data ){
                             $scope.queriedResults = data;
                         })
                         .error( function( error){
                             console.log( "Error getting data: " + error.data );
                         });
                     $scope.totalItems = newVal.length;
                 }
                 else{
                     $scope.queriedResults = [];
                     $scope.totalItems = 0;

                 }
                 $scope.nav.currentPage = 0;
                 $scope.nav.numberOfPages = Math.ceil( $scope.$parent.results.length/$scope.numPerPage );


             });

           }


      };
}) ;
