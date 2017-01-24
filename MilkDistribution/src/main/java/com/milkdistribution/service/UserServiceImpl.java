package com.milkdistribution.service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public void createUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = userDTO.getUser();
		user = setEntities(user);
		userDAO.save(user);
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
			roasterDAO.delete(calendar.getTime(), user);
			roasterService.saveRoaster(user, calendar);
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
		
	}
	
	private User setEntities(User user) {
		if (user.getArea() != null) {
			Area area =areaDAO.getArea(user.getArea().getId());
			user.setArea(area);
		}
		Set<UserDailyRequirement> requirement = user.getRequirement();
		if(requirement != null) {
			for (UserDailyRequirement req:requirement) {
				Product product = productDAO.getProduct(req.getProduct().getId());
				req.setProduct(product);
			}
		}
		User userObj = null;
		if (user.getId() != null) {
			userObj = userDAO.getUser(user.getId());
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
		} else {
			userObj = user;
		}
		return userObj;
	}
	
	@Override
	public User validateUser(UserDTO userDTO) {
		User user = userDAO.getUserByUserId(userDTO.getUser().getUserId());
		return user;
	}
	
	@Override
	public User forgotUserDetails(UserDTO userDTO) {
		User user = userDAO.getUserByMailId(userDTO.getUser().getMailId());
		return user;
	}
	
	@Override
	public UserDTO getVerifiedUsers() {
		UserDTO userDTO = new UserDTO();
		List<User> users = userDAO.getActiveUsers();
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
