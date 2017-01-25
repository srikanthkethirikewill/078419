package com.milkdistribution.service;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.milkdistribution.dao.ProductDAO;
import com.milkdistribution.dao.RoasterDAO;
import com.milkdistribution.dao.UserDAO;
import com.milkdistribution.dto.RoasterDTO;
import com.milkdistribution.entity.Roaster;
import com.milkdistribution.entity.RoasterDetail;
import com.milkdistribution.entity.User;
import com.milkdistribution.entity.UserDailyRequirement;

@Service("roasterService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class RoasterServiceImpl implements RoasterService{
	
	@Autowired
	RoasterDAO roasterDAO;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	ProductDAO productDAO;

	@Override
	public void generateRoaster() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		String fileName = "Roaster.xls";
		List<Roaster> roasterList = roasterDAO.list(calendar.getTime());
		HSSFWorkbook book = new HSSFWorkbook();
		String previousArea = null;
		HSSFSheet sheet = null;
		short count=0;
		for (Roaster roaster:roasterList) {
			String area = roaster.getArea().getDescription();
			if (!area.equals(previousArea)) {
				sheet = book.createSheet(area);
				HSSFRow rowhead = sheet.createRow((short)0);
	            rowhead.createCell(0).setCellValue("Address");
	            rowhead.createCell(1).setCellValue("User");
	            rowhead.createCell(2).setCellValue("Mobile");
	            rowhead.createCell(3).setCellValue("Product");
	            rowhead.createCell(4).setCellValue("Qty");
	            count=1;
			}
			HSSFRow row = sheet.createRow(count);
            row.createCell(0).setCellValue(roaster.getUser().getAddress());
            row.createCell(1).setCellValue(roaster.getUser().getUserId());
            row.createCell(2).setCellValue(roaster.getUser().getMobile());
            row.createCell(3).setCellValue("");
            row.createCell(4).setCellValue("");
            count++;
            Set<RoasterDetail> roasterDetails = roaster.getRoasterDetails();
            for (RoasterDetail detail:roasterDetails) {
            	HSSFRow detailrow = sheet.createRow(count);
            	detailrow.createCell(0).setCellValue("");
            	detailrow.createCell(1).setCellValue("");
            	detailrow.createCell(2).setCellValue("");
            	detailrow.createCell(3).setCellValue(detail.getProduct().getDescription());
            	detailrow.createCell(4).setCellValue(detail.getQty());
                count++;
            }
		}
		FileOutputStream fileOut = new FileOutputStream(fileName);
        book.write(fileOut);
        fileOut.close();
		List<User> users = userDAO.getUsers(true);
		MimeMessage[] messages = new MimeMessage[users.size()];
		count=0;
		FileSystemResource file = new FileSystemResource("Roaster.xls");
		for(User user:users) {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("srikanthreddy.kethiri@gmail.com");
			helper.setTo(user.getMailId());
			helper.setSubject("Daily Roaster");
			helper.setText("");
			helper.addAttachment(file.getFilename(), file);
			messages[count] = message;
			count++;
		}
		mailSender.send(messages);
		file.getFile().delete();
	}
	
	@Override
	public List<Roaster> getMonthlyRoaster(RoasterDTO roasterDTO) {
		User user = userDAO.getUser(roasterDTO.getUser().getId());
		return roasterDAO.getMonthlyRoaster(roasterDTO.getMonth(), roasterDTO.getYear(), user);
	}
	
	@Override
	public void saveRoaster(User user) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+2);
		int month = calendar.get(Calendar.MONTH);
		int m = month;
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		while (m==month) {
			Roaster roaster = new Roaster();
			roaster.setArea(user.getArea());
			roaster.setDate(calendar.getTime());
			roaster.setStatus("A");
			roaster.setUser(user);
			double price =0;
			Set<RoasterDetail> roasterDetails = new HashSet<RoasterDetail>();
			Set<UserDailyRequirement> requirements = user.getRequirement();
			for (UserDailyRequirement requirement: requirements) {
				RoasterDetail detail = new RoasterDetail();
				detail.setProduct(requirement.getProduct());
				detail.setQty(requirement.getQty());
				double productPrice = requirement.getProduct().getPrice();
				detail.setRate(productPrice);
				price = price + productPrice;
				roasterDetails.add(detail);
			}
			roaster.setAmount(price);
			roasterDAO.save(roaster);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
			m = calendar.get(Calendar.MONTH);
		}
	}
	
	@Override
	public void updateRoaster(RoasterDTO roasterDTO) {
		String fromDate = roasterDTO.getFromDate();
		String toDate = roasterDTO.getToDate();
		String[] fromDateArray = fromDate.split("/");
		String[] toDateArray = toDate.split("/");
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.set(Calendar.DATE, Integer.parseInt(fromDateArray[0]));
		fromCalendar.set(Calendar.MONTH, Integer.parseInt(fromDateArray[1]));
		fromCalendar.set(Calendar.YEAR, Integer.parseInt(fromDateArray[2]));
		fromCalendar.set(Calendar.HOUR, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.set(Calendar.DATE, Integer.parseInt(toDateArray[0]));
		toCalendar.set(Calendar.MONTH, Integer.parseInt(toDateArray[1]));
		toCalendar.set(Calendar.YEAR, Integer.parseInt(toDateArray[2]));
		toCalendar.set(Calendar.HOUR, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);
		User user = userDAO.getUser(roasterDTO.getUser().getId());
		roasterDAO.deleteByRange(fromCalendar.getTime(), toCalendar.getTime(), user);
		Set<RoasterDetail> roasterDetails = roasterDTO.getRoasterDetails();
		for(RoasterDetail detail:roasterDetails) {
			detail.setProduct(productDAO.getProduct(detail.getProduct().getId()));
		}
		while (toCalendar.compareTo(fromCalendar) >= 0 ) {
			Roaster roaster = new Roaster();
			roaster.setArea(user.getArea());
			roaster.setDate(fromCalendar.getTime());
			roaster.setStatus("A");
			roaster.setUser(user);
			roaster.setRoasterDetails(roasterDetails);
			roasterDAO.save(roaster);
			fromCalendar.set(Calendar.DATE, fromCalendar.get(Calendar.DATE)+1);
		}
	}
	
	@Override
	public void saveRoaster(User user,Calendar calendar) {
		int month = calendar.get(Calendar.MONTH);
		int m = month;		
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Calendar current = Calendar.getInstance();
		month = current.get(Calendar.MONTH)+2;
		while (m<month) {
			Roaster roaster = new Roaster();
			roaster.setArea(user.getArea());
			roaster.setDate(calendar.getTime());
			roaster.setStatus("A");
			roaster.setUser(user);
			double price =0;
			Set<RoasterDetail> roasterDetails = new HashSet<RoasterDetail>();
			Set<UserDailyRequirement> requirements = user.getRequirement();
			for (UserDailyRequirement requirement: requirements) {
				RoasterDetail detail = new RoasterDetail();
				detail.setProduct(requirement.getProduct());
				detail.setQty(requirement.getQty());
				double productPrice = requirement.getProduct().getPrice();
				detail.setRate(productPrice);
				price = price + productPrice;
				roasterDetails.add(detail);
			}
			roaster.setAmount(price);
			roasterDAO.save(roaster);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
			m = calendar.get(Calendar.MONTH);
		}
	}

}
