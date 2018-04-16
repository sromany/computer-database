package com.excilys.formation.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;

public enum WebServiceCompany {
	
	INSTANCE;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WebServiceCompany.class);
	private static final String WEBSERVICE_COMPANY_EXCEPTION = "WebServiceCompany: %s, from %s";
	private static final String WEBSERVICE_COMPANY_LOGGER = "WebServiceCompany: {}";

	private WebServiceCompany() {
		
	}

	public int getNumberOf() throws ServiceManagerException {
		CompanyDB cpyDB = CompanyDB.INSTANCE;
		try {
			return cpyDB.getNumCompanies();			
		}catch(DAOException e) {
			logger.error("WebServiceCompany: {}", e.getMessage(), e);
			throw new ServiceManagerException("WebServiceCompany: " + e.getMessage(), e);
		}
	}

	public List<Company> getAllList() throws ServiceManagerException {
		CompanyDB cmpDB = CompanyDB.INSTANCE;
		try {
			return cmpDB.getCompanyList();			
		}catch(DAOException e) {
			logger.error("WebServiceCompany: {}", e.getMessage(), e);
			throw new ServiceManagerException("WebServiceCompany: " + e.getMessage(), e);
		}
	}
	
	public List<Company> getList(int limit, int offset) throws ServiceManagerException{
		CompanyDB cpnDB = CompanyDB.INSTANCE;
		try {
			if(limit == 0 && offset == 0) {
				return cpnDB.getCompanyList();
			}
			return cpnDB.getCompanyList(limit, offset);			
		}catch (DAOException e) {
			logger.error("WebServiceCompany: {}", e.getMessage(), e);
			throw new ServiceManagerException("WebServiceCompany: " + e.getMessage(), e);
		}
	}
	
	public Company getCompany(String id) throws ServiceManagerException {
		CompanyDB cpyDB = CompanyDB.INSTANCE;
		Optional<Company> optCompany = Optional.empty();
		try {
			optCompany = cpyDB.getCompanyByID(Integer.valueOf(id));
		}catch(NumberFormatException|DAOException e) {
			logger.error("WebServiceCompany: {}", e.getMessage(), e);
			throw new ServiceManagerException("WebServiceCompany: " + e.getMessage(), e);
		}
		return optCompany.get();
	}
	
	
	public void deleteCompany(String id) throws ServiceManagerException {
		CompanyDB cpyDB = CompanyDB.INSTANCE;
		Company cpy = null;
		try {
			cpy  = getCompany(id);
			cpyDB.delete(cpy);
		} catch (DAOException e) {
			logger.error(WEBSERVICE_COMPANY_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPANY_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}

	
	
}
