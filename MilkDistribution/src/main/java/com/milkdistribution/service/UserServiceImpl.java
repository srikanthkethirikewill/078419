package com.milkdistribution.service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.milkdistribution.dao.AreaDAO;
import com.milkdistribution.dao.ProductDAO;
import com.milkdistribution.dao.RoasterDAO;
import com.milkdistribution.dao.UserDAO;
import com.milkdistribution.dto.UserDTO;
import com.milkdistribution.entity.Area;
import com.milkdistribution.entity.Product;
import com.milkdistribution.entity.User;
import com.milkdistribution.entity.UserDailyRequirement;

@Service("userService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	AreaDAO areaDAO;
	
	@Autowired
	ProductDAO productDAO;
	
	@Autowired
	RoasterService roasterService;
	
	@Autowired
	RoasterDAO roasterDAO;
	
	@Autowired
	JavaMailSender mailSender;

	@Override
	public void createUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = userDTO.getUser();
		user = setEntities(user);
		userDAO.save(user);
		if (user.getMailId() != null && (!"".equals(user.getMailId()))) {
			SimpleMailMessage mailMessage= new SimpleMailMessage();
			mailMessage.setFrom("srikanthreddy.kethiri@gmail.com");
			mailMessage.setTo(user.getMailId());
			mailMessage.setSubject("Welcome to Milk Distribution App");
			mailMessage.setText("You have successfully registered in Milk Distribution App. Please give us some time for activating your account");
			mailSender.send(mailMessage);
		}
	}

	@Override
	public void updateUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = userDTO.getUser();
		user = setEntities(user);
		userDAO.save(user);
		if ("Y".equals(userDTO.getRoasterUpdateRequired())) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.AM_PM, Calendar.PM);
			roasterDAO.delete(calendar.getTime(), user);
			roasterService.saveRoaster(user, calendar);
		}
		if (user.getMailId() != null && (!"".equals(user.getMailId()))) {
			SimpleMailMessage mailMessage= new SimpleMailMessage();
			mailMessage.setFrom("srikanthreddy.kethiri@gmail.com");
			mailMessage.setTo(user.getMailId());
			mailMessage.setSubject("Details Update in Milk Distribution App");
			mailMessage.setText("Your details has been updated in Milk Distribution App. Please give us some time for activating your account");
			mailSender.send(mailMessage);
		}
		
	}

	@Override
	public void verifyUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = userDTO.getUser();		
		user = setEntities(user);
		boolean flag = true;
		if (userDTO.getSeq() != 0) {
			User userObj = userDAO.getUserBySequence(userDTO.getSeq());
			if (userObj != null) {
				userObj.setUserId(user.getUserId());
				userObj.setPassword(user.getPassword());
				userObj.setMobile(user.getMobile());
				userObj.setMailId(user.getMailId());
				userDAO.save(userObj);
				userDAO.delete(user);
				flag = false;
			}
		} 
		if (flag) {
			userDAO.save(user);
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
			roasterService.saveRoaster(user, calendar);
		}
		if (user.getMailId() != null && (!"".equals(user.getMailId()))) {
			SimpleMailMessage mailMessage= new SimpleMailMessage();
			mailMessage.setFrom("srikanthreddy.kethiri@gmail.com");
			mailMessage.setTo(user.getMailId());
			mailMessage.setSubject("User Activation in Milk Distribution App");
			mailMessage.setText("You have been activated in Milk Distribution App. Now you can use our services.");
			mailSender.send(mailMessage);
		}
	}
	
	private User setEntities(User user) {
		if (user.getArea() != null) {
			Area area =areaDAO.getArea(user.getArea().getId());
			user.setArea(area);
		}
		Set<UserDailyRequirement> requirement = user.getRequirement();
		
		User userObj = null;
		if (user.getId() != null) {
			userObj = userDAO.getUser(user.getId());
			if(requirement != null) {
				for (UserDailyRequirement req:requirement) {
					Product product = productDAO.getProduct(req.getProduct().getId());
					req.setProduct(product);
					req.setUser(userObj);
				}
			}
			userObj.getRequirement().clear();
			userObj.getRequirement().addAll(requirement);
			userObj.setAddress(user.getAddress());
			userObj.setArea(user.getArea());
			userObj.setMailId(user.getMailId());
			userObj.setMobile(user.getMobile());
			userObj.setPassword(user.getPassword());
			userObj.setRole(user.getRole());
			userObj.setSeq(user.getSeq());
			userObj.setStatus(user.getStatus());
			userObj.setUserId(user.getUserId());
			userObj.setFirstName(user.getFirstName());
			userObj.setLastName(user.getLastName());
		} else {
			userObj = user;
			if(requirement != null) {
				for (UserDailyRequirement req:requirement) {
					Product product = productDAO.getProduct(req.getProduct().getId());
					req.setProduct(product);
					req.setUser(userObj);
				}
			}
		}
		return userObj;
	}
	
	@Override
	public User validateUser(UserDTO userDTO) {
		User user = userDAO.getUserByUserId(userDTO.getUser().getUserId());
		if (user == null || (!user.getPassword().equals(userDTO.getUser().getPassword()))) {
			return null;
		}
		return user;
	}
	
	@Override
	public User getUser(UserDTO userDTO, boolean isUserId) {
		User user = isUserId ? (userDAO.getUserByUserId(userDTO.getUser().getUserId())) : (userDAO.getUserByMobile(userDTO.getUser().getMobile()));
		
		return user;
	}
	
	@Override
	public User forgotUserDetails(UserDTO userDTO) {
		User user = userDAO.getUserByMailId(userDTO.getUser().getMailId());
		if (user!= null) {
			SimpleMailMessage mailMessage= new SimpleMailMessage();
			mailMessage.setFrom("srikanthreddy.kethiri@gmail.com");
			mailMessage.setTo(user.getMailId());
			mailMessage.setSubject("Password for Milk Distribution App");
			mailMessage.setText("Your User Id and Password for the Milk Distribution App \n User Id :"+user.getUserId() + "\n Password:"+user.getPassword());
			mailSender.send(mailMessage);
		}
		return user;
	}
	
	@Override
	public UserDTO getVerifiedUsers(String areaId) {
		UserDTO userDTO = new UserDTO();
		Area area = areaDAO.getArea(areaId);
		List<User> users = userDAO.getActiveUsersByArea(area);
		userDTO.setUsers(users);
		return userDTO;
	}
	
	@Override
	public UserDTO getPendingUsers() {
		UserDTO userDTO = new UserDTO();
		List<User> users = userDAO.getPendingUsers();
		userDTO.setUsers(users);
		return userDTO;
	}

}
