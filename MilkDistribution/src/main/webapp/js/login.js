/* Login authentication handler */

$(document).on( "pagecontainerbeforeshow", function( event, ui ) {

       var toPageID = ui.toPage.prop("id");

       if(toPageID == "login") {  //if Login screen
               
         
       }

});


$(document).ready(function() {

$( "#signin" ).click(function(e) {

       e.preventDefault();

       var email = $('#txt_email').val();
       var pwd = $('#txt_pwd').val();

       if(email && pwd) {

           //encrypt basic userinfo
           var encUserInfo = window.btoa(email + ":" + pwd);



           //show loading view
           loadingSpinner.show();

           $.bbObj.ajax({
                        type: 'POST',
                        url: authenticate_API_URL,
                        data: {},
                        success: authenticattionSuccess,
                        error: authenticattionFail,
                        userinfo: encUserInfo

                        });

       } else {

            swal("Sign In Error", "Please enter username/password");

       }

       });




   //forgot pwd


    $( "#forgotpwd" ).click(function(e) {

         e.preventDefault();
        $("#popupFpwd").popup('open',{'transition':'flip'});

    });

    $( "#forgot_submit" ).click(function(e) {


        e.preventDefault();

        var forgot_email = $('#forgot_email').val();

        if(forgot_email) {

            var ajaxURL = 'https://app.bizbee.io/noauth/signup/user/forgotPassword?email=' + forgot_email;

            //console.log("forgotpwd url:" + ajaxURL);

            //show loading view
            loadingSpinner.show();

            $.bbObj.ajax({
                         type: 'POST',
                         url: ajaxURL,
                         data: {},
                         success: fpwdSuccess,
                         error: fpwdFail
            });



        } else {

        swal("Sign In Error", "Please enter username/password");

        }


    });


});

function fpwdSuccess(data) {
    loadingSpinner.hide();


    swal({
         title: "Success",
         text: "Please check your email for the password reset link.",
         closeOnConfirm: true,
         },
         function(){
            $("#popupFpwd").popup('close');
         });

}

function fpwdFail(data) {
    loadingSpinner.hide();
    swal("Error", "Problem communicationg with server.");

}



function authenticattionSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
    storage.set('USERID', data.user.id);
    storage.set('USERJSON', data.user);
    if(data.user.userType != 'END_USER'){
        window.location = "#dashboard";
    }else {
        storage.set("DBITEM", "My Pending");
        window.location = "#listings";
    }

}

function authenticattionFail(data) {
     loadingSpinner.hide();
    storage.remove('USERINFO');
    alert("failed: " + data);

}
