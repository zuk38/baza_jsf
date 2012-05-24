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
		newCompany.setRegon(company.getRegon());
		newCompany.setDateOfBirth(company.getDateOfBirth());
		newCompany.setMarried(company.isMarried());
		newCompany.setWeight(company.getWeight());
		newCompany.setNumOfChildren(company.getNumOfChildren());

		db.add(newCompany);
	}

	// Removes the company with given REGON
	public void deleteCompany(Company company) {
		Company companyToRemove = null;
		for (Company p : db) {
			if (company.getRegon().equals(p.getRegon())) {
				companyToRemove = p;
				break;
			}
		}
		if (companyToRemove != null)
			db.remove(companyToRemove);
	}

	public List<Company> getAllCompanys() {
		return db;
	}
}
