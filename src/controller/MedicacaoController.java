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
import model.entities.Medicacao;
import model.services.MedicacaoService;
import view.MedicacaoForm;
import view.MedicacaoView;

public class MedicacaoController {
	private MedicacaoService service = new MedicacaoService();
	private MedicacaoForm form = new MedicacaoForm();
	private Stage stage;
	
	public MedicacaoController() {
		// TODO Auto-generated constructor stub
	}
	public MedicacaoController(Stage stage) {
		this.stage = stage;
	}

	public void atualizarMedic(
			TableColumn<Medicacao, Integer> tableColumnId,
			TableColumn<Medicacao, String> tableColumnNome,
			TableColumn<Medicacao, Float> tableColumnValor,
			TableColumn<Medicacao, Integer> tableColumnQt,
			TableColumn<Medicacao, Date> tableColumnData,
			TableView<Medicacao> tableViewMedicacao,
			TableColumn<Medicacao, Medicacao> tableColumnEDIT,
			TableColumn<Medicacao, Medicacao> tableColumnREMOVE,
			String nomeMedic) {
		initTableMedic(tableColumnId, tableColumnNome, tableColumnValor, tableColumnQt, tableColumnData, tableViewMedicacao,
				tableColumnEDIT, tableColumnREMOVE, nomeMedic);
	}
	

	public void novo() {
		try {
			form.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initTableMedic(
			TableColumn<Medicacao, Integer> tableColumnId,
			TableColumn<Medicacao, String> tableColumnNome,
			TableColumn<Medicacao, Float> tableColumnValor,
			TableColumn<Medicacao, Integer> tableColumnQt,
			TableColumn<Medicacao, Date> tableColumnData,
			TableView<Medicacao> tableViewMedicacao,
			TableColumn<Medicacao, Medicacao> tableColumnEDIT,
			TableColumn<Medicacao, Medicacao> tableColumnREMOVE, 
			String nomeMedic) {
		initEditButtons(tableColumnEDIT);
		initRemoveButtons(tableColumnREMOVE);
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("IdMedicacao"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("NomeMedicacao"));
		tableColumnValor.setCellValueFactory(new PropertyValueFactory<>("ValorMedicacao"));
		tableColumnQt.setCellValueFactory(new PropertyValueFactory<>("QuantidadeMedicacao"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("DataValidade"));
		Utils.formatTableColumnDate(tableColumnData, "dd/MM/yyyy");
		tableViewMedicacao.setItems(uptadeTable(nomeMedic));

		
	}

	private ObservableList<Medicacao> uptadeTable(String nomeMedic) {
		if(nomeMedic == "") {
			return FXCollections.observableArrayList(service.findAll());
		}
		
		return FXCollections.observableArrayList(service.findByNome(nomeMedic));
	}

	// ESSE METODO CRIA OS BOTOES DE EDITAR NO PAINEL
	private void initEditButtons(TableColumn<Medicacao, Medicacao> tableColumnEDIT) {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Medicacao, Medicacao>() {
			private final Button button = new Button("Editar");
			
			@Override
			protected void updateItem(Medicacao obj, boolean empty) {
				super.updateItem(obj, empty);
				MedicacaoForm form = new MedicacaoForm(obj);
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
	private void initRemoveButtons(TableColumn<Medicacao, Medicacao> tableColumnREMOVE) {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Medicacao, Medicacao>() {
			private final Button button = new Button("Delete");

			@Override
			protected void updateItem(Medicacao obj, boolean empty) {
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
	
	private void removeEntity(Medicacao obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				try {
					MedicacaoView view = new MedicacaoView();
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
