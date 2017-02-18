var areaCollection = [];
var activeUsers = [];
var pendingUsers = [];
var activate_nextId = 0;
$(document).on("pageshow","#view-admin-menu",function(){
	loadAreas();
	$('#adminback').off('click');
	$('#adminback').click(function () {
		$.mobile.changePage($("#login"));
	});
});
$(document).on("pageshow","#view-users",function(){
	$('#viewusersback').off('click');
	$('#viewusersback').click(function () {
		$.mobile.changePage($("#view-admin-menu"));
	});
});
$(document).on("pageshow","#view-active-users",function(){
	$('#userAreaList').empty();
	var content = "";
	for (var t=0;t<areaCollection.length;t++) {
		content = content + "<li><a href='#' id='userArea"+t+"'>"+areaCollection[t].description+"</a></li>";
		
	}
	$('#userAreaList').append(content).listview( "refresh" );
	for (t=0;t<areaCollection.length;t++) {
		$('#userArea'+t).off("click");
		$('#userArea'+t).click(function (event) {
			var ind = parseInt(event.target.id.toString().substring(8));
			storage.set("USER_AREA", areaCollection[ind]);
			$.mobile.changePage($("#view-users-by-area"));
		});
	}
	$('#viewactiveusersback').off('click');
	$('#viewactiveusersback').click(function () {
		$.mobile.changePage($("#view-users"));
	});
});
$(document).on("pageshow","#view-users-by-area",function(){
	loadActiveUsers();
	$('#viewusersbyareaback').off('click');
	$('#viewusersbyareaback').click(function () {
		$.mobile.changePage($("#view-active-users"));
	});
});
function loadActiveUsers() {
	var reqObj = {};
	var areaObj = storage.get("USER_AREA");
	reqObj.areaId = areaObj.id;
	var jsonRequest = prepareRequestData (reqObj, 'activeUsers');
	loadingSpinner.show();
    $.ajax({
   	 type: 'POST',
        url: base_URL + "/activeUsers",
        data: jsonRequest,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: loadActiveUsersSuccess,
        error: loadActiveUsersFail

    });
}
function loadActiveUsersFail(data) {
    loadingSpinner.hide();
    activeUsers = [];
    prepareActiveUsers();
}



function loadActiveUsersSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   activeUsers = [];
   } else if (data.body == "null") {
	   activeUsers = [];
   } else {
	   activeUsers = data.body;
   }
   prepareActiveUsers();
}
function prepareActiveUsers() {
	$('#userList').empty();
	var content = "";
	for (var t=0;t<activeUsers.length;t++) {
		content = content + "<li><a href='#' id='activeUser"+t+"'>"+activeUsers[t].userId+"</a></li>";
	}
	$('#userList').append(content).listview( "refresh" );
	for (t=0;t<activeUsers.length;t++) {
		$('#activeUser'+t).off("click");
		$('#activeUser'+t).click(function (event) {
			var ind = parseInt(event.target.id.toString().substring(10));
			storage.set("USER", activeUsers[ind]);
			$.mobile.changePage($("#view-calendar"));
		});
	}
}
function loadAreas() {
	var jsonRequest = prepareRequestData ({}, 'getAreas');

     //show loading view
     loadingSpinner.show();
     $.ajax({
    	 type: 'POST',
         url: base_URL + "/getAreas",
         data: jsonRequest,
         dataType: 'json',
         contentType: 'application/json; charset=utf-8',
         success: loadAreasSuccess,
         error: loadAreasFail

     });
}
function loadAreasFail(data) {
    loadingSpinner.hide();
    areaCollection = [];

}



function loadAreasSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   areaCollection = [];
   } else if (data.body == "null") {
	   areaCollection = [];
   } else {
	   areaCollection = data.body;
   }
}
$(document).on("pageshow","#view-pending-users",function(){
	loadPendingUsers();
	
	$('#viewpendingusersback').off('click');
	$('#viewpendingusersback').click(function () {
		$.mobile.changePage($("#view-users"));
	});
	
});
function loadPendingUsers() {
	var jsonRequest = prepareRequestData ({}, 'pendingUsers');
	loadingSpinner.show();
    $.ajax({
   	 type: 'POST',
        url: base_URL + "/pendingUsers",
        data: jsonRequest,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: loadPendingUsersSuccess,
        error: loadPendingUsersFail

    });
}
function loadPendingUsersFail(data) {
	loadingSpinner.hide();
    pendingUsers = [];
    preparePendingUsers();
}



function loadPendingUsersSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   pendingUsers = [];
   } else if (data.body == "null") {
	   pendingUsers = [];
   } else {
	   pendingUsers = data.body;
   }
   preparePendingUsers();
}
function preparePendingUsers() {
	$('#pendingUsersList').empty();
	var content = "";
	for (var t=0;t<pendingUsers.length;t++) {
		content = content + "<li><a href='#' id='pendingUser"+t+"'>"+pendingUsers[t].userId+"</a></li>";
	}
	$('#pendingUsersList').append(content).listview( "refresh" );
	for (t=0;t<pendingUsers.length;t++) {
		$('#pendingUser'+t).off("click");
		$('#pendingUser'+t).click(function (event) {
			var ind = parseInt(event.target.id.toString().substring(11));
			storage.set("PENDING_USER", pendingUsers[ind]);
			$.mobile.changePage($("#activate-user"));
		});
	}
}
$(document).on("pageshow","#activate-user",function(){
	$('#activate-user').find("input[type=text], input[type=password], textarea").val("");
	activate_nextId = 0;
	var userObj = storage.get("PENDING_USER");
	$('#setActivateProduct').empty();
	$('#addActivateProduct').off('click');
	$("#addActivateProduct").click(function() {
		activate_nextId++;
    	var content = "<div data-role='collapsible' id='setActivateProduct" + activate_nextId + "'><h4>Product</h4>";
        content += "<div class='ui-field-contain remove-margin'>";
        content += "<select name='activateProductType"+activate_nextId+"' id='activateProductType"+activate_nextId+"'>";
        content += "<option value=''></option>";
        for (var j=0; j<productCollection.length;j++) {
        	content += "<option value='"+productCollection[j].id+"'>"+productCollection[j].description+"</option>";
        }
        content += "</div>";
        content += "<input type='number' name='activateProductQty"+activate_nextId+"' id='activateProductQty"+activate_nextId+"' min='1' placeholder='Qty'>";
        content += "<a href='#' id='activateRemoveProduct"+activate_nextId+"' class='ui-btn ui-btn-raised clr-primary'>Remove</a>";
        content += "</div>";
        $( "#setActivateProduct" ).append( content ).collapsibleset( "refresh" );
        $( "#setActivateProduct" ).trigger('create');
        $("#activateRemoveProduct"+activate_nextId).off('click');
        $("#activateRemoveProduct"+activate_nextId).click (function (event) {
        	var str = event.target.id.toString().substring(17);
        	$("#setActivateProduct"+str).hide();
        });
    });
	$('#activate_area').empty();
	var areaContent = "<option value=''></option>";
	for (var j=0; j<areaCollection.length;j++) {
		areaContent += "<option value='"+areaCollection[j].id+"'>"+areaCollection[j].description+"</option>";
    }
	$("#activate_area").append(areaContent).selectmenu( "refresh" );
	$('#activate_userId').val(userObj.userId);	
	$('#activate_mobile').val(userObj.mobile);	
	$('#activate_email').val(userObj.mailId);	
	$('#activate_address').val(userObj.address);	
	$("#activate_area").val((userObj.area != null ? userObj.area.id : ""));
	var roasterDetails = userObj.requirement;
	for (var t=0;t<roasterDetails.length;t++) {
		$( "#addActivateProduct" ).trigger('click');
		var l = parseInt(t)+1;
		$("#activateProductQty"+l).val(roasterDetails[t].qty);
		$("#activateProductType"+l).val(roasterDetails[t].product.id).selectmenu('refresh');
	}
	$('#activate_submit').off('click');
	$('#activate_submit').click(function() {
		activateUser();
    });
	$('#activateuserback').off('click');
	$('#activateuserback').click(function () {
		$.mobile.changePage($("#view-pending-users"));
	});
});
function validateActivateUserDetails() {
	var userId = $('#activate_userId').val();
    var mobile = $('#activate_mobile').val();
    var email = $('#activate_email').val();
    var address = $('#activate_address').val();
    var area = $('#activate_area').val();
    if (userId == '') {
    	signUpValidationError("Please enter User Id");
    	return false;
    }
    
    if (mobile == '') {
    	activateUpValidationError("Please enter Mobile Number");
    	return false;
    }
    var mobileFilter = /^[0-9-+]+$/;
    if (!mobileFilter.test(mobile)) {
    	activateValidationError("Please enter valid Mobile Number");
    	return false;
    }
    if (email == '') {
    	activateValidationError("Please enter Email Address");
    	return false;
    }
    var emailPattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
    if(!emailPattern.test(email)) {
    	activateValidationError("Please enter valid Email Address");
    	return false;
    }
    if (address == '') {
    	activateValidationError("Please enter Address");
    	return false;
    }
    if (area == '') {
    	activateValidationError("Please Select Area");
    	return false;
    }
    if (activate_nextId == 0) {
    	activateValidationError("Please enter required product details");
    	return false;
    }
   
    for (var k=1;k<=activate_nextId;k++) {
    	if ($("#setActivateProduct"+k).is(":visible")) {
    		var prodType = $('#activateProductType'+k).val();
        	var prodQty = $('#activateProductQty'+k).val();
        	if (prodType == '') {
        		activateValidationError("Please select Product Type");
            	return false;
        	}
        	if (prodQty == '') {
        		activateValidationError("Please enter Product Qty");
            	return false;
        	}
        	if (parseInt(prodQty) < 1) {
        		activateValidationError("Please enter Product Qty as more than 1");
            	return false;
        	}
    	}
    	
    }
    return true;
}
function prepareActivateUserData() {
	var mobile = $('#activate_mobile').val();
    var email = $('#activate_email').val();
    var address = $('#activate_address').val();
    var area = $('#activate_area').val();
    var userObj = storage.get("PENDING_USER");
    var areaObj = {};
    areaObj.id = area;
    userObj.mailId = email;
    userObj.mobile = mobile;
    userObj.address = address;
    userObj.status = "A";
    userObj.role = "N";
    userObj.area = areaObj;
    var requirement = [];
    for (var k=1;k<=activate_nextId;k++) {
    	if ($("#setActivateProduct"+k).is(":visible")) {
    		var prodType = $('#activateProductType'+k).val();
        	var prodQty = $('#activateProductQty'+k).val();
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
    var jsonRequest = prepareRequestData (requestObj, 'verifyUser');
    return jsonRequest;
}
function activateValidationError(message) {
	swal("User Activation Error", message);
}
function activateUser() {
	var flag = validateActivateUserDetails();
	if (!flag) {
		return;
	}
	loadingSpinner.show();
    
    
		
	var jsonRequest = prepareActivateUserData();
	$.ajax({
          type: 'POST',
          url: base_URL + "/verifyUser",
          data: jsonRequest,
          dataType: 'json',
        contentType: 'application/json; charset=utf-8',
          success: activateUserSuccess,
          error: activateUserFail

          });
}
function activateUserSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   swal("Error", "Problem communicationg with server.");
   } else {
	   swal("Success", "User Activated successfully");
	   $.mobile.changePage($("#view-pending-users"));
   }
}

function activateUserFail(data) {
     loadingSpinner.hide();
     swal("Network Error", "There was an error in communicating with the server.");

}
