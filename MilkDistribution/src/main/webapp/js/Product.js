$(document).on("pageshow","#view-products",function(){
	loadMasterProducts();
	
	$('#viewproductsback').off('click');
	$('#viewproductsback').click(function () {
		$.mobile.changePage($("#view-admin-menu"));
	});
	$('#addProduct').off('click');
	$('#addProduct').click(function () {
		storage.set("PRODUCT", {});
		storage.set("MODE", "I");
		$.mobile.changePage($("#product-detail"));
	});
});
function prepareMasterProducts() {
	$('#productList').empty();
	var content = "";
	for (var t=0;t<productCollection.length;t++) {
		content = content + "<li><a href='#' id='product"+t+"'>"+productCollection[t].description+"</a></li>";
		
	}
	$('#productList').append(content).listview( "refresh" );
	for (t=0;t<productCollection.length;t++) {
		$('#product'+t).off("click");
		$('#product'+t).click(function (event) {
			var ind = parseInt(event.target.id.toString().substring(7));
			storage.set("PRODUCT", productCollection[ind]);
			storage.set("MODE", "U");
			$.mobile.changePage($("#product-detail"));
		});
	}
}
function loadMasterProducts() {
	var jsonRequest = prepareRequestData ({}, 'getProducts');

     //show loading view
     loadingSpinner.show();
     $.ajax({
    	 type: 'POST',
         url: base_URL + "/getProducts",
         data: jsonRequest,
         dataType: 'json',
         contentType: 'application/json; charset=utf-8',
         success: loadMasterProductsSuccess,
         error: loadMasterProductsFail

     });
}
function loadMasterProductsFail(data) {
    loadingSpinner.hide();
    productCollection = [];
    prepareMasterProducts();
}



function loadMasterProductsSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   productCollection = [];
   } else if (data.body == "null") {
	   productCollection = [];
   } else {
	   productCollection = data.body;
   }
   prepareMasterProducts();
}
$(document).on("pageshow","#product-detail",function(){
	$('#product_submit').off('click');
	$('#product_submit').click(function () {
		saveProduct();
	});
	var productObj = storage.get("PRODUCT");
	$('#productDescription').val(productObj.description);
	$('#productRate').val(productObj.price);
	$('#productdetailback').off('click');
	$('#productdetailback').click(function () {
		$.mobile.changePage($("#view-products"));
	});
});
function saveProduct() {
	var description = $('#productDescription').val();
	if (description == '') {
		swal("Product Error", "Enter Product");
		return;
	}
	var rate = $('#productRate').val();
	if (rate == '') {
		swal("Product Error", "Enter Rate");
		return;
	}
	loadingSpinner.show();
	var productObj = storage.get("PRODUCT");
	productObj.description = description;
	productObj.price = rate;
    var serviceName = (storage.get("MODE") == "I") ? "createProduct" : "updateProduct";
	
    
    var jsonRequest = prepareRequestData (productObj, serviceName);
	$.ajax({
          type: 'POST',
          url: base_URL + "/" +serviceName,
          data: jsonRequest,
          dataType: 'json',
          contentType: 'application/json; charset=utf-8',
          success: saveProductSuccess,
          error: saveProductFail

     });

}
function saveProductSuccess(data) {
    loadingSpinner.hide();
    //alert("success: user is: " + data.user.firstName);
   if (data.result.errorCode == "failure") {
	   swal("Error", "Problem communicationg with server.");
   } else {
	   swal("Success", "Product Saved successfully");
	   $.mobile.changePage($("#view-products"));
   }
}

function saveProductFail(data) {
     loadingSpinner.hide();
     swal("Network Error", "There was an error in communicating with the server.");

}
