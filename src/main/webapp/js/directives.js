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
                queryResults(page - 1);

                   console.log( "Which Page: " + page );
              };
              $scope.nav = {
                  currentPage : 0
              };


             $scope.queriedResults = [];

             function queryResults(index){
                 $scope.resultsPresent = false;
                 var numPerPage = Number($scope.numPerPage);
                 var startIndex = index * numPerPage;
                 var endIndex = startIndex + numPerPage;
                 var sliceOfResults = $scope.$parent.results.slice( startIndex, endIndex );
                 $scope.queriedResults = [];
                 $http({ method: "POST", url : "/rest/ipAddressPayload/portScan", data: sliceOfResults })
                     .success( function( data ){
                         $scope.queriedResults = data;
                         $scope.resultsPresent = true;
                     })
                     .error( function( error){
                         console.log( "Error getting data: " + error.data );
                     });
             }


             $scope.$watch( '$parent.results', function(newVal){
                 console.log("Results changed");
                 if( newVal != undefined || newVal.length > 0 ){
                     queryResults(0);
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
