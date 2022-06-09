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
import model.entities.Equipamento;
import model.services.EquipamentoService;
import view.EquipamentoForm;
import view.EquipamentoView;

public class EquipamentoController {
	private EquipamentoService service = new EquipamentoService();
	private EquipamentoForm form = new EquipamentoForm();
	private Stage stage;
	
	public EquipamentoController() {
		// TODO Auto-generated constructor stub
	}
	public EquipamentoController(Stage stage) {
		this.stage = stage;
	}

	public void atualizarEquip(TableColumn<Equipamento, Integer> tableColumnId, TableColumn<Equipamento, String> tableColumnNome,
			TableColumn<Equipamento, Float> tableColumnValor, TableColumn<Equipamento, Date> tableColumnData,  TableView<Equipamento> tableViewEquipamento,  
			TableColumn<Equipamento, Equipamento> tableColumnEDIT, TableColumn<Equipamento, Equipamento> tableColumnREMOVE,
			String nomeEquip) {
		initTableEquip(tableColumnId, tableColumnNome, tableColumnValor, tableColumnData, tableViewEquipamento,
				tableColumnEDIT, tableColumnREMOVE, nomeEquip);
	}

	public void novo() {
		try {
			form.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initTableEquip(TableColumn<Equipamento, Integer> tableColumnId, TableColumn<Equipamento, String> tableColumnNome,
			TableColumn<Equipamento, Float> tableColumnValor, TableColumn<Equipamento, Date> tableColumnData, TableView<Equipamento> tableViewEquipamento,
			TableColumn<Equipamento, Equipamento> tableColumnEDIT, TableColumn<Equipamento, Equipamento> tableColumnREMOVE 
			, String nomeEquip) {
		initEditButtons(tableColumnEDIT);
		initRemoveButtons(tableColumnREMOVE);
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("IdEquipamento"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("NomeEquipamento"));
		tableColumnValor.setCellValueFactory(new PropertyValueFactory<>("ValorEquipamento"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("DataFabricacao"));
		Utils.formatTableColumnDate(tableColumnData, "dd/MM/yyyy");
		tableViewEquipamento.setItems(uptadeTable(nomeEquip));

		
	}

	private ObservableList<Equipamento> uptadeTable(String nomeEquip) {
		if(nomeEquip == "") {
			return FXCollections.observableArrayList(service.findAll());
		}
		
		return FXCollections.observableArrayList(service.findByNome(nomeEquip));
	}

	// ESSE METODO CRIA OS BOTOES DE EDITAR NO PAINEL
	private void initEditButtons(TableColumn<Equipamento, Equipamento> tableColumnEDIT) {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Equipamento, Equipamento>() {
			private final Button button = new Button("Editar");
			
			@Override
			protected void updateItem(Equipamento obj, boolean empty) {
				super.updateItem(obj, empty);
				EquipamentoForm form = new EquipamentoForm(obj);
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

	// ESSE METODO CRIA OS BOTOES DE REMOVER NO PAINEL
	private void initRemoveButtons(TableColumn<Equipamento, Equipamento> tableColumnREMOVE) {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Equipamento, Equipamento>() {
			private final Button button = new Button("Delete");

			@Override
			protected void updateItem(Equipamento obj, boolean empty) {
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
	
	private void removeEntity(Equipamento obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				try {
					EquipamentoView view = new EquipamentoView();
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
