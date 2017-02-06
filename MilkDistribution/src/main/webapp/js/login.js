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
          

    	   var userData = {};
    	   userData.userId = email;
    	   userData.password = pwd;
    	   var user = {};
    	   user.user = userData;
    	   var jsonRequest = prepareRequestData (user, 'validateUser');

           //show loading view
           loadingSpinner.show();
           
           
        		

        		$.ajax({
                        type: 'POST',
                        url: base_URL + "/validateUser",
                        data: jsonRequest,
                        dataType: 'json',
            	        contentType: 'application/json; charset=utf-8',
                        success: authenticattionSuccess,
                        error: authenticattionFail

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
    
    $( "#signup" ).click(function(e) {

        e.preventDefault();
       $("#popupSignup").popup('open',{'transition':'flip'});

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
   if (data.result.errorCode == "failure") {
	   swal("Error", "Problem communicationg with server.");
   } else if (data.body == "null") {
	   swal("Error", "Invalid User Id Or Password");
   } else {
	   var user = data.body;
	   storage.set('USER', user);
	   if (user.role == "A") {
		   window.location("");
	   } else {
		   window.location("");
	   }
   }
}

function authenticattionFail(data) {
     loadingSpinner.hide();
     swal("Network Error", "There was an error in communicating with the server.");

}
