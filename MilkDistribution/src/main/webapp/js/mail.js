$("#login").click(function() {
var fname=$("#txt_fname").val();
var lname=$("#txt_lname").val();
var email=$("#txt_email").val();
var training=$("#txt_training").val();
if(fname=="")
{
	$("#txt_fname").addClass("errors");
}else{
	$("#txt_fname").removeClass("errors");
}
if(lname=="")
{
	$("#txt_lname").addClass("errors");
}else{
	$("#txt_lname").removeClass("errors");
}

if(!validEmail(email))
{
	$("#txt_email").addClass("errors");
}else{
	$("#txt_email").removeClass("errors");
}

if(fname!="" && lname!="" && validEmail(email))
{
	$("#mail_check").load("mail.php?fname="+fname+"&lname="+lname+"&training="+training+"&email="+email);
	$("#txt_fname").val('');
	$("#txt_lname").val('');
	$("#txt_email").val('');	
}	
return false;
});
function validEmail(e) {
    var filter = /^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/;
    return String(e).search (filter) != -1;
}
$("#news_send").click(function() {
							   
var email=$("#news_email").val();							   
if(!validEmail(email))
{
$("#news_email").addClass("errors");
}else{
$("#news_email").removeClass("errors");
$("#mail_msg").load("newsletter_mail.php?email="+email);
}						   
							   
});