/* turn of default loading spinner  */

$(document).on("mobileinit", function(){
    $.mobile.ajaxEnabled=false;
    $.mobile.loadingMessage = false;
});
