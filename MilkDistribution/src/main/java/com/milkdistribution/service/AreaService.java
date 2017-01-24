package com.milkdistribution.service;

import java.util.List;

import com.milkdistribution.entity.Area;

public interface AreaService {
	
	List<Area> getList();
	
	void createArea(Area area);
	
	void updateArea(Area area);
	
	void deleteArea(Area area);

}
