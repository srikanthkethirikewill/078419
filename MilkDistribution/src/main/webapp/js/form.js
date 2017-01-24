function send_email() {
var name=$("#app_name").val();
var email=$("#app_email").val();
var phone=$("#app_phone").val();
if(name=="")
{
	$("#app_name").addClass("errors");
}
else
{
	$("#app_name").removeClass("errors");
}
if(!validEmail(email))
{
	$("#app_email").addClass("errors");
}
else
{
	$("#app_email").removeClass("errors");
}
if(phone=="")
{
	$("#app_phone").addClass("errors");
}
else
{
	$("#app_phone").removeClass("errors");
}

if(name!="" && phone!="" && validEmail(email) )
{
	$("#mail_msg").load("email.php?name="+name+"&email="+email+"&phone="+phone);
	$("#app_name").val('');
	$("#app_email").val('');
	$("#app_phone").val('');
}	
return false;
}
function validEmail(e) {
    var filter = /^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/;
    return String(e).search (filter) != -1;
}