package com.excilys.formation.cdb.pages;

import java.util.List;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.service.WebServiceComputer;

public class PagesComputer<T> extends Pages<T> {

	public PagesComputer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PagesComputer(List<T> page) {
		super(page);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public void next() throws ServiceManagerException {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		int max = webcmp.getNumberOf();
		pageIndex += PAGE_STRIDE;
		if( (max - pageIndex) < PAGE_STRIDE) {
			pageIndex = max - PAGE_STRIDE;
		} else {
			num++;
		}
		this.page = (List<T>) webcmp.getList(pageIndex, pageIndex+PAGE_STRIDE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void preview() throws ServiceManagerException  {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		pageIndex -= PAGE_STRIDE;
		if(pageIndex < 0) {
			pageIndex = 0;
		}else {
			num--;
		}
		this.page = (List<T>) webcmp.getList(pageIndex, pageIndex+PAGE_STRIDE);
	}
	
	
}
