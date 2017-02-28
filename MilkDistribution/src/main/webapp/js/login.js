/* Login authentication handler */

$(document).on( "pagecontainerbeforeshow", function( event, ui ) {

       var toPageID = ui.toPage.prop("id");

       if(toPageID == "login") {  //if Login screen
               
         
       }

});
var productCollection = [];
var user_nextId = 0;
var edit_nextId = 0;

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
         $('#forgot_email').val('');
        $("#popupFpwd").popup('open',{'transition':'flip'});

    });
    
    $( "#signupBtn" ).click(function(e) {

        e.preventDefault();
        $.mobile.changePage($("#signup"));
       //$("#popupSignup").popup('open',{'transition':'flip'});

   });
    $('#signup_userId').blur(function () {
    	var signup_userId = $('#signup_userId').val();
    	if (signup_userId != '') {
	    	var userObj = {};
	    	userObj.userId = signup_userId;
	    	var reqObj = {};
	    	reqObj.user = userObj;
	    	var jsonRequest = prepareRequestData (reqObj, 'userIdExists');
	    	loadingSpinner.show();
	        $.ajax({
	       	 type: 'POST',
	            url: base_URL + "/userIdExists",
	            data: jsonRequest,
	            dataType: 'json',
	            contentType: 'application/json; charset=utf-8',
	            success: userIdExistsSuccess,
	            error: userIdExistsFail
	
	        });
    	}
    });
    $('#signup_mobile').blur(function () {
    	var signup_mobile = $('#signup_mobile').val();
    	if (signup_mobile != '') {
	    	var userObj = {};
	    	userObj.mobile = signup_mobile;
	    	var reqObj = {};
	    	reqObj.user = userObj;
	    	var jsonRequest = prepareRequestData (reqObj, 'mobileExists');
	    	loadingSpinner.show();
	        $.ajax({
	       	 type: 'POST',
	            url: base_URL + "/mobileExists",
	            data: jsonRequest,
	            dataType: 'json',
	            contentType: 'application/json; charset=utf-8',
	            success: mobileExistsSuccess,
	            error: mobileExistsFail
	
	        });
    	}
    });
    $( "#forgot_submit" ).click(function(e) {


        e.preventDefault();

        var forgot_email = $('#forgot_email').val();

        if(forgot_email) {
        	var userObj = {};
        	userObj.mailId = forgot_email;
        	var reqObj = {};
        	reqObj.user = userObj;
        	var jsonRequest = prepareRequestData (reqObj, 'forgotUserDetails');
        	loadingSpinner.show();
            $.ajax({
           	 type: 'POST',
                url: base_URL + "/forgotUserDetails",
                data: jsonRequest,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: forgotPasswordSuccess,
                error: forgotPasswordFail

            });



        } else {

        swal("Forgot Password Error", "Please enter Email Address");

        }


    });

    
    
    $('#signup_submit').click(function() {
    	signup();
    });
    $('#edit_submit').click(function() {
    	editUser();
    });
});
$(document).on("pageshow","#login",function(){
	storage.set('USER', '');
	$('#login').find("input[type=text], input[type=password]").val("");
});

$(document).on("pageshow","#signup",function(){
	$('#signup').find("input[type=text], input[type=password], textarea").val("");
	user_nextId = 0;
	$('#setUserProduct').empty();
	$('#addUserProduct').off('click');
	$("#addUserProduct").click(function() {
    	user_nextId++;
    	var content = "<div data-role='collapsible' id='setUserProduct" + user_nextId + "'><h4>Product</h4>";
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
        $("#userRemoveProduct"+user_nextId).off('click');
        $("#userRemoveProduct"+user_nextId).click (function (event) {
        	var str = event.target.id.toString().substring(17);
        	$("#setUserProduct"+str).hide();
        });
    });
});

$(document).on("pageshow","#edit-user",function(){
	$('#edit-user').find("input[type=text], input[type=password], textarea").val("");
	edit_nextId = 0;
	$('#setEditProduct').empty();
	$('#addEditProduct').off('click');
	$("#addEditProduct").click(function() {
		edit_nextId++;
    	var content = "<div data-role='collapsible' id='setEditProduct" + edit_nextId + "'><h4>Product</h4>";
        content += "<div class='ui-field-contain remove-margin'>";
        content += "<select name='editProductType"+edit_nextId+"' id='editProductType"+edit_nextId+"'>";
        content += "<option value=''></option>";
        for (var j=0; j<productCollection.length;j++) {
        	content += "<option value='"+productCollection[j].id+"'>"+productCollection[j].description+"</option>";
        }
        content += "</div>";
        content += "<input type='number' name='editProductQty"+edit_nextId+"' id='editProductQty"+edit_nextId+"' min='1' placeholder='Qty'>";
        content += "<a href='#' id='editRemoveProduct"+edit_nextId+"' class='ui-btn ui-btn-raised clr-primary'>Remove</a>";
        content += "</div>";
        $( "#setEditProduct" ).append( content ).collapsibleset( "refresh" );
        $( "#setEditProduct" ).trigger('create');
        $("#editRemoveProduct"+user_nextId).off('click');
        $("#editRemoveProduct"+user_nextId).click (function (event) {
        	var str = event.target.id.toString().substring(17);
        	$("#setEditProduct"+str).hide();
        });
    });
	var userObj = storage.get('USER');
	$('#edit_firstName').val(userObj.firstName);
	$('#edit_lastName').val(userObj.lastName);
	$('#edit_userId').val(userObj.userId);
    $('#edit_mobile').val(userObj.mobile);
    $('#edit_email').val(userObj.mailId);
    $('#edit_address').val(userObj.address);
	var requirement = userObj.requirement;
	if (requirement != null && requirement.length > 0) {
    	var roasterDetails = userObj.requirement;
    	for (var t=0;t<roasterDetails.length;t++) {
    		$( "#addEditProduct" ).trigger('click');
    		var l = parseInt(t)+1;
    		$("#editProductQty"+l).val(roasterDetails[t].qty);
    		$("#editProductType"+l).val(roasterDetails[t].product.id).selectmenu('refresh');
    	}
    	$('#setEditProduct').show();
    }
});

function mobileExistsSuccess(data) {
	loadingSpinner.hide();
	if (data.result.errorCode == "failure") {
    	
    } else if (data.body == "null") {
    	
    } else {
    	var flag = data.body;
    	if (flag == true) {
    		swal("Error", "User already Exists with the same mobile.");
    		$('#signup_mobile').val('');
    	}
    }
}
function mobileExistsFail(data) {
	loadingSpinner.hide();
}
function userIdExistsSuccess(data) {
	loadingSpinner.hide();
	if (data.result.errorCode == "failure") {
    	
    } else if (data.body == "null") {
    	
    } else {
    	var flag = data.body;
    	if (flag == true) {
    		swal("Error", "User Id already Exists");
    		$('#signup_userId').val('');
    	}
    }
}
function userIdExistsFail(data) {
	loadingSpinner.hide();
}
function forgotPasswordSuccess(data) {
    loadingSpinner.hide();
    if (data.result.errorCode == "failure") {
    	swal("Error", "There was an error in communicating with the server.");
    	return;
    } else if (data.body == "null") {
    	swal("Forgot Password Error", "Please enter valid Email Address");
    	return;
    }

    swal({
         title: "Success",
         text: "Please check your email for the password",
         closeOnConfirm: true,
         },
         function(){
            $("#popupFpwd").popup('close');
         });

}

function forgotPasswordFail(data) {
    loadingSpinner.hide();
    swal("Error", "There was an error in communicating with the server.");

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
	   if (user.status == 'I') {
		   swal("Pending For Verification", "User is under Pending for verification");
		   return;
	   }
	   if (user.role == "A") {
		   storage.set('USER_ROLE', "A");
		   $.mobile.changePage($("#view-admin-menu"));
	   } else {
		  storage.set('USER', user);
		  storage.set('USER_ROLE', "N");
		  $.mobile.changePage($("#view-user-menu"));
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
	var firstName = $('#signup_firstName').val();
	var lastName = $('#signup_lastName').val();
	var pwd = $('#signup_password').val();
    var re_pwd = $('#signup_repassword').val();
    var mobile = $('#signup_mobile').val();
    var email = $('#signup_email').val();
    var address = $('#signup_address').val();
    if (firstName == '') {
    	signUpValidationError("Please enter First Name");
    	return false;
    }
    if (lastName == '') {
    	signUpValidationError("Please enter Last Name");
    	return false;
    }
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
    var prodTypeArr = [];
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
        	if (containsElement(prodTypeArr, prodType)) {
        		signUpValidationError("Selected Product Types should not be duplicate");
	       		return false;
	       	} else {
	       		 prodTypeArr.push(prodType);
	       	}
    	}
    	
    }
    return true;
}
function prepareUserData() {
	var firstName = $('#signup_firstName').val();
	var lastName = $('#signup_lastName').val();
	var userId = $('#signup_userId').val();
	var pwd = $('#signup_password').val();
    var mobile = $('#signup_mobile').val();
    var email = $('#signup_email').val();
    var address = $('#signup_address').val();
    var userObj = {};
    userObj.userId = userId;
    userObj.firstName = firstName;
    userObj.lastName = lastName;
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
	   $.mobile.changePage($("#login"));
   }
}

function signupFail(data) {
     loadingSpinner.hide();
     swal("Network Error", "There was an error in communicating with the server.");

}
function validateEditUserDetails() {
	var firstName = $('#edit_firstName').val();
	var lastName = $('#edit_lastName').val();
	
    var mobile = $('#edit_mobile').val();
    var email = $('#edit_email').val();
    var address = $('#edit_address').val();
    var prodTypeArr = [];
    if (firstName == '') {
    	editValidationError("Please enter First Name");
    	return false;
    }
    if (lastName == '') {
    	editValidationError("Please enter Last Name");
    	return false;
    }
    
    
    if (mobile == '') {
    	editValidationError("Please enter Mobile Number");
    	return false;
    }
    var mobileFilter = /^[0-9-+]+$/;
    if (!mobileFilter.test(mobile)) {
    	editValidationError("Please enter valid Mobile Number");
    	return false;
    }
    if (email == '') {
    	editValidationError("Please enter Email Address");
    	return false;
    }
    var emailPattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
    if(!emailPattern.test(email)) {
    	editValidationError("Please enter valid Email Address");
    	return false;
    }
    if (address == '') {
    	editValidationError("Please enter Address");
    	return false;
    }
    if (edit_nextId == 0) {
    	editValidationError("Please enter required product details");
    	return false;
    }
   
    for (var k=1;k<=edit_nextId;k++) {
    	if ($("#setEditProduct"+k).is(":visible")) {
    		var prodType = $('#editProductType'+k).val();
        	var prodQty = $('#editProductQty'+k).val();
        	if (prodType == '') {
        		editValidationError("Please select Product Type");
            	return false;
        	}
        	if (prodQty == '') {
        		editValidationError("Please enter Product Qty");
            	return false;
        	}
        	if (parseInt(prodQty) < 1) {
        		editValidationError("Please enter Product Qty as more than 1");
            	return false;
        	}
        	if (containsElement(prodTypeArr, prodType)) {
        		 editValidationError("Selected Product Types should not be duplicate");
	       		 return false;
	       	} else {
	       		 prodTypeArr.push(prodType);
	       	}
    	}
    	
    }
    return true;
}
function prepareEditUserData() {
	var firstName = $('#edit_firstName').val();
	var lastName = $('#edit_lastName').val();
	var mobile = $('#edit_mobile').val();
    var email = $('#edit_email').val();
    var address = $('#edit_address').val();
    var userObj = storage.get('USER');
    userObj.firstName = firstName;
    userObj.lastName = lastName;
    userObj.mailId = email;
    userObj.mobile = mobile;
    userObj.address = address;
    userObj.status = "I";
    userObj.role = "N";
    var requirement = [];
    for (var k=1;k<=edit_nextId;k++) {
    	if ($("#setEditProduct"+k).is(":visible")) {
    		var prodType = $('#editProductType'+k).val();
        	var prodQty = $('#editProductQty'+k).val();
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
    var jsonRequest = prepareRequestData (requestObj, 'updateUser');
    return jsonRequest;
}
function editValidationError(message) {
	swal("Edit User Error", message);
}
function editUser() {
	var flag = validateEditUserDetails();
	if (!flag) {
		return;
	}
	loadingSpinner.show();
    
    
		
	var jsonRequest = prepareEditUserData();
	$.ajax({
          type: 'POST',
          url: base_URL + "/updateUser",
          data: jsonRequest,
          dataType: 'json',
        contentType: 'application/json; charset=utf-8',
          success: editSuccess,
          error: editFail

          });
}
function editSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   swal("Error", "Problem communicationg with server.");
   } else {
	   swal("Success", "You have Successfully Modified User Details");
	   $.mobile.changePage($("#login"));
   }
}

function editFail(data) {
     loadingSpinner.hide();
     swal("Network Error", "There was an error in communicating with the server.");

}