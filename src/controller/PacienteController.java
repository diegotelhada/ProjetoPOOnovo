package controller;

import java.util.Date;

import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
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

	public void adicionar(TableColumn<Paciente, Integer> tableColumnId, TableColumn<Paciente, String> tableColumnNome,
			TableColumn<Paciente, Date> tableColumnData, TableView<Paciente> tableViewPaciente,  
			TableColumn<Paciente, Paciente> tableColumnEDIT, TableColumn<Paciente, Paciente> tableColumnREMOVE ) {
		initTable(tableColumnId, tableColumnNome, tableColumnData, tableViewPaciente,
				tableColumnEDIT, tableColumnREMOVE);
	}

	public void novo(Stage stage) {
		try {
			form.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initTable(TableColumn<Paciente, Integer> tableColumnId, TableColumn<Paciente, String> tableColumnNome,
			TableColumn<Paciente, Date> tableColumnData, TableView<Paciente> tableViewPaciente,
			TableColumn<Paciente, Paciente> tableColumnEDIT, TableColumn<Paciente, Paciente> tableColumnREMOVE ) {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("IdPaciente"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dataAniversario"));
		tableViewPaciente.setItems(uptadeTable());

		initEditButtons(tableColumnEDIT);
		initRemoveButtons(tableColumnREMOVE);
	}

	private ObservableList<Paciente> uptadeTable() {

		return FXCollections.observableArrayList(service.findAll());
	}

	// ESSE METODO CRIA OS BOTÕES DE EDITAR NO PAINEL
	private void initEditButtons(TableColumn<Paciente, Paciente> tableColumnEDIT) {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Paciente, Paciente>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Paciente obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
//				button.setOnAction(event -> createDialogForm(obj, "/gui/PacienteForm.fxml", Utils.currentStage(event)));
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
//				button.setOnAction(event -> removeEntity(obj));
			}

		});
	}
}
