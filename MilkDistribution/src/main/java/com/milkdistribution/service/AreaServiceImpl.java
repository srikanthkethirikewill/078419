package com.milkdistribution.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.milkdistribution.dao.AreaDAO;
import com.milkdistribution.entity.Area;

@Service("areaService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class AreaServiceImpl implements AreaService{
	
	@Autowired
	AreaDAO areaDAO;

	@Override
	public List<Area> getList() {
		// TODO Auto-generated method stub
		return areaDAO.list();
	}

	@Override
	public void createArea(Area area) {
		// TODO Auto-generated method stub
		areaDAO.save(area);
	}

	@Override
	public void updateArea(Area area) {
		// TODO Auto-generated method stub
		Area areaObj = areaDAO.getArea(area.getId());
		areaObj.setDescription(area.getDescription());
		areaDAO.save(areaObj);
	}

	@Override
	public void deleteArea(Area area) {
		// TODO Auto-generated method stub
		Area areaObj = areaDAO.getArea(area.getId());
		areaDAO.delete(areaObj);
	}

}
