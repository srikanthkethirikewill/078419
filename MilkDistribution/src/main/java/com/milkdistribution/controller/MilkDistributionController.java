package com.milkdistribution.controller;


import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.milkdistribution.dto.BillingDTO;
import com.milkdistribution.dto.RoasterDTO;
import com.milkdistribution.dto.UserDTO;
import com.milkdistribution.entity.Area;
import com.milkdistribution.entity.Billing;
import com.milkdistribution.entity.Product;
import com.milkdistribution.entity.Roaster;
import com.milkdistribution.entity.User;
import com.milkdistribution.service.AreaService;
import com.milkdistribution.service.BillingService;
import com.milkdistribution.service.ProductService;
import com.milkdistribution.service.RoasterService;
import com.milkdistribution.service.UserService;
import com.milkdistribution.vo.RequestDTO;
import com.milkdistribution.vo.ResponseDTO;



@RestController
public class MilkDistributionController {
	
	@Autowired
	RoasterService roasterService;
	
	@Autowired
	BillingService billingService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AreaService areaService;
	
	@Autowired
	ProductService productService;
			
	
	
	
	
	@Scheduled(cron="0 0 4 * * ?")
	public void generateRoasters() throws Exception {
		roasterService.generateRoaster();
	}
	
	@Scheduled(cron="0 0 20 28-31 * ?")
	public void generateBilling() {
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DATE)) {
			billingService.performBilling();
		}
		
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST) 
	public ResponseDTO<String> createUser(@RequestBody RequestDTO<UserDTO> request) {  
		UserDTO user = request.getBody();
	    userService.createUser(user);
	    ResponseDTO<String> responseDTO = new ResponseDTO<String>();
	    return responseDTO;
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST) 
	public ResponseDTO<String> updateUser(@RequestBody RequestDTO<UserDTO> request) {  
		UserDTO user = request.getBody();
		userService.updateUser(user);
		ResponseDTO<String> responseDTO = new ResponseDTO<String>();
	    return responseDTO;
	}
	
	@RequestMapping(value = "/verifyUser", method = RequestMethod.POST) 
	public ResponseDTO<String> verifyUser(@RequestBody RequestDTO<UserDTO> request) {  
		UserDTO user = request.getBody();
		userService.verifyUser(user);
		ResponseDTO<String> responseDTO = new ResponseDTO<String>();
	    return responseDTO;
	}
	
	@RequestMapping(value = "/validateUser", method = RequestMethod.POST) 
	public ResponseDTO<User> validateUser(@RequestBody RequestDTO<UserDTO> request) {  
		UserDTO userDTO = request.getBody();
		ResponseDTO<User> responseDTO = new ResponseDTO<User>();
		responseDTO.setBody(userService.validateUser(userDTO));
	    return responseDTO;
	}
	
	@RequestMapping(value = "/forgotUserDetails", method = RequestMethod.POST) 
	public ResponseDTO<User> forgotUserDetails(@RequestBody RequestDTO<UserDTO> request) {  
		UserDTO userDTO = request.getBody();
		User user = userService.forgotUserDetails(userDTO);
		ResponseDTO<User> responseDTO = new ResponseDTO<User>();
		responseDTO.setBody(user);
	    return responseDTO;
	}
	
	@RequestMapping(value = "/pendingUsers", method = RequestMethod.POST) 
	public ResponseDTO<List<User>> getPendingUsers(@RequestBody RequestDTO<UserDTO> request) {  
		UserDTO userDTO = userService.getPendingUsers();
		ResponseDTO<List<User>> responseDTO = new ResponseDTO<List<User>>();
		responseDTO.setBody(userDTO.getUsers());
	    return responseDTO;
	}
	
	@RequestMapping(value = "/activeUsers", method = RequestMethod.POST) 
	public ResponseDTO<List<User>> getActiveUsers(@RequestBody RequestDTO<UserDTO> request) { 
		UserDTO userDTO = request.getBody();
		userDTO = userService.getVerifiedUsers(userDTO.getAreaId());
		ResponseDTO<List<User>> responseDTO = new ResponseDTO<List<User>>();
		responseDTO.setBody(userDTO.getUsers());
	    return responseDTO;
	}
	
	@RequestMapping(value = "/getAreas", method = RequestMethod.POST) 
	public ResponseDTO<List<Area>> getAreas(@RequestBody RequestDTO<UserDTO> request) { 
		ResponseDTO<List<Area>> responseDTO = new ResponseDTO<List<Area>>();
		responseDTO.setBody(areaService.getList());
	    return responseDTO;
	}
	
	@RequestMapping(value = "/createArea", method = RequestMethod.POST) 
	public ResponseDTO<String> createArea(@RequestBody RequestDTO<Area> request) {  
		areaService.createArea(request.getBody());
		ResponseDTO<String> responseDTO = new ResponseDTO<String>();
		return responseDTO;
	}
	
	@RequestMapping(value = "/updateArea", method = RequestMethod.POST) 
	public ResponseDTO<String> updateArea(@RequestBody RequestDTO<Area> request) {  
		areaService.updateArea(request.getBody());
		ResponseDTO<String> responseDTO = new ResponseDTO<String>();
		return responseDTO;
	}
	
	/*@RequestMapping(value = "/deleteArea", method = RequestMethod.POST) 
	public void deleteArea(@RequestBody RequestDTO<Area> request) {  
		areaService.deleteArea(request.getBody());
	}*/
	
	@RequestMapping(value = "/getProducts", method = RequestMethod.POST) 
	public ResponseDTO<List<Product>> getProducts(@RequestBody RequestDTO<UserDTO> request) {  
		ResponseDTO<List<Product>> responseDTO = new ResponseDTO<List<Product>>();
		responseDTO.setBody(productService.getList());
		return responseDTO;
	}
	
	@RequestMapping(value = "/createProduct", method = RequestMethod.POST) 
	public ResponseDTO<String> createProduct(@RequestBody RequestDTO<Product> request) {  
		productService.createProduct(request.getBody());
		ResponseDTO<String> responseDTO = new ResponseDTO<String>();
		return responseDTO;
	}
	
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST) 
	public ResponseDTO<String> updateProduct(@RequestBody RequestDTO<Product> request) {  
		productService.updateProduct(request.getBody());
		ResponseDTO<String> responseDTO = new ResponseDTO<String>();
		return responseDTO;
	}
	
	
	
	/*@RequestMapping(value = "/deleteProduct", method = RequestMethod.POST) 
	public void deleteProduct(@RequestBody RequestDTO<Product> request) {  
		productService.deleteProduct(request.getBody());
	}*/
	
	@RequestMapping(value = "/getBillingList", method = RequestMethod.POST) 
	public ResponseDTO<List<Billing>> getBillingList(@RequestBody RequestDTO<BillingDTO> request) { 
		BillingDTO billingDTO = request.getBody();
		billingService.getBillingList(billingDTO);
		ResponseDTO<List<Billing>> responseDTO = new ResponseDTO<List<Billing>>();
		responseDTO.setBody(billingDTO.getBillList());
		return responseDTO;
	}
	
	
	
	@RequestMapping(value = "/updateBilling", method = RequestMethod.POST) 
	public ResponseDTO<String> updateBilling(@RequestBody RequestDTO<Billing> request) {  
		billingService.updateBilling(request.getBody());
		ResponseDTO<String> responseDTO = new ResponseDTO<String>();
		return responseDTO;
	}
	
	@RequestMapping(value = "/getRoasterDetails", method = RequestMethod.POST)
	public ResponseDTO<List<Roaster>> getRoasterDetails(@RequestBody RequestDTO<RoasterDTO> request) {
		RoasterDTO roasterDTO = request.getBody();
		ResponseDTO<List<Roaster>> responseDTO = new ResponseDTO<List<Roaster>>();
		responseDTO.setBody(roasterService.getMonthlyRoaster(roasterDTO));
		return responseDTO;
	}
	
	@RequestMapping(value = "/updateRoaster", method = RequestMethod.POST)
	public ResponseDTO<String> updateRoaster(@RequestBody RequestDTO<RoasterDTO> request) {
		RoasterDTO roasterDTO = request.getBody();
		roasterService.updateRoaster(roasterDTO);
		ResponseDTO<String> responseDTO = new ResponseDTO<String>();
		return responseDTO;
	}
	
	@RequestMapping(value = "/createRoasters", method = RequestMethod.POST)
	public ResponseDTO<String> createRoasters(@RequestBody RequestDTO<String> request)throws Exception {
		//roasterService.createRoasters();
		roasterService.generateRoaster();
		ResponseDTO<String> responseDTO = new ResponseDTO<String>();
		return responseDTO;
	}
}
