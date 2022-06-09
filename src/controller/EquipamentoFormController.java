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
import model.entities.Equipamento;
import model.exceptions.ValidationException;
import model.services.EquipamentoService;

public class EquipamentoFormController {
	private Equipamento entidade = new Equipamento();

	private EquipamentoService service = new EquipamentoService();
	
	public void salvarEquip(TextField txtNome, TextField txtId, TextField vlEquip, DatePicker dpNasc) {
		
		if (entidade == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entidade = getFormData(txtNome, txtId, vlEquip, dpNasc);
			service.saveOrUptade(entidade);

		}catch (DbException e) {
		
			Alerts.showAlert("Error savings Object", null, e.getMessage(), AlertType.ERROR);
		}
		
	}

	private Equipamento getFormData(TextField txtNome, TextField txtId, TextField vlEquip, DatePicker dpNasc) {
		Equipamento obj = new Equipamento();

		ValidationException exception = new ValidationException("Validation Errors");

		obj.setIdEquipamento(Utils.tryParseToInt(txtId.getText()));

		obj.setNomeEquipamento(txtNome.getText());
		
		obj.setValorEquipamento(Float.parseFloat(vlEquip.getText()));
		
		if(txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("NomeEquip", "Fields can't be empty");
		}
		
		if(vlEquip.getText() == null || vlEquip.getText().trim().equals("")) {
			exception.addError("NomeEquip", "Fields can't be empty");}
	
		if (dpNasc.getValue() == null) {
			exception.addError("DataFab", "Fields can't be empty");
		}else {
			Instant instant = Instant.from(dpNasc.getValue().atStartOfDay(ZoneId.systemDefault()));
		
			obj.setDataFabricacao(Date.from(instant));
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
	}
}
