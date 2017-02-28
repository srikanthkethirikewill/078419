package com.milkdistribution.service;

import com.milkdistribution.dto.BillingDTO;
import com.milkdistribution.entity.Billing;

public interface BillingService {
	
	void performBilling();
	
	void getBillingList(BillingDTO billingDTO);
	
	void updateBilling(Billing billing);

	Billing getCurrentMonthBilling(BillingDTO billingDTO);
}
