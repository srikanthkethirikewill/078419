package com.milkdistribution.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.milkdistribution.dao.BillingDAO;
import com.milkdistribution.dao.RoasterDAO;
import com.milkdistribution.dto.BillingDTO;
import com.milkdistribution.entity.Billing;

@Service("billingService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BillingServiceImpl implements BillingService {
	
	@Autowired
	BillingDAO billingDAO;
	
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
		List<Billing> billList = billingDAO.getBilling(billingDTO.getMonth(), billingDTO.getYear());
		billingDTO.setBillList(billList);
	}
	
	public void updateBilling(Billing billing) {
		Billing billingObj = billingDAO.getBilling(billing.getId());
		billingObj.setStatus("C");
		billingDAO.save(billingObj);
	}

}
