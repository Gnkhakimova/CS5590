angular.module('CalculatorApp', [])
    .controller('CalculatorController', function($scope) {

            $scope.result = "0";
            $scope.input = "";
            $scope.num1=0;
            $scope.num2=0;
            $scope.beginCalc = true;
            $scope.oper =null;
            $scope.add = "+";
            $scope.subs="-";
            $scope.mult = "*";
            $scope.dev="/";

        $scope.update = function(btn){

          $scope.input += btn;

        };
        $scope.clear = function(result)
        {
            $scope.input="";
            $scope.result=0;
        }
        $scope.calculate = function (input) {
            for (var i = 0; i<input.length; i++)
            {
                if(input[i]=="+" || input[i]=="-" || input[i]=="*" || input[i]=="/")
                {
                    $scope.oper = input[i];
                    var operPosition = i;
                }
            }

            //var test = input.find($scope.oper);
            $scope.num1 = parseInt(input.substring(0,operPosition));
            $scope.num2 = parseInt(input.substring(operPosition+1,input.length));
            if($scope.oper=="+"){
                $scope.result = $scope.num1+$scope.num2;
            }
            else if($scope.oper=="-"){
                $scope.result = $scope.num1-$scope.num2;
            }
            else if($scope.oper=="*"){
                $scope.result = $scope.num1*$scope.num2;
            }
            else if($scope.oper=="/"){
                $scope.result = $scope.num1/$scope.num2;
            }
        } ;



    });