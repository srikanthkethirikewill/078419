package com.milkdistribution.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.milkdistribution.dto.BillingDTO;
import com.milkdistribution.dto.UserDTO;
import com.milkdistribution.entity.Area;
import com.milkdistribution.entity.Authentication;
import com.milkdistribution.entity.Billing;
import com.milkdistribution.entity.Product;
import com.milkdistribution.entity.Requestor;
import com.milkdistribution.entity.User;
import com.milkdistribution.service.AreaService;
import com.milkdistribution.service.AuthenticationService;
import com.milkdistribution.service.BillingService;
import com.milkdistribution.service.ProductService;
import com.milkdistribution.service.RequestorService;
import com.milkdistribution.service.RoasterService;
import com.milkdistribution.service.UserService;
import com.milkdistribution.vo.Header;
import com.milkdistribution.vo.RequestDTO;
import com.milkdistribution.vo.RequestorList;


@RestController
public class MilkDistributionController {
	
	@Autowired
	RequestorService requestorService;
	
	@Autowired
	RoasterService roasterService;
	
	@Autowired
	BillingService billingService;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AreaService areaService;
	
	@Autowired
	ProductService productService;
			
	@RequestMapping(value = "/createRequest", method = RequestMethod.POST, headers = {"Accept=application/json","Content-type=application/json"}) 
	public Requestor createRequest(@RequestBody RequestDTO<Requestor> request) {  
	    Requestor requestor = request.getBody();
	    Header header = request.getHeader();
	    Authentication authentication = authenticationService.getAuthentication(header.getDeviceId());
	    if (authentication == null || (!authentication.getOtp().equals(header.getOtp()))) {
	    	return null;
	    }
	    requestor.setAuthentication(authentication);
	    requestorService.save(requestor);
	    return requestor;
	}
	
	@RequestMapping(value = "/createOTP", method = RequestMethod.POST, headers = {"Accept=application/json","Content-type=application/json"}) 
	public Authentication createOTP(@RequestBody RequestDTO<Authentication> request) {  
	    Authentication authentication = request.getBody();
	    Authentication updatedAuthentication = authenticationService.getAuthentication(authentication.getMobileNumber());
	    if (updatedAuthentication == null) {
	    	authenticationService.save(authentication);
	    	return authentication;
	    } else {
	    	authenticationService.update(updatedAuthentication);
	    	return updatedAuthentication;
	    }	    
	}
	
	@RequestMapping(value = "/validateOTP", method = RequestMethod.POST) 
	public Authentication validateOTP(@RequestBody RequestDTO<Authentication> request) {  
	    Authentication authentication = request.getBody();
	    String otp = authentication.getOtp();
	    authentication = authenticationService.getAuthentication(authentication.getMobileNumber());
	    if (authentication == null || (!authentication.getOtp().equals(otp))) {
	    	return null;
	    }
	    return authentication;
	}
	
	@RequestMapping(value = "/listRequestors", method = RequestMethod.GET) 
	@ResponseBody
	public RequestorList getRequestors() {		
		
		RequestorList list = new RequestorList();
		list.setList(requestorService.list());
	    return list;
	}
	
	@Scheduled(cron="0 0 2 1/1 * ? *")
	public void generateRoasters() throws Exception {
		roasterService.generateRoaster();
	}
	
	@Scheduled(cron="0 0 20 L * ? *")
	public void generateBilling() {
		billingService.performBilling();
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST) 
	public void createUser(@RequestBody RequestDTO<UserDTO> request) {  
		UserDTO user = request.getBody();
	    userService.createUser(user);
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST) 
	public void updateUser(@RequestBody RequestDTO<UserDTO> request) {  
		UserDTO user = request.getBody();
		userService.updateUser(user);
	}
	
	@RequestMapping(value = "/verifyUser", method = RequestMethod.POST) 
	public void verifyUser(@RequestBody RequestDTO<UserDTO> request) {  
		UserDTO user = request.getBody();
		userService.verifyUser(user);
	}
	
	@RequestMapping(value = "/validateyUser", method = RequestMethod.POST) 
	public User validateUser(@RequestBody RequestDTO<UserDTO> request) {  
		UserDTO userDTO = request.getBody();
		User user = userService.validateUser(userDTO);
	    return user;
	}
	
	@RequestMapping(value = "/forgotUserDetails", method = RequestMethod.POST) 
	public User forgotUserDetails(@RequestBody RequestDTO<UserDTO> request) {  
		UserDTO userDTO = request.getBody();
		User user = userService.forgotUserDetails(userDTO);
	    return user;
	}
	
	@RequestMapping(value = "/pendingUsers", method = RequestMethod.POST) 
	public UserDTO getPendingUsers() {  
		UserDTO userDTO = userService.getPendingUsers();
	    return userDTO;
	}
	
	@RequestMapping(value = "/activeUsers", method = RequestMethod.POST) 
	public UserDTO getActiveUsers() {  
		UserDTO userDTO = userService.getVerifiedUsers();
	    return userDTO;
	}
	
	@RequestMapping(value = "/getAreas", method = RequestMethod.POST) 
	public List<Area> getAreas(@RequestBody RequestDTO<UserDTO> request) {  
		return areaService.getList();
	}
	
	@RequestMapping(value = "/createArea", method = RequestMethod.POST) 
	public void createArea(@RequestBody RequestDTO<Area> request) {  
		areaService.createArea(request.getBody());
	}
	
	@RequestMapping(value = "/updateArea", method = RequestMethod.POST) 
	public void updateArea(@RequestBody RequestDTO<Area> request) {  
		areaService.updateArea(request.getBody());
	}
	
	/*@RequestMapping(value = "/deleteArea", method = RequestMethod.POST) 
	public void deleteArea(@RequestBody RequestDTO<Area> request) {  
		areaService.deleteArea(request.getBody());
	}*/
	
	@RequestMapping(value = "/getProducts", method = RequestMethod.POST) 
	public List<Product> getProducts(@RequestBody RequestDTO<UserDTO> request) {  
		return productService.getList();
	}
	
	@RequestMapping(value = "/createProduct", method = RequestMethod.POST) 
	public void createProduct(@RequestBody RequestDTO<Product> request) {  
		productService.createProduct(request.getBody());
	}
	
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST) 
	public void updateProduct(@RequestBody RequestDTO<Product> request) {  
		productService.updateProduct(request.getBody());
	}
	
	
	
	/*@RequestMapping(value = "/deleteProduct", method = RequestMethod.POST) 
	public void deleteProduct(@RequestBody RequestDTO<Product> request) {  
		productService.deleteProduct(request.getBody());
	}*/
	
	@RequestMapping(value = "/getBillingList", method = RequestMethod.POST) 
	public List<Billing> getBillingList(@RequestBody RequestDTO<BillingDTO> request) { 
		BillingDTO billingDTO = request.getBody();
		billingService.getBillingList(billingDTO);
		return billingDTO.getBillList();		
	}
	
	
	
	@RequestMapping(value = "/updateBilling", method = RequestMethod.POST) 
	public void updateBilling(@RequestBody RequestDTO<Billing> request) {  
		billingService.updateBilling(request.getBody());
	}
}
