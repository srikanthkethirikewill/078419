/* Login authentication handler */

$(document).on( "pagecontainerbeforeshow", function( event, ui ) {

       var toPageID = ui.toPage.prop("id");

       if(toPageID == "login") {  //if Login screen
               
         
       }

});
var productCollection = [];
var user_nextId = 0;

$(document).ready(function() {

loadProducts();	

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
        window.location = "#signup";
       //$("#popupSignup").popup('open',{'transition':'flip'});

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

    
    
    $('#signup_submit').click(function() {
    	signup();
    });
});

$(document).on("pageshow","#signup",function(){
	user_nextId = 0;
	$('#setUserProduct').empty();
	$('#signup').trigger('refresh');
	$("#addUserProduct").click(function() {
    	user_nextId++;
        var content = "<div data-role='collapsible' id='setUserProduct" + user_nextId + "'><h4>Product</h3>";
        content += "<div class='ui-field-contain remove-margin'>";
        content += "<select name='userProductType"+user_nextId+"' id='userProductType"+user_nextId+"'>";
        content += "<option value=''></option>";
        for (var j=0; j<productCollection.length;j++) {
        	content += "<option value='"+productCollection[j].id+"'>"+productCollection[j].description+"</option>";
        }
        content += "</div>";
        content += "<input type='number' name='userProductQty"+user_nextId+"' id='userProductQty"+user_nextId+"' min='1' placeholder='Qty'>";
        content += "<a href='#' id='userRemoveProduct"+user_nextId+"' class='ui-btn ui-btn-raised clr-primary'>Remove</a>";
        content += "</div>";
        $( "#setUserProduct" ).append( content ).collapsibleset( "refresh" );
        $( "#setUserProduct" ).trigger('create');
        $("#userRemoveProduct"+user_nextId).click (function (event) {
        	var str = event.target.id.toString().substring(17);
        	$("#setUserProduct"+str).hide();
        });
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
   } else if (data.body == "null" || data.body == null) {
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

function loadProducts() {
	var jsonRequest = prepareRequestData ({}, 'getProducts');

     //show loading view
     loadingSpinner.show();
     
     
  		

  		$.ajax({
                  type: 'POST',
                  url: base_URL + "/getProducts",
                  data: jsonRequest,
                  dataType: 'json',
      	        contentType: 'application/json; charset=utf-8',
                  success: loadProductsSuccess,
                  error: loadProductsFail

                  });
}
function loadProductsFail(data) {
    loadingSpinner.hide();
    productCollection = [];

}



function loadProductsSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   productCollection = [];
   } else if (data.body == "null") {
	   productCollection = [];
   } else {
	   productCollection = data.body;
   }
}
function validateUserDetails() {
	var userId = $('#signup_userId').val();
	var pwd = $('#signup_password').val();
    var re_pwd = $('#signup_repassword').val();
    var mobile = $('#signup_mobile').val();
    var email = $('#signup_email').val();
    var address = $('#signup_address').val();
    if (userId == '') {
    	signUpValidationError("Please enter User Id");
    	return false;
    }
    if (pwd == '') {
    	signUpValidationError("Please enter Password");
    	return false;
    }
    if (re_pwd == '') {
    	signUpValidationError("Please reenter Password");
    	return false;
    }
    if (pwd != re_pwd) {
    	signUpValidationError("Password and Reenter Password should be same");
    	return false;
    }
    if (mobile == '') {
    	signUpValidationError("Please enter Mobile Number");
    	return false;
    }
    var mobileFilter = /^[0-9-+]+$/;
    if (!mobileFilter.test(mobile)) {
    	signUpValidationError("Please enter valid Mobile Number");
    	return false;
    }
    if (email == '') {
    	signUpValidationError("Please enter Email Address");
    	return false;
    }
    var emailPattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
    if(!emailPattern.test(email)) {
    	signUpValidationError("Please enter valid Email Address");
    	return false;
    }
    if (address == '') {
    	signUpValidationError("Please enter Address");
    	return false;
    }
    if (user_nextId == 0) {
    	signUpValidationError("Please enter required product details");
    	return false;
    }
   
    for (var k=1;k<=user_nextId;k++) {
    	if ($("#setUserProduct"+k).is(":visible")) {
    		var prodType = $('#userProductType'+k).val();
        	var prodQty = $('#userProductQty'+k).val();
        	if (prodType == '') {
        		signUpValidationError("Please select Product Type");
            	return false;
        	}
        	if (prodQty == '') {
        		signUpValidationError("Please enter Product Qty");
            	return false;
        	}
        	if (parseInt(prodQty) < 1) {
        		signUpValidationError("Please enter Product Qty as more than 1");
            	return false;
        	}
    	}
    	
    }
    return true;
}
function prepareUserData() {
	var userId = $('#signup_userId').val();
	var pwd = $('#signup_password').val();
    var mobile = $('#signup_mobile').val();
    var email = $('#signup_email').val();
    var address = $('#signup_address').val();
    var userObj = {};
    userObj.userId = userId;
    userObj.password = pwd;
    userObj.mailId = email;
    userObj.mobile = mobile;
    userObj.address = address;
    userObj.status = "I";
    userObj.role = "N";
    var requirement = [];
    for (var k=1;k<=user_nextId;k++) {
    	if ($("#setUserProduct"+k).is(":visible")) {
    		var prodType = $('#userProductType'+k).val();
        	var prodQty = $('#userProductQty'+k).val();
        	var req = {};
        	var prod = {}; 
        	prod.id = prodType;
        	req.product = prod;
        	req.qty = prodQty;
        	requirement.push(req);
    	}
    	
    }
    userObj.requirement = requirement;
    var requestObj = {};
    requestObj.user = userObj;
    var jsonRequest = prepareRequestData (requestObj, 'createUser');
    return jsonRequest;
}
function signUpValidationError(message) {
	swal("Sign Up Error", message);
}
function signup() {
	var flag = validateUserDetails();
	if (!flag) {
		return;
	}
	loadingSpinner.show();
    
    
		
	var jsonRequest = prepareUserData();
	$.ajax({
          type: 'POST',
          url: base_URL + "/createUser",
          data: jsonRequest,
          dataType: 'json',
        contentType: 'application/json; charset=utf-8',
          success: signupSuccess,
          error: signupFail

          });
}
function signupSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   swal("Error", "Problem communicationg with server.");
   } else {
	   swal("Success", "You have registered successfully");
	   window.location = "#login";
   }
}

function signupFail(data) {
     loadingSpinner.hide();
     swal("Network Error", "There was an error in communicating with the server.");

}
