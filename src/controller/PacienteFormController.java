package controller;

import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;

import db.DbException;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.entities.Paciente;
import model.exceptions.ValidationException;
import model.services.PacienteService;

public class PacienteFormController {
	private Paciente entidade = new Paciente();

	private PacienteService service = new PacienteService();
	
	public void salvar(TextField txtNome, TextField txtId, DatePicker dpNasc) {
		
		if (entidade == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entidade = getFormData(txtNome, txtId, dpNasc);
			service.saveOrUptade(entidade);

		}catch (DbException e) {
		
			Alerts.showAlert("Error savings Object", null, e.getMessage(), AlertType.ERROR);
		}
		
	}

	private Paciente getFormData(TextField txtNome, TextField txtId, DatePicker dpNasc) {
		Paciente obj = new Paciente();

		ValidationException exception = new ValidationException("Validation Errors");

		obj.setIdPaciente(Utils.tryParseToInt(txtId.getText()));

		obj.setNomePaciente(txtNome.getText());
		
		if(txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "Fields can't be emply");
		}
		
		if (dpNasc.getValue() == null) {
			exception.addError("dataAniv", "Fields can't be emply");
		}else {
			Instant instant = Instant.from(dpNasc.getValue().atStartOfDay(ZoneId.systemDefault()));
		
			obj.setDataAniversario(Date.from(instant));
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
	}
}
