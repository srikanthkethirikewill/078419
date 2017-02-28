var roasters = [];
var nextId = 0;
var calObj;
$(document).on("pageshow","#view-calendar",function(){
	roasters = [];
	$("#calendar").empty();
	$("#calendar").jqmCalendar({
	      events : [],
	      months : ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
	      days : ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
	      startOfWeek : 0
	   });
	$.mobile.resetActivePageHeight();
	   var height = parseInt($('#view-calendar').height()) || 0;
	   height = parseFloat(height) * 0.8;
	   $('#calendar-table').css('min-height', height);
	   $('#user_back').off('click');
	   $('#user_back').click(function () {
		    $.mobile.changePage($('#view-user-menu'));
		    
		});
});
$(document).on("pageshow","#view-user-menu",function(){
	 $('#viewusermenuback').off('click');
	   $('#viewusermenuback').click(function () {
		    if ( storage.get('USER_ROLE') == 'A') {
				$.mobile.changePage($("#view-users-by-area"));
			} else {
				 $.mobile.changePage($("#login"));
			}
		});
});


 $( document ).on("pageshow","#view-roaster-products", function() {
	 	var dateValDate = storage.get("roaster_date");
	 	var dateValMonth = storage.get("roaster_month");
	 	var dateValYear = storage.get("roaster_year");
	 	var dateValObj = new Date(dateValYear,dateValMonth,dateValDate);
	 	var dateObj = new Date();
	 	var nextMonthDate = new Date();
	 	nextMonthDate.setMonth(dateObj.getMonth()+2);
	 	nextMonthDate.setDate(1);
	 	var roasterDataObj = roasters[dateValDate];
	 	var isReadOnly = (dateValObj <= dateObj) || (dateValObj >= nextMonthDate);
	 	nextId = 0;
	 	$('#set').empty();
	 	$('#add').off('click');
	 	$("#add").click(function() {
	        nextId++;
	        var content = "<div data-role='collapsible' id='set" + nextId + "'><h4>Product</h3>";
	        content += "<div class='ui-field-contain remove-margin'>";
	        content += "<select name='productType"+nextId+"' id='productType"+nextId+"' "+(isReadOnly ? "disabled" : "")+">";
	        content += "<option value=''></option>";
	        for (var j=0; j<productCollection.length;j++) {
	        	content += "<option value='"+productCollection[j].id+"'>"+productCollection[j].description+"</option>";
	        }
	        content += "</div>";
	        content += "<input type='number' name='productQty"+nextId+"' id='productQty"+nextId+"' "+(isReadOnly ? "readOnly" : "")+" placeholder='Qty'>";
	        if (!isReadOnly) {
	        	content += "<a href='#' id='removeProduct"+nextId+"' class='ui-btn ui-btn-raised clr-primary'>Remove</a>";
	        }
	        content += "</div>";
	        $( "#set" ).append( content ).collapsibleset( "refresh" );
	        $( "#set" ).trigger('create');
	        if (!isReadOnly) {
	        	$("#removeProduct"+nextId).off('click');
		        $("#removeProduct"+nextId).click (function (event) {
		        	var str = event.target.id.toString().substring(13);
		        	$("#set"+str).hide();
		        });
	        }
	    });
	    
	    if (isReadOnly) {
	    	$('#fromDateLbl').hide();
	    	$('#toDateLbl').hide();
	    	$('#noProductGrp').hide();
	    	$('#fromDate').parent().hide();
		    $('#toDate').parent().hide();
		    $('#add').hide();
		    $('#roaster_submit').hide();
	    } else {
	    	$('#fromDateLbl').show();
	    	$('#toDateLbl').show();
	    	$('#noProductGrp').show();
	    	$('#fromDate').parent().show();
		    $('#toDate').parent().show();
		    $('#add').show();
		    $('#roaster_submit').show();
		    var fromStr = dateValObj.getFullYear() + "-" + ("0" + (parseInt(dateValObj.getMonth())+1)).slice(-2) + "-" + ("0" + dateValObj.getDate()).slice(-2);
		    nextMonthDate.setDate(nextMonthDate.getDate() -1);
		    var toStr = nextMonthDate.getFullYear() + "-" +("0" +  (parseInt(nextMonthDate.getMonth())+1)).slice(-2) + "-" + ("0" + nextMonthDate.getDate()).slice(-2);
	    	$('#toDate').attr('min', fromStr);
		    $('#toDate').attr('max', toStr);
		    $('#fromDate').val(fromStr);
		    $('#toDate').val(fromStr);
		    $("#noProducts").off('click');
		    $('#noProducts').prop("checked", false).checkboxradio('refresh');
		    $('#noProducts').click(function () {
		    	if($(this).prop("checked") == true){
		    		$('#add').hide();
		    		$('#set').hide();
		    	} else {
		    		$('#add').show();
		    		$('#set').show();
		    	}
		    	$('#set').empty();
		    	nextId = 0;
		    });
		    $("#roaster_submit").off('click');
		    $('#roaster_submit').click(function () {
		    	saveRoaster();
		    });
	    }
	    if (roasterDataObj != null && roasterDataObj.roasterDetails.length > 0) {
	    	var roasterDetails = roasterDataObj.roasterDetails;
	    	for (var t=0;t<roasterDetails.length;t++) {
	    		$( "#add" ).trigger('click');
	    		var l = parseInt(t)+1;
	    		$("#productQty"+l).val(roasterDetails[t].qty);
	    		$("#productType"+l).val(roasterDetails[t].product.id).selectmenu('refresh');
	    	}
	    	$('#noDefaultRoaster').hide();
	    	$('#set').show();
	    } else if (isReadOnly) {
	    	$('#noDefaultRoaster').show();
	    } else {
	    	$('#noDefaultRoaster').hide();
	    	$('#noProducts').prop("checked", true).checkboxradio('refresh');
	    	$('#add').hide();
	    	$('#set').hide();
	    	$('#set').empty();
	    	nextId = 0;
	    }
	    $('#roasterproductback').off('click');
		$('#roasterproductback').click(function () {
			$.mobile.changePage($("#view-calendar"));
		});
	});
 function loadRoasterDetails(month,year,callbackFn) {
	 loadingSpinner.show();
	 calObj = callbackFn;
	 var requestObj = {};
	 requestObj.user = storage.get('USER');
	 requestObj.month = month;
	 requestObj.year = year;
	 var jsonRequest = prepareRequestData (requestObj, 'getRoasterDetails');    
	roasters = [];
	$.ajax({
          type: 'POST',
          url: base_URL + "/getRoasterDetails",
          data: jsonRequest,
          dataType: 'json',
        contentType: 'application/json; charset=utf-8',
          success: loadRoasterSuccess,
          error: loadRoasterFail

          });
}
function loadRoasterSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   //swal("Error", "Problem communicationg with server.");
	   roasters = [];
   } else {
	  var roasterData = data.body;
	  if (roasterData != null && roasterData.length != 0) {
		  for (var g=0;g<roasterData.length;g++) {
			  var roasterDetails = roasterData[g].roasterDetails;
			  var prodQty = 0;
			  var prodAmount =0;
			  for (var h=0;h<roasterDetails.length;h++) {
				  prodQty = prodQty + parseInt(roasterDetails[h].qty);
				  prodAmount= prodAmount + parseFloat(roasterDetails[h].rate);
			  }
			  roasterData[g].prodQty = prodQty;
			  roasterData[g].prodAmount =  parseFloat(roasterData[g].amount).toFixed(2);
			  if (roasterData[g].date != null && roasterData[g].date != '') {
				  var dateNum = parseInt(new Date(parseInt(roasterData[g].date.toString())).getDate());
				  roasters[dateNum] = roasterData[g];
			  }
		  }
	  }
   }
   calObj();
}

function loadRoasterFail(data) {
     loadingSpinner.hide();
     //swal("Network Error", "There was an error in communicating with the server.");
     roasters = [];
     calObj();
}
function parseDate(s) {
  var b = s.split(/\D/);
  return new Date(b[0], --b[1], b[2]);
}
function containsElement(arr, elem) {
	if (arr != null) {
		for(var t=0;t<arr.length;t++) {
			if (arr[t] == elem) {
				return true;
			}
		}
	}
	return false;
}
function validateRoaster() {
	var toDateVal = parseDate($('#toDate').val());
	var maxDateVal = parseDate($('#toDate').attr("max"));
    var fromDateVal = parseDate($('#fromDate').val());
    var prodTypeArr = [];
    if (toDateVal < fromDateVal) {
    	roasterValidationError("To Date should be greater than From Date");
    	return false;
    }
    if (toDateVal > maxDateVal) {
    	roasterValidationError("To Date should not be more than "+$('#toDate').attr("max"));
    	return faslse;
    }
    var noProducts = $('#noProducts').prop("checked");
    if (noProducts) {
    	return true;
    }
    if (nextId == 0) {
    	roasterValidationError("Please enter required product details");
    	return false;
    }
   
    for (var k=1;k<=nextId;k++) {
    	if ($("#set"+k).is(":visible")) {
    		var prodType = $('#productType'+k).val();
        	var prodQty = $('#productQty'+k).val();
        	if (prodType == '') {
        		roasterValidationError("Please select Product Type");
            	return false;
        	}
        	if (prodQty == '') {
        		roasterValidationError("Please enter Product Qty");
            	return false;
        	}
        	if (parseInt(prodQty) < 1) {
        		roasterValidationError("Please enter Product Qty as more than 1");
            	return false;
        	}
        	
        	 if (containsElement(prodTypeArr, prodType)) {
        		 roasterValidationError("Selected Product Types should not be duplicate");
             	 return false;
        	 } else {
        		 prodTypeArr.push(prodType);
        	 }
    	}
    	
    }
    return true;
}
function roasterValidationError(message) {
	swal("Roaster Error", message);
}

function saveRoaster() {
	var flag = validateRoaster();
	if (!flag) {
		return;
	}
	loadingSpinner.show();
	var requestObj = {};
	 requestObj.user = storage.get('USER');
	 requestObj.fromDate = $('#fromDate').val();
	 requestObj.toDate = $('#toDate').val();
	 var roasterDetails = [];
	 var noProducts = $('#noProducts').prop("checked");
	 if (!noProducts) {
		 for (var k=1;k<=nextId;k++) {
	    	if ($("#set"+k).is(":visible")) {
	    		var prodType = $('#productType'+k).val();
	        	var prodQty = $('#productQty'+k).val();
	        	var req = {};
	        	var prod = {}; 
	        	prod.id = prodType;
	        	req.product = prod;
	        	req.qty = prodQty;
	        	roasterDetails.push(req);
	    	}
	    	
	    }
	 }
	 requestObj.roasterDetails = roasterDetails;
	 var jsonRequest = prepareRequestData (requestObj, 'updateRoaster');    
	roasters = [];
	$.ajax({
         type: 'POST',
         url: base_URL + "/updateRoaster",
         data: jsonRequest,
         dataType: 'json',
       contentType: 'application/json; charset=utf-8',
         success: saveRoasterSuccess,
         error: saveRoasterFail

         });

}
function saveRoasterSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   swal("Error", "Problem communicationg with server.");
   } else {
	   swal("Success", "Roaster Modified successfully");
	   $.mobile.changePage($("#view-calendar"));
   }
}

function saveRoasterFail(data) {
     loadingSpinner.hide();
     swal("Network Error", "There was an error in communicating with the server.");

}