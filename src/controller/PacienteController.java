package controller;

import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Paciente;
import model.services.PacienteService;
import view.PacienteForm;

public class PacienteController {
	private PacienteService service = new PacienteService();
	private PacienteForm form = new PacienteForm();
	
	public void adicionar(TableColumn<Paciente, Integer> tableColumnId,
			TableColumn<Paciente, String>   tableColumnNome,
			TableColumn<Paciente, Date> tableColumnData,
			TableView<Paciente> tableViewPaciente) {
		initTable(tableColumnId,tableColumnNome,tableColumnData,tableViewPaciente);
	}
	
	public void novo(Stage stage) {
		try {
			form.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void initTable(TableColumn<Paciente, Integer> tableColumnId,
			TableColumn<Paciente, String>   tableColumnNome,
			TableColumn<Paciente, Date> tableColumnData,
			TableView<Paciente> tableViewPaciente) {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("IdPaciente"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dataAniversario"));
		tableViewPaciente.setItems(uptadeTable());
	}

	private ObservableList<Paciente> uptadeTable() {
		
		return FXCollections.observableArrayList(service.findAll());
	}
}
