
function getGithubInfo(user) {
    //1. Create an instance of XMLHttpRequest class and send a GET request using it. The function should finally return the object(it now contains the response!)

    var xhttp = new XMLHttpRequest();
    var url = 'https://api.github.com/search/users?q='+ user;
    xhttp.open('GET',url,false);
    xhttp.send();
return xhttp;
}

function showUser(user) {

    //2. set the contents of the h2 and the two div elements in the div '#profile' with the user content
    //var h2 = document.getSelection('h2');

    document.querySelector('h2').innerHTML = user['items'][0]['login'];

    var id = '<p>'+user['items'][0]['id']+'</p>';
    document.getElementById('ID').innerHTML = id;

    var avatar = '<img src=\"' + user['items'][0]['avatar_url'] + '\"'+'/>';
    document.getElementById('avatar').innerHTML = avatar;

    var url = '<a href=\"' + user['items'][0]['html_url'] + '\">' + user['items'][0]['html_url'] + '</a>';
    document.getElementById('information').innerHTML = url;

    //$('.ID').append('<p>'+user['items'][0]['id']+'</p>');
   // $('.avatar').append('<img src=\"'+user['items'][0]['avatar_url']+'\" style=\"height\: 100px\;width\: 100px\;\"/>');
   // $('.information').append('<a href=\"'+user['items'][0]['html_url']+'\">'+user['items'][0]['html_url']+'</a>');

}

function noSuchUser(username) {
    //3. set the elements such that a suitable message is displayed

    document.querySelector('h2').innerHTML = username +'  ' +'NOT FOUND';

    document.getElementById('ID').innerHTML = '';
    document.getElementById('avatar').innerHTML = '';
    document.getElementById('information').innerHTML = '';
}

$(document).ready(function(){
    $(document).on('keypress', '#username', function(e){
        //check if the enter(i.e return) key is pressed

        if (e.which == 13) {
            //get what the user enters
            var username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the respsonse
            response = getGithubInfo(username);

            //if the response is successful show the user's details
            if (response.status == 200) {
                var text = JSON.parse(response.responseText);
                if(text['total_count']==0){
                    noSuchUser(username);
                }
                else{
                showUser(JSON.parse(response.responseText));
                }
                //else display suitable message
            } else {
                noSuchUser(username);
            }
        }
    })
});
