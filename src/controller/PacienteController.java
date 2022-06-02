package controller;

import java.util.Date;
import java.util.Optional;

import db.DbException;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Paciente;
import model.services.PacienteService;
import view.PacienteForm;
import view.PacienteView;

public class PacienteController {
	private PacienteService service = new PacienteService();
	private PacienteForm form = new PacienteForm();
	private Stage stage;
	
	public PacienteController() {
		// TODO Auto-generated constructor stub
	}
	public PacienteController(Stage stage) {
		this.stage = stage;
	}

	public void atualizar(TableColumn<Paciente, Integer> tableColumnId, TableColumn<Paciente, String> tableColumnNome,
			TableColumn<Paciente, Date> tableColumnData, TableView<Paciente> tableViewPaciente,  
			TableColumn<Paciente, Paciente> tableColumnEDIT, TableColumn<Paciente, Paciente> tableColumnREMOVE,
			String nome) {
		initTable(tableColumnId, tableColumnNome, tableColumnData, tableViewPaciente,
				tableColumnEDIT, tableColumnREMOVE, nome);
	}

	public void novo() {
		try {
			form.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initTable(TableColumn<Paciente, Integer> tableColumnId, TableColumn<Paciente, String> tableColumnNome,
			TableColumn<Paciente, Date> tableColumnData, TableView<Paciente> tableViewPaciente,
			TableColumn<Paciente, Paciente> tableColumnEDIT, TableColumn<Paciente, Paciente> tableColumnREMOVE 
			, String nome) {
		initEditButtons(tableColumnEDIT);
		initRemoveButtons(tableColumnREMOVE);
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("IdPaciente"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dataAniversario"));
		Utils.formatTableColumnDate(tableColumnData, "dd/MM/yyyy");
		tableViewPaciente.setItems(uptadeTable(nome));

		
	}

	private ObservableList<Paciente> uptadeTable(String nome) {
		if(nome == "") {
			return FXCollections.observableArrayList(service.findAll());
		}
		
		return FXCollections.observableArrayList(service.findByNome(nome));
	}

	// ESSE METODO CRIA OS BOTÕES DE EDITAR NO PAINEL
	private void initEditButtons(TableColumn<Paciente, Paciente> tableColumnEDIT) {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Paciente, Paciente>() {
			private final Button button = new Button("Editar");
			
			@Override
			protected void updateItem(Paciente obj, boolean empty) {
				super.updateItem(obj, empty);
				PacienteForm form = new PacienteForm(obj);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction((e) -> { try {
					form.start(stage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}});
			}
		});
	}

	// ESSE METODO CRIA OS BOTÕES DE REMOVER NO PAINEL
	private void initRemoveButtons(TableColumn<Paciente, Paciente> tableColumnREMOVE) {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Paciente, Paciente>() {
			private final Button button = new Button("Delete");

			@Override
			protected void updateItem(Paciente obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}

		});
	}
	
	private void removeEntity(Paciente obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				try {
					PacienteView view = new PacienteView();
					view.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}catch (DbException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
}
