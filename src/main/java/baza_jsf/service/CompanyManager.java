package baza_jsf.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import baza_jsf.domain.Company;

@ApplicationScoped
public class CompanyManager {
	private List<Company> db = new ArrayList<Company>();

	public void addCompany(Company company) {
		Company newCompany = new Company();

		newCompany.setFirstName(company.getFirstName());
		newCompany.setZipCode(company.getZipCode());
		newCompany.setNip(company.getNip());
		newCompany.setDateOfBirth(company.getDateOfBirth());
		newCompany.setMarried(company.isMarried());
		newCompany.setEmploy(company.getEmploy());
		newCompany.setNumOfChildren(company.getNumOfChildren());

		db.add(newCompany);
	}

	// Removes the company with given NIP
	public void deleteCompany(Company company) {
		Company companyToRemove = null;
		for (Company p : db) {
			if (company.getNip().equals(p.getNip())) {
				companyToRemove = p;
				break;
			}
		}
		if (companyToRemove != null)
			db.remove(companyToRemove);
	}
	
	public List<Company> editCompany(Company c) {
		List<Company> cl = new ArrayList<Company>();
		for (Company p : db) {
			if (c.getNip().equals(p.getNip())) {
			cl.add(p);
			break;
			}
		}
		return cl;
		
	}
	public List<Company> searchCompany(Company c) {
		List<Company> cl = new ArrayList<Company>();
		for (Company p : db) {
			if (c.getNip().equals(p.getNip()) || c.getFirstName().equals(p.getFirstName())) {
			cl.add(p);
			}
		}
		return cl;
	}
	public List<Company> getAllCompanies() {
		return db;
	}
}
