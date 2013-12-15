/**
 * Created with IntelliJ IDEA.
 * User: hmbadiwe
 * Date: 12/10/13
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */

function cloner( obj ){
    return JSON.parse( JSON.stringify( obj ));
}

ipAddressRange.directive( 'pageresults', function(){
      return {
           restrict : 'A',
           templateUrl : '/templates/pageResults.html',
           scope : {
               query : '='
           },
           controller : function( $scope, $http ){

              $scope.queryClone = cloner( $scope.query );

              $scope.queryResults = function(page){
                queryResults(page - 1);

                   console.log( "Which Page: " + page );
              };
              $scope.nav = {
                  currentPage : 0
              };
              $scope.totalItems = 0;


             $scope.queriedResults = [];

             function queryResults(page){
                 $scope.queriedResults = [];
                 $scope.queryClone.page = page;
                 $http({ method: "POST", url : "/rest/ip-range/portScan", data: $scope.queryClone })
                     .success( function( data ){
                         $scope.queriedResults = data;
                         $scope.resultsPresent = true;
                     })
                     .error( function( error){
                         console.log( "Error getting data: " + error.data );
                     });
             }


             $scope.$watch( 'query.queryCount', function(newVal){
                 if( newVal  ){
                     console.log("Count changed: " + newVal);
                     $scope.queryClone = cloner( $scope.query);
                     $scope.query.queryCount = undefined;
                     queryResults( 0 );
                 }

             });

           }


      };
}) ;
