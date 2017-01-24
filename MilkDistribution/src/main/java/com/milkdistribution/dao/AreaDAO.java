package com.milkdistribution.dao;

import java.util.List;

import com.milkdistribution.entity.Area;

public interface AreaDAO {
	
	void save(Area area);
	
	void delete(Area area);
	
	Area getArea(String id);
	
	List<Area> list();
	
}
