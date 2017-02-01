/* Listings handler */

$(document).on( "pagecontainerbeforeshow", function( event, ui ) {

       var toPageID = ui.toPage.prop("id");

       if(toPageID == "listings") {  //if Listings screen
         //removing the old form
         $('#listings-content').empty();
           //show loading view
           loadingSpinner.show();

		   //Removing the section values
             storage.remove("APPID");
             storage.remove("TASKID");
             storage.remove("TASKNAME");
             storage.remove("APPENTITYID");
             storage.remove("TASKITEMID");
             storage.remove("STATUS");
             storage.remove("APPNAME");

           var dbItem = storage.get("DBITEM");
           var userId = storage.get("USERID");
           
           //change urls based on tasklisting / entity listings

            var ajaxURL;

                switch(dbItem) {

                case "Pending Approvals":
                    ajaxURL = base_URL + '/workFlowTaskItems/searching.json?query=taskStatus:IN_PROGRESS,workFlowTask.taskType:APPROVAL,users;' + userId;
                     storage.set("STATUS", 'IN_PROGRESS');
                    break;

                case "Pending Tasks":
                    ajaxURL = base_URL + '/workFlowTaskItems/searching.json?query=taskStatus:IN_PROGRESS,workFlowTask.taskType:INPUT_REQUEST,users;' + userId;
                    storage.set("STATUS", 'IN_PROGRESS');
                    break;

                case "Clarifications":
                    ajaxURL = base_URL + '/workFlowTaskItems/searching.json?query=taskStatus:CLARIFICATION,users;' + userId;
                    storage.set("STATUS", 'CLARIFICATION');
                    break;


                case "Pending Requests":
                    ajaxURL = base_URL + '/appEntities/searching.json?query=appStatus:IN_PROGRESS,createdBy.id:' + userId;
                    storage.set("STATUS", 'NO_BUTTONS');
                    break;

                case "Completed Requests":
                    ajaxURL = base_URL + '/appEntities/searching.json?query=appStatus:COMPLETED,createdBy.id:' + userId;
                    storage.set("STATUS", 'NO_BUTTONS');
                    break;

                case "My Pending":
                    ajaxURL = base_URL + '/appEntities/searching.json?query=appStatus:IN_PROGRESS&entityScope=MINE';
                    storage.set("STATUS", 'NO_BUTTONS');
                    break;
                case "My Completed":
                    ajaxURL = base_URL + '/appEntities/searching.json?query=appStatus:COMPLETED&entityScope=MINE';
                    storage.set("STATUS", 'NO_BUTTONS');
                    break;
                case "Company Pending":
                      ajaxURL = base_URL + '/appEntities/searching.json?query=appStatus:IN_PROGRESS&entityScope=COMPANY';
                      storage.set("STATUS", 'NO_BUTTONS');
                      break;
                case "Company Completed":
                      ajaxURL = base_URL + '/appEntities/searching.json?query=appStatus:COMPLETED&entityScope=COMPANY';
                      storage.set("STATUS", 'NO_BUTTONS');
                      break;


                default: break;
        }

           //ajaxURL = "json/tasklisting.json";

           $.bbObj.ajax({
                        type: 'GET',
                        url: ajaxURL,
                        data: {},
                        success: function(data) {  listingsSuccess(data, dbItem);  } ,
                        error: function(data) {  listingsFail(data, dbItem);  }

                        });

       }


});


function listingsSuccess(data, item) {
     loadingSpinner.hide();
     loadPanelContent($("#listings-panel"));
    loadListings(data, item);


}

function listingsFail(data, item) {
     loadingSpinner.hide();
    alert("dbFail: " + data);

}

function loadListings(data, item){

	var content = "";
	content += '<ul data-role="listview" id="ul-listings" style="margin-left:-2em">';
	var li_content = '';
	var taskEntityArray;


	if(item == "Pending Requests" || item == "Completed Requests" || item == "My Pending" || item == "My Completed" || item == 'Company Pending' || item == 'Company Completed') {

		taskEntityArray = data.appEntity;

		if(taskEntityArray.length == 0) {

			li_content += '<li data-icon="false"><a href="javascript:void(0);" style="margin:10px;">No Requests Found.</a></li>';
		}

		 jQuery.each(taskEntityArray, function( ind, val ){
		    	var img = 'http://app.bizbee.io/mobile/images/user.png';
          var assigneeNames = val.assigneeNames;
          var currentTaskNames = val.currentTaskNames;
          if(typeof val.assigneeNames == 'undefined') {
            assigneeNames = ' ';
          }
          if(typeof val.currentTaskNames == 'undefined') {
            currentTaskNames = ' ';
          }

          console.log(val.assigneeNames);
		    	li_content += '<li>';
				  li_content += '<a href="#" class="listitem"  appId="'+val.app.id+'" appName="'+val.app.name+'" taskName ="'+currentTaskNames+'"   appEntityId="'+val.id+'">';
		    	li_content += '<img src="' + img + '" class="ui-thumbnail ui-thumbnail-circular">';
		    	li_content += '<h2>' + val.app.name + '</h2>';
		    	li_content += '<p>' + currentTaskNames + ' at ' + val.createdTime + '</p>';
		    	li_content += '<p>' + assigneeNames + '</p>';
		    	//li_content += '<p>' + 'Due by - ' + val.slaTime + '</p>';
		    	li_content += '</a>';
		    	li_content += '</li>';


		    });



	} else {


		     taskEntityArray = data.workFlowTaskItem;

		     if(taskEntityArray.length == 0) {

                 li_content += '<li data-icon="false"><a href="javascript:void(0);" style="margin:10px;">No Tasks Found.</a></li>';

				}


		    jQuery.each(taskEntityArray, function( ind, val ){
		    	var img = 'http://app.bizbee.io/images' + val.appEntity.requester.image;
		    	if(typeof val.appEntity.requester.image == 'undefined') {
		    		img = 'http://app.bizbee.io/mobile/images/user.png';
		    	}

		    	li_content += '<li data-icon-"true">';
		    	li_content += '<a href="#" class="listitem" taskItemId="' + val.id + '" appId="'+val.appEntity.app.id+'" appName="'+val.appEntity.app.name+'"  taskId ="'+val.workFlowTask.id+'" taskName ="'+val.workFlowTask.name+'" appEntityId="'+val.appEntity.id+'">';
		    	li_content += '<img src="' + img + '" class="ui-thumbnail ui-thumbnail-circular">';
		    	li_content += '<h2>' + val.appEntity.app.name + '</h2>';
		    	li_content += '<p>' + val.workFlowTask.name + '</p>';
		    	li_content += '<p>' + val.appEntity.requester.firstName + ' at ' + val.createdTime + '</p>';
		    	li_content += '<p>' + 'Due by - ' + val.slaTime + '</p>';
		    	li_content += '</a>';
		    	li_content += '</li>';

		    });


	}

    content += li_content;
	  content += '</ul>';
      var userJson = storage.get('USERJSON');
    if(userJson.userType != 'END_USER'){
      content += '<a href="#apps" class="float">';
      content += '<i class="fa fa-plus my-float"></i>';
      content += '</a>';
    }


    //update listings
    $('#listings-content').html(content);

    //set page title
    $('#listings_title').html(item);

    //Refresh the page
    $('#listings').trigger('create');

}

$( document ).ready(function() {

	 $('#listings-content').on('click', '.listitem', function(e) {
		 e.preventDefault();
	       var appId = $(this).attr("appId");
         var appName = $(this).attr("appName");
         var taskId = $(this).attr("taskId");
         var taskName = $(this).attr("taskName");
         var appEntityId = $(this).attr("appEntityId");
         var taskItemId = $(this).attr("taskItemId");

	       storage.set("APPID", appId);
         storage.set("APPNAME", appName);
         storage.set("TASKID", taskId);
         storage.set("TASKNAME", taskName);
         storage.set("APPENTITYID", appEntityId);
         storage.set("TASKITEMID", taskItemId);

	       window.location = "#appform";

		   return;

	   });

});
