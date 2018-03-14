package com.excilys.formation.java.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.java.model.Company;

public class CompanyMapper {

	private static CompanyMapper _interface = null;
	
	private CompanyMapper() {
		
	}
	
	public static CompanyMapper getInterface() {
		if(_interface == null) {
			_interface = new CompanyMapper();
		}
		return _interface;
	}
	
	public static Company map(ResultSet res) {
		Company cpn = null;		
		try {
			if(!res.equals(null)) {
				int id = res.getInt("ID");
				String name = res.getString("NAME");
				cpn = new Company(id, name);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cpn;
	}
}
