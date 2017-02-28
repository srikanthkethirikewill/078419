var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
  "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
];
var billList = [];
$(document).on("pageshow","#view-billing",function(){
	loadCurrentMonthBilling();
	$('#viewbillingback').off('click');
	$('#viewbillingback').click(function () {
		$.mobile.changePage($("#view-user-menu"));
	});
	if (storage.get('USER_ROLE') == 'N') {
		$('.billing-admin').hide();
		$('#billing_receivedAmount').attr('readonly', true);
	} else {
		$('.billing-admin').show();
		$('#billing_receivedAmount').attr('readonly', false);
	}
	$('#billing_submit').off('click');
	$('#billing_submit').click(function () {
		saveBilling();
	});
});
$(document).on("pageshow","#view-previous-bills",function(){
	billList = [];
	loadPreviousBillings();
	$('#viewpreviousbillsback').off('click');
	$('#viewpreviousbillsback').click(function () {
		$.mobile.changePage($("#view-user-menu"));
	});	
});
function loadPreviousBillings() {
	var reqObj = {};
	var userObj = storage.get("USER");
	reqObj.user = userObj;
	var jsonRequest = prepareRequestData (reqObj, 'getBillingList');
	loadingSpinner.show();
    $.ajax({
   	 type: 'POST',
        url: base_URL + "/getBillingList",
        data: jsonRequest,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: billListSuccess,
        error: billListFail

    });
}
function billListFail() {
	loadingSpinner.hide();
    billList = [];
    prepareBillList();
}
function billListSuccess(data) {
	loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   billList = [];
   } else if (data.body == "null") {
	   billList = [];
   } else {
	   billList = data.body;
   }
   prepareBillList();
}
function prepareBillList() {
	$('#billList').empty();
	var content = "";
	for (var t=0;t<billList.length;t++) {
		content = content + "<li><h3> Bill Amount: "+billList[t].billAmount+"</h3><p> Previous Due: "+billList[t].billAmount+"</p><p> Total Amount: "+billList[t].totalAmount+"</p><p> Received Amount: "+billList[t].receivedAmount+"</p></li>";
	}
	$('#billList').append(content).listview( "refresh" );
	
}
function loadCurrentMonthBilling() {
	var reqObj = {};
	var userObj = storage.get("USER");
	var dateObj = new Date();
	var month = monthNames[dateObj.getMonth()];
	var year = dateObj.getFullYear();
	reqObj.month = month;
	reqObj.year = year;
	reqObj.user = userObj;
	var jsonRequest = prepareRequestData (reqObj, 'getCurrentMonthBilling');
	loadingSpinner.show();
    $.ajax({
   	 type: 'POST',
        url: base_URL + "/getCurrentMonthBilling",
        data: jsonRequest,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: loadCurrentMonthBillingSuccess,
        error: loadCurrentMonthBillingFail

    });
}
function loadCurrentMonthBillingSuccess(data) {
    loadingSpinner.hide();
    if (data.result.errorCode == "failure") {
    	swal("Error", "There was an error in communicating with the server.");
    	$.mobile.changePage($("#view-user-menu"));
    	return;
    } else if (data.body == "null") {
    	swal("Error", "There is no bill generated for the current month");
    	$.mobile.changePage($("#view-user-menu"));
    	return;
    } else {
    	var billingObj = data.body;
    	$('#billing_billAmount').val(billingObj.billAmount);
    	$('#billing_previousDue').val(billingObj.previousDue);
    	$('#billing_totalAmount').val(billingObj.totalAmount);
    	$('#billing_receivedAmount').val(billingObj.receivedAmount);
    	storage.set('BILLING', billingObj);
    }

}

function loadCurrentMonthBillingFail(data) {
    loadingSpinner.hide();
    swal("Error", "There was an error in communicating with the server.");
    $.mobile.changePage($("#view-user-menu"));
}
function saveBilling() {
	var billingObj = storage.get('BILLING');
	var receivedAmount = $('#billing_receivedAmount').val();
	if (receivedAmount == '' || receivedAmount == '0') {
		swal("Billing Error", "Please Enter Received Amount");
		return;
	}
	billingObj.receivedAmount = receivedAmount;
	var jsonRequest = prepareRequestData (billingObj, 'updateBilling');
	loadingSpinner.show();
    $.ajax({
   	 type: 'POST',
        url: base_URL + "/updateBilling",
        data: jsonRequest,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: saveBillingSuccess,
        error: saveBillingFail

    });
}
function saveBillingSuccess(data) {
	loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   swal("Error", "Problem communicationg with server.");
   } else {
	   swal("Success", "You have Successfully Updated Received Amount");
	   $.mobile.changePage($("#view-user-menu"));
   }
}
function saveBillingFail() {
	loadingSpinner.hide();
    swal("Network Error", "There was an error in communicating with the server.");
}