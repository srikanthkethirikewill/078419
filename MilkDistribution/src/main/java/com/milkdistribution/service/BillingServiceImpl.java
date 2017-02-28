package com.milkdistribution.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.milkdistribution.dao.BillingDAO;
import com.milkdistribution.dao.RoasterDAO;
import com.milkdistribution.dao.UserDAO;
import com.milkdistribution.dto.BillingDTO;
import com.milkdistribution.entity.Billing;
import com.milkdistribution.entity.User;

@Service("billingService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BillingServiceImpl implements BillingService {
	
	@Autowired
	BillingDAO billingDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	RoasterDAO roasterDAO;
	
	@Autowired
	RoasterService roasterService;

	@Override
	public void performBilling() {
		List<Billing> billingList = roasterDAO.prepareBilling();
		for(Billing billing:billingList) {
			billingDAO.save(billing);
			if (!"I".equals(billing.getUser().getStatus())) {
				roasterService.saveRoaster(billing.getUser());
			}
		}

	}
	
	public void getBillingList(BillingDTO billingDTO) {
		User user = userDAO.getUser(billingDTO.getUser().getId());
		List<Billing> billList = billingDAO.getBilling(user);
		billingDTO.setBillList(billList);
	}
	
	public void updateBilling(Billing billing) {
		Billing billingObj = billingDAO.getBilling(billing.getId());
		billingObj.setStatus((billing.getReceivedAmount() >= billing.getTotalAmount()) ? "C" : "I");
		billingObj.setReceivedAmount(billing.getReceivedAmount());
		billingDAO.save(billingObj);
	}
	
	public Billing getCurrentMonthBilling(BillingDTO billingDTO) {
		User user = userDAO.getUser(billingDTO.getUser().getId());
		Billing billing = billingDAO.getBilling(billingDTO.getMonth(),billingDTO.getYear(),user);
		return billing;
	}

}
