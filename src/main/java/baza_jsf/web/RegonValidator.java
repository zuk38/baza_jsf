package baza_jsf.web;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("regonValidator")
public class RegonValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		
		String regon = (String) value;
		
		if (regon.length() != 4) {
			FacesMessage message = new FacesMessage();
			message.setDetail("REGON musi składać się z 5 cyfr");
			message.setSummary("REGON musi składać się z 4 cyfr");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
}
