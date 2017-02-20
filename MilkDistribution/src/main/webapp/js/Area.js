$(document).on("pageshow","#view-areas",function(){
	loadMasterAreas();
	
	$('#viewareasback').off('click');
	$('#viewareasback').click(function () {
		$.mobile.changePage($("#view-admin-menu"));
	});
	$('#addArea').off('click');
	$('#addArea').click(function () {
		storage.set("AREA", {});
		storage.set("MODE", "I");
		$.mobile.changePage($("#area-detail"));
	});
});
function prepareMasterAreas() {
	$('#areaList').empty();
	var content = "";
	for (var t=0;t<areaCollection.length;t++) {
		content = content + "<li><a href='#' id='area"+t+"'>"+areaCollection[t].description+"</a></li>";
		
	}
	$('#areaList').append(content).listview( "refresh" );
	for (t=0;t<areaCollection.length;t++) {
		$('#area'+t).off("click");
		$('#area'+t).click(function (event) {
			var ind = parseInt(event.target.id.toString().substring(4));
			storage.set("AREA", areaCollection[ind]);
			storage.set("MODE", "U");
			$.mobile.changePage($("#area-detail"));
		});
	}
}
function loadMasterAreas() {
	var jsonRequest = prepareRequestData ({}, 'getAreas');

     //show loading view
     loadingSpinner.show();
     $.ajax({
    	 type: 'POST',
         url: base_URL + "/getAreas",
         data: jsonRequest,
         dataType: 'json',
         contentType: 'application/json; charset=utf-8',
         success: loadMasterAreasSuccess,
         error: loadMasterAreasFail

     });
}
function loadMasterAreasFail(data) {
    loadingSpinner.hide();
    areaCollection = [];
    prepareMasterAreas();
}



function loadMasterAreasSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   areaCollection = [];
   } else if (data.body == "null") {
	   areaCollection = [];
   } else {
	   areaCollection = data.body;
   }
   prepareMasterAreas();
}
$(document).on("pageshow","#area-detail",function(){
	$('#area_submit').off('click');
	$('#area_submit').click(function () {
		saveArea();
	});
	var areaObj = storage.get("AREA");
	$('#areaDescription').val(areaObj.description);
	$('#areadetailback').off('click');
	$('#areadetailback').click(function () {
		$.mobile.changePage($("#view-areas"));
	});
});
function saveArea() {
	var description = $('#areaDescription').val();
	if (description == '') {
		swal("Area Error", "Enter Area");
		return;
	}
	loadingSpinner.show();
	var areaObj = storage.get("AREA");
	areaObj.description = description;
    var serviceName = (storage.get("MODE") == "I") ? "createArea" : "updateArea";
	
    
    var jsonRequest = prepareRequestData (areaObj, serviceName);
	$.ajax({
          type: 'POST',
          url: base_URL + "/" +serviceName,
          data: jsonRequest,
          dataType: 'json',
          contentType: 'application/json; charset=utf-8',
          success: saveAreaSuccess,
          error: saveAreaFail

     });

}
function saveAreaSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   swal("Error", "Problem communicationg with server.");
   } else {
	   swal("Success", "Area Saved successfully");
	   $.mobile.changePage($("#view-areas"));
   }
}

function saveAreaFail(data) {
     loadingSpinner.hide();
     swal("Network Error", "There was an error in communicating with the server.");

}
