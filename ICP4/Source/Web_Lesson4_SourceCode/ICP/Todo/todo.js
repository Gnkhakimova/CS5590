var angularTodo = angular.module('angularTodo', []);

angularTodo.controller('angularTodoC', ['$scope', function ($scope) {
    // define list of items
    $scope.items = [];
    $scope.pending=0;
    $scope.done=0;
    $scope.status="";

    // Write code to push new item

    $scope.submitNewItem = function (item) {
        $scope.items.push({name: item.name, completed: false});
        item.name = "";
        $scope.pending+=1;
    };

    // Write code to complete item
    $scope.completeItem = function (index) {
        // can flip to uncheck in case it was a mistake
        $scope.items[index].completed = !$scope.items[index].completed;
        $scope.pending-=1;
        $scope.done+=1;
    };

    // Write code to delete item

    $scope.deleteItem = function (index) {
        $scope.items.splice(index, 1);

        if($scope.pending !=0 && $scope.items[index].completed == false ) {
            $scope.pending -= 1;
        }
        if($scope.done !=0 && $scope.items[index].completed==true) {
            $scope.done -= 1;
        }
    };
}]);