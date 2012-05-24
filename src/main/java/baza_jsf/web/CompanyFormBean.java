package baza_jsf.web;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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


@SessionScoped

public class CompanyFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Company Company = new Company();

	private ListDataModel<Company> Companys = new ListDataModel<Company>();

	@Inject
	private CompanyManager pm;

	public Company getCompany() {
		return Company;
	}

	public void setCompany(Company Company) {
		this.Company = Company;
	}

	public ListDataModel<Company> getAllCompanys() {
		Companys.setWrappedData(pm.getAllCompanys());
		return Companys;
	}

	// Actions
	public String addCompany() {
		pm.addCompany(Company);
		return "showDetalis";
		//return null;
	}
	public String showCompany() {
		return "showCompanys";
		//return null;
	}
	public String Back() {
		pm.deleteCompany(Company);
		return "addSimple";
		//return null;
	}
	

	public String deleteCompany() {
		Company CompanyToDelete = Companys.getRowData();
		pm.deleteCompany(CompanyToDelete);
		return null;
	}

	// Validators

	// Business logic validation
	public void uniqueRegon(FacesContext context, UIComponent component,
			Object value) {

		String regon = (String) value;

		for (Company Company : pm.getAllCompanys()) {
			if (Company.getRegon().equalsIgnoreCase(regon)) {
				FacesMessage message = new FacesMessage(
						"Company with this REGON already exists in database");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}
	}

	// Multi field validation with <f:event>
	// Rule: first two digits of REGON must match last two digits of the year of
	// birth
	public void validatePinDob(ComponentSystemEvent event) {

		UIForm form = (UIForm) event.getComponent();
		UIInput pin = (UIInput) form.findComponent("pin");
		UIInput dob = (UIInput) form.findComponent("dob");

		if (pin.getValue() != null && dob.getValue() != null
				&& pin.getValue().toString().length() >= 2) {
			String twoDigitsOfPin = pin.getValue().toString().substring(0, 2);
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
