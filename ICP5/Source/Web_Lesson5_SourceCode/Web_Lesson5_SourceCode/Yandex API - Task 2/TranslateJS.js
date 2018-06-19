// 'use strict';

// Declare app level module which depends on views, and components
angular.module('App', [])

    .controller('Ctrl', function ($scope, $http) {
        $scope.mostRecentReview;
        $scope.translate = function () {
            var key = 'trnsl.1.1.20180619T052424Z.a6524ff57a9afa33.ae7bb436e06cccd1051329db4a12d392716c6e67'
            var text = document.getElementById("txt").value;
            var language = document.getElementById("lang").value;
            if (text != null && text != "") {

                //This is the API that gives the list of venues based on the place and search query.

                var handler = $http.get("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + key + "&text=" + text + "&lang=" + language);

                handler.success(function (data) {
                    if (data != null && data.text != null) {
                           $scope.result = data.text[0];
                        };

                    }
                );
                handler.error(function (data) {
                    alert("There was some error processing your request. Please try after some time.");
                });
            }
        }
    });
