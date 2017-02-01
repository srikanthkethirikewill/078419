/**
 * @author mahipal banala
 *
 */
(function($) {
     $.bbObj = {

//Central AJAX caller gateway.

 ajax: function(paramsObj) {

    var encUserInfo = (window.storage.get('USERINFO') === null) ? paramsObj.userinfo:window.storage.get('USERINFO');
    //var encUserInfo = paramsObj.userinfo;

     $.ajax({
        type: paramsObj.type, //mandatory field
        url: paramsObj.url, //mandatory
       headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + encUserInfo
            },

        async: (paramsObj.async != undefined)?paramsObj.async:true,
        data : paramsObj.data,
        dataType : (paramsObj.dataType != undefined)?paramsObj.dataType:'json',
        cache: (paramsObj.cache != undefined)?paramsObj.cache:true,
        timeout: 60000,
        success: function(responseData) {

                       paramsObj.success(responseData); //Invoke success callback method.

        },

        error: function(jqXHR, textStatus, errorThrown) {

        	 if(textStatus == "error" || textStatus == "timeout" ) {
             	showErrorMessage();
             } else {
               paramsObj.error(jqXHR, textStatus, errorThrown);
             }

        }
     });
 }


     }
}(jQuery));



var showErrorMessage = function(){

	swal("Network Error", "There was an error in communicating with the server.");
	loadingSpinner.hide();

}


/**
 * Local storage wrapper

 */

window.storage = {
store:localStorage,
get: function( key ) {
    try {
        return JSON.parse(this.store[key]);
    } catch(e) {};
    return undefined;
},
set: function( key, value) {
    try {
        this.store[key] = JSON.stringify(value);
    } catch(e) {};
},
remove: function( key ) {
    try {
        this.store.removeItem(key);

    } catch(e) {};

},
removeAll: function() {
    try {
        this.store.clear();

    } catch(e) {};
},

loggedIn: function() {
    try {
        for (var key in this.store){
            if(key=='BIZBEE') {
                return true;
                break;
            }
        }

    } catch(e) {};

    return false;
}

}

/**
 GetUnique array
 */

Array.prototype.getUnique = function(){
    var u = {}, a = [];
    for(var i = 0, l = this.length; i < l; ++i){
        if(u.hasOwnProperty(this[i])) {
            continue;
        }
        a.push(this[i]);
        u[this[i]] = 1;
    }
    return a;
}


/**
 Get value for key from object
 */


var getValueForKey = function(obj, key_search) {
    var retObj ;
    for (var key in obj) {
        if ((obj.hasOwnProperty(key)) && (key==key_search)) {
            retObj = obj[key];
        }
    }

    return retObj;

}


/**
   Show loading spinner during ajax calls
 */

window.loadingSpinner = {

    show: function() {
        $('body').waitMe({ effect:'bounce', bg: 'rgba(0,0,0,0.3)', color:'rgb(253,184,25)', source:'images/img.svg'});
    },

    hide: function() {
        $('body').waitMe('hide');
    }

}

/*thhere is no image displaying color and name first letter*/
function displayLetter(Nsize){
  var color = ['#ddd','#ccc','#000','#3d3d3d','#00ff00','#fff'];
  return color[Nsize%6];
}
