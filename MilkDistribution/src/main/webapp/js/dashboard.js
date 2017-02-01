/* dashboard handler */

$(document).on( "pagecontainerbeforeshow", function( event, ui ) {

       var toPageID = ui.toPage.prop("id");

       if(toPageID == "dashboard") {  //if dashboard screen

           //show loading view
           loadingSpinner.show();

           $.bbObj.ajax({
                  type: 'GET',
                  url: dashboard_API_URL,
                  data: {},
                  success: dbSuccess,
                  error: dbFail
            });

       }


});

$( document ).ready(function() {


    $('#dashboard-content').on('click', '.dbitem', function(e) {
    	e.preventDefault();
       //var dbitem = $(this).attr("item");
       var dbitem = $( this ).find( "p.counttitle" ).html();  //alert(dbitem)
       storage.set("DBITEM", dbitem);

       window.location = "#listings";

     });
});




function dbSuccess(data) {
     loadingSpinner.hide();
     //alert("dbSuccess: " + data);

    storage.set("DBJSON", data);

    loadPanelContent($("#dashboard-panel"));
    loadDashboard();

}

function dbFail(data) {
     loadingSpinner.hide();
    alert("dbFail: " + data);

}


function loadDashboard(){

    var dbJson = storage.get("DBJSON");

    var content = "";

	content += '<div class="ui-grid-a">';
	content += '<div class="ui-block-a dbitem"><div id="dashboard_box" class="ui-bar ui-bar-a" ><span class="bbcount">' + dbJson.approval + '</span><p class="counttitle">Pending Approvals</p></div></div>';
	content += '<div class="ui-block-b dbitem"><div id="dashboard_box" class="ui-bar ui-bar-a" ><span class="bbcount">' + dbJson.inputTask + '</span> <p class="counttitle">Pending Tasks</p></div></div>';
	content += '</div>';


	content += '<div class="ui-grid-a">';
	content += '<div class="ui-block-a dbitem"><div id="dashboard_box" class="ui-bar ui-bar-a" ><span class="bbcount">' + dbJson.clarification + '</span> <p class="counttitle">Clarifications</p></div></div>';
	content += '<div class="ui-block-b dbitem"><div id="dashboard_box" class="ui-bar ui-bar-a" ><span class="bbcount">' + dbJson.inProgress + '</span> <p class="counttitle">Pending Requests</p></div></div>';
	content += '</div>';

	content += '<div class="ui-grid-a">';
	content += '<div class="ui-block-a dbitem"><div id="dashboard_box" class="ui-bar ui-bar-a" ><span class="bbcount">' + dbJson.completed + '</span> <p class="counttitle">Completed Requests</p></div></div>';
	content += '</div>';

  content += '<a href="#apps" class="float">';
  content += '<i class="fa fa-plus my-float"></i>';
  content += '</a>';

    $('#dashboard-content').html(content);
    $('#dashboard').trigger('create');

}
