<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Medical & health landing page templates for online appointment that help you to medical service & patient visit">
<meta name="author" content="">
<title>Wealth.life | Medical Landing Page Template</title>
<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/font-awesome.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
<!-- your favicon icon link -->
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />

<!-- Custom CSS -->

</head>
<!-- The #page-top ID is part of the scrolling feature - the data-spy and data-target are part of the built-in Bootstrap scrollspy function -->
<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">
<div class="container">
  <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
      <div class="navbar-header page-scroll">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
        <a class="navbar-brand" href="#page-top">
        <h1 class="logo-brand"><img src="images/milkdonor3.png" width="7%" height="7%"/></h1>
        </a> </div>
      <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav">
          <!-- Hidden li included to remove active class from about link when scrolled up past about section -->
          <li class="hidden"> <a href="#page-top"></a> </li>
          <!-- li class="page-scroll"> <a href="#about">SERVICES</a> </li>
          <li class="page-scroll"> <a href="#services">FEATURE</a> </li>
          <li class="page-scroll"> <a href="#doctor">DOCTOR</a> </li>
          <li class="page-scroll"> <a href="#contact">CONTACT US</a> </li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">News <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                      <li><a href="blog.html">Blog</a></li>
                      <li><a href="blog-detail.html">Blog Single</a></li>
                    </ul>
                  </li-->
          <li class="page-scroll social"> <a href="#"><i class="fa fa-facebook-square fa-size"> </i></a> <a href="#"><i class="fa  fa-twitter-square fa-size"> </i> </a> <a href="#"><i class="fa fa-google-plus-square fa-size"> </i></a> <a href="#"><i class="fa fa-flickr fa-size"> </i> </a></li>
        </ul>
      </div>
      <!-- /.navbar-collapse --> 
    </div>
    <!-- /.container --> 
  </nav>
</div>
<section id="intro" class="intro-section"><!-- intro start -->
  <div class="container">
    <div class="row">
      <div class="col-md-6 intro-caption">
        <!-- <h1 class="intro-title">Expert health advice <br>
          for <span>wealth life</span> </h1>
        <p>Nullam id commodo augueat cursus arcu tempaus acnteger porta massa sed lectus auctorsit amet suscipit tortor hendreriusce laoreet dui eu.</p>
        <div class="page-scroll"> <a class="btn btn-default btn-green" href="#doctor">Meet The Doctor</a> <a class="btn btn-default btn-grey" href="#services">Learn More</a></div>
        
        <hr class="divider">
        
        <p class="call-info">info@wealthlife.com     |     1-800-643-4500</p> -->
      </div>
  
		<div class="col-md-offset-1 col-md-4 intro-caption appoinment-form">
        <h2> Request Form </h2>
        <div class="">
          <form role="form" >
          	<small id="mail_msg"></small>
            <div class="form-group">
              <label for="exampleInputName">Mobile Number</label>
              <input type="text" class="form-control" id="mobileNumber" placeholder="Mobile Number" maxlength="10">              
            </div>
            <button id="generate" class="btn btn-block btn-orange">Generate OTP</button> <br>
            <div class="form-group">
              <label for="exampleInputEmail1">OTP</label>
              <input type="text" class="form-control" id="otp" placeholder="OTP" maxlength="4">              
            </div>                       
            <div class="form-group lookingFor ">
              <label for="exampleInputPassword1">Are you looking For</label>              
            </div>
            <div class="form-group lookingFor ">
              <input type="radio" name="requestType" id="requestor" checked>
              <label for="exampleInputPassword1">Consuming</label>
              <input type="radio" name="requestType" id="donor">
              <label for="exampleInputPassword1">Donating</label>
            </div>
             <div class="form-group requestor-group">
              <label for="exampleInputName">Baby Born Date</label>
              <input type="text" class="form-control date-field" id="babyBornDate" placeholder="Baby Born Date in dd/mm/yyyy" required><br>
              <input type="text" class="form-control date-field" id="ageInWeeks" readOnly>
            </div>
            <div class="form-group  requestor-group">
              <label for="exampleInputEmail1">Baby Born At</label>              
            </div>
            <div class="form-group  requestor-group">
              <input type="radio" name="babyBornAt" id="veryPremature" value="<20 weeks">
              <label for="exampleInputPassword1">Very Premature</label>
              <input type="radio" name="babyBornAt" id="premature" value="20-37 weeks">
              <label for="exampleInputPassword1">Premature</label>
              <input type="radio" name="babyBornAt" id="term" value="38+ weeks" checked>
              <label for="exampleInputPassword1">Term</label>
            </div>
            <div class="form-group  requestor-group">
              <label for="exampleInputPassword1">Does baby or mother have any current medical condition</label>              
            </div>
            <div class="form-group   requestor-group">
              <input type="radio" name="medicalCondition" id="conditionYes" value="Y">
              <label for="exampleInputPassword1">Yes</label>
              <input type="radio" name="medicalCondition" id="conditionNo" value="N" checked>
              <label for="exampleInputPassword1">No</label>              
            </div>
            <div class="form-group  requestor-group">
              <label for="exampleInputPassword1">Is Baby in NICU now</label>              
            </div>
            <div class="form-group  requestor-group">
              <input type="radio" name="isBabyInICU" id="icuYes" value="Y">
              <label for="exampleInputPassword1">Yes</label>
              <input type="radio" name="isBabyInICU" id="icuNo" value="N" checked>
              <label for="exampleInputPassword1">No</label>              
            </div>
            <div class="form-group  requestor-group">
              <label for="exampleInputName">Hospital baby is currently now</label>
              <input type="text" class="form-control" id="hospital" placeholder="Hospital" maxlength="20">              
            </div>
            <div class="form-group  requestor-group">
              <label for="exampleInputName">City</label>
              <select class="form-control" id="city">
              	<option value="Hyderabad">Hyderabad</option>   
              </select>           
            </div>
            <div class="form-group requestor-group">
              <label for="exampleInputName">Contact Person Mobile</label>
              <input type="text" class="form-control" id="cpMobileNumber" placeholder="Contact Person Mobile" maxlength="10" >              
            </div>
            <div class="form-group requestor-group">
              <label for="exampleInputName">Address</label>
              <textarea rows="5" cols="10" class="form-control" placeholder="Address" name="address" id="address" maxlength="200"></textarea>              
            </div>
            <div class="form-group donor-group">
              <label for="exampleInputName">Address</label>
              <textarea rows="5" cols="10" class="form-control" placeholder="Address" name="address" id="address" maxlength="200"></textarea>              
            </div>
            <button id="generateRequest" class="lookingFor btn btn-block btn-orange">SUBMIT</button>            
          </form>
         
        </div>
      </div>
	
     </div>
  </div>
</section>
<!-- intro close -->

<div class="footer"><!-- footer section start -->
  <div class="container">
    <div class="row ft">
      <div class="col-md-9">
        <p> © Copyright 2014. Anavigationll Rights Reserved by Wealth.life </p>
      </div>
      <div class="col-md-3"> <a href="#"> <i class="fa fa-facebook-square fa-2x social-icon"> </i></a><a href="#"> <i class="fa  fa-twitter-square  fa-2x social-icon"> </i> </a><a href="#"><i class="fa fa-google-plus-square fa-2x social-icon"> </i></a> <i class="fa fa-flickr  fa-2x social-icon"> </i> <a href="#"><i class="fa fa-pinterest-square fa-2x social-icon"> </i> </a></div>
    </div>
  </div>
</div><!-- footer section close -->

<!-- Core JavaScript Files --> 
<script src="js/jquery-1.10.2.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/jquery.easing.min.js"></script> 
<!-- Custom Theme JavaScript --> 
 
<script src="js/scrolling-nav.js"></script> 
<script src="js/owl.carousel.js"></script> 
<script>
$("#owl-demo").owlCarousel({
 
      autoPlay: 3000, //Set AutoPlay to 3 seconds
 
      items : 4,
      itemsDesktop : [1199,3],
      itemsDesktopSmall : [979,3]
 
  });
 
$('.requestor-group').hide();
$('.donor-group').hide();
$('.lookingFor').hide();

$('#generate').click(function (){
	var headerObj = getHeader();
	var bodyObj = {mobileNumber:$('#mobileNumber').val()};
	var requestObj={header:headerObj,body:bodyObj};
	$.ajax({
        type: 'POST',
        url: '/MilkDonor/rest/createOTP',
        data: JSON.stringify(requestObj),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function(response) {
            alert(JSON.stringify(response));
        },
        error: function(error) {
            console.log(error);
        }
    });
});

$('#otp').blur(function (){
	var headerObj = getHeader();
	var bodyObj = {mobileNumber:$('#mobileNumber').val(),otp:$('#otp').val()};
	var requestObj={header:headerObj,body:bodyObj};
	$.ajax({
        type: 'POST',
        url: '/MilkDonor/rest/validateOTP',
        data: JSON.stringify(requestObj),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function(response) {
            if (response.otp == $('#otp').val()) {
            	$('.lookingFor').show();
            	$('.requestor-group').show();
            	$('.donor-group').hide();
            } 
        },
        error: function(error) {
            console.log(error);
        }
    });
});

$("input[name='requestType']").click(function (){	
	if ($('#requestor').prop('checked')==true) {
		$('.requestor-group').show();	
		$('.donor-group').hide();
	} else {
		$('.requestor-group').hide();
		$('.donor-group').show();
	}
	
});

$('#generateRequest').click(function (){
	var headerObj = getHeader();
	var bodyObj,url;
	if ($('#requestor').prop('checked')==true) {
		bodyObj = {
				babyBornDate:$('#babyBornDate').val(),
				babyBornAt:$("input[name='babyBornAt']").val(),
				medicalCondition:$("input[name='medicalCondition']").val(),
				isBabyInICU:$("input[name='isBabyInICU']").val(),
				ageInWeeks:$('#ageInWeeks').val(),
				hospital:$('#hospital').val(),
				city:$('#city').val(),
				address:$('#address').val(),
				mobileNumber:$('#cpMobileNumber').val()
			  };
			url = '/MilkDonor/rest/createRequest';
	} else {
		return;	
	}	 
	var requestObj={header:headerObj,body:bodyObj};
	$.ajax({
        type: 'POST',
        url: url,
        data: JSON.stringify(requestObj),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function(response) {
            $('#mail_msg').text('Request created Successfully');
        },
        error: function(error) {
            console.log(error);
        }
    });
});

$('#babyBornDate').blur(function () {
	var mdy = $('#babyBornDate').val().split('/');    
	var dob = new Date(mdy[2], mdy[1]-1, mdy[0]);	
	var today = new Date();
	var days = Math.round((today-dob)/(1000*60*60*24));
	var weeks = Math.round(days/7);
	$('#ageInWeeks').val(weeks);
});



function getHeader() {
	var header = {isBrowser:"true", deviceId:$('#mobileNumber').val(), otp:$('#otp').val(), version:"1.0"};
	return header;
}

</script>

<!--====== Google Analytics Code 
 Note :- Keep in mind that the string UA-XXXXXXXX-X should be replaced with your own Google Analytics account number.
--> 


</body>
</html>
