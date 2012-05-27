package baza_jsf.web;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import baza_jsf.domain.Company;
import baza_jsf.service.CompanyManager;

@ManagedBean
@SessionScoped
@Named("companyBean")
public class CompanyFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Company Company = new Company();
	private Company CompanyToEdit = new Company();
	
	private ListDataModel<Company> Companies = new ListDataModel<Company>();
	
	private String searchText = null;
	
	public String getSearchText(){
		return searchText; 
	}
	
	public void setSearchText(String s){
		searchText = s;
	}
	

	@Inject
	private CompanyManager pm;

	public Company getCompany() {
		return Company;
	}

	public void setCompany(Company Company) {
		this.Company = Company;
	}

	public ListDataModel<Company> getAllCompanies() {
		Companies.setWrappedData(pm.getAllCompanies());
		return Companies;
	}
	
	public ListDataModel<Company> getAllCompanies2() {
		Companies.setWrappedData(pm.editCompany(CompanyToEdit));
		return Companies;
	}
	
	public ListDataModel<Company> getCompanySearch() {
		Companies.setWrappedData(pm.searchCompany(CompanyToEdit));
		return Companies;
	}
	
	public ListDataModel<Company> getCompanyUpdate() {
		Companies.setWrappedData(pm.searchCompany(Company));
		return Companies;
	}
	
	public boolean getAllCompanies3() {
		if (Companies.getRowCount()==0) return true;
		else return false;
	}

	// Actions
	public String addCompany() {
		pm.addCompany(Company);
		return "showDetalis";
		//return null;
	}
	
	public String showCompany() {
		return "showCompany";
		//return null;
	}
	public String Back() {
		pm.deleteCompany(Company);
		return "addSimple";
		//return null;
	}	
	public String editCompany(){
		CompanyToEdit = Companies.getRowData();
		return "editSimple";	
	}
	
	public String deleteCompany() {
		Company CompanyToDelete = Companies.getRowData();
		pm.deleteCompany(CompanyToDelete);
		return null;
	}

	public String searchCompany() {
		  CompanyToEdit.setNip(searchText);
		  CompanyToEdit.setFirstName(searchText);
		  return "searchResult";
		 }
	public String searchCompany2() {
		  CompanyToEdit.setNip(searchText);
		  CompanyToEdit.setFirstName(searchText);
		  return null;
		 }
	
	public String updateCompany(){
		Company = new Company();
		return "showCompany";
	}

	// Validators

	// Business logic validation
	public void uniqueNIP(FacesContext context, UIComponent component,
			Object value) {

		String nip = (String) value;

		for (Company Company : pm.getAllCompanies()) {
			if (Company.getNip().equalsIgnoreCase(nip)) {
				FacesMessage message = new FacesMessage(
						"Company with this NIP already exists in database");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}
	}

	// Multi field validation with <f:event>
	// Rule: first two digits of NIP must match last two digits of the year of
	// birth
	public void validateNIPDob(ComponentSystemEvent event) {

		UIForm form = (UIForm) event.getComponent();
		UIInput nip = (UIInput) form.findComponent("nip");
		UIInput dob = (UIInput) form.findComponent("dob");

		if (nip.getValue() != null && dob.getValue() != null
				&& nip.getValue().toString().length() >= 2) {
			String twoDigitsOfPin = nip.getValue().toString().substring(0, 2);
			Calendar cal = Calendar.getInstance();
			cal.setTime(((Date) dob.getValue()));

			String lastDigitsOfDob = ((Integer) cal.get(Calendar.YEAR))
					.toString().substring(2);

			if (!twoDigitsOfPin.equals(lastDigitsOfDob)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(form.getClientId(), new FacesMessage(
						"PIN doesn't match date of birth"));
				context.renderResponse();
			}
		}
	}
}
