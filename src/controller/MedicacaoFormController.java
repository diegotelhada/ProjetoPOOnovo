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
import model.entities.Medicacao;
import model.exceptions.ValidationException;
import model.services.MedicacaoService;

public class MedicacaoFormController {
	private Medicacao entidade = new Medicacao();

	private MedicacaoService service = new MedicacaoService();
	
	public void salvarMedic(TextField txtNome, TextField txtId, TextField vlMedic, TextField qtMedic, DatePicker dpValidade) {
		
		if (entidade == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entidade = getFormData(txtNome, txtId, vlMedic, qtMedic, dpValidade);
			service.saveOrUptade(entidade);

		}catch (DbException e) {
		
			Alerts.showAlert("Error savings Object", null, e.getMessage(), AlertType.ERROR);
		}
		
	}

	private Medicacao getFormData(TextField txtNome, TextField txtId, TextField vlMedic, TextField qtMedic, DatePicker dpValidade) {
		Medicacao obj = new Medicacao();

		ValidationException exception = new ValidationException("Validation Errors");

		obj.setIdMedicacao(Utils.tryParseToInt(txtId.getText()));

		obj.setNomeMedicacao(txtNome.getText());
		
		obj.setValorMedicacao(Float.parseFloat(vlMedic.getText()));
		
		obj.setQuantidadeMedicacao(Utils.tryParseToInt(qtMedic.getText()));

		
		if(txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("NomeMedic", "Fields can't be empty");
		}
		
		if(vlMedic.getText() == null || vlMedic.getText().trim().equals("")) {
			exception.addError("vlMedic", "Fields can't be empty");}
		
		if(qtMedic.getText() == null || qtMedic.getText().trim().equals("")) {
			exception.addError("qtMedic", "Fields can't be empty");}
	
		if (dpValidade.getValue() == null) {
			exception.addError("DataVal", "Fields can't be empty");
		}else {
			Instant instant = Instant.from(dpValidade.getValue().atStartOfDay(ZoneId.systemDefault()));
		
			obj.setDataValidade(Date.from(instant));
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
	}
}
