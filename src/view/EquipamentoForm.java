package view;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import controller.EquipamentoFormController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.entities.Equipamento;

public class EquipamentoForm extends Application {
	private TextField txtNomeEquip = new TextField();
	private TextField txtId = new TextField();
	private TextField txtvlEquip = new TextField();
	private DatePicker dataFab = new DatePicker();
	private Equipamento equipamento;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private Button btSalvar = new Button("Salvar");
	private Button btnCancelar = new Button("Cancelar");

	private EquipamentoFormController formControl = new EquipamentoFormController();

	public EquipamentoForm() {
	}

	public EquipamentoForm(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	@Override
	public void start(Stage stage) throws Exception {
		GridPane pane = new GridPane();
		Scene scn = new Scene(pane, 600, 400);
		pane.setMinHeight(6);

		pane.setVgap(20);
		pane.setHgap(20);
		pane.setAlignment(Pos.CENTER);
		pane.add(new Label("Nome"), 0, 1);
		pane.add(txtNomeEquip, 1, 1);
		pane.add(new Label("Fabricacao"), 0, 2);
		pane.add(dataFab, 1, 2);
		pane.add(new Label ("Valor"), 0, 3);
		pane.add(txtvlEquip, 1, 3);
		
		pane.add(btSalvar, 0, 4);
		pane.add(btnCancelar, 1, 4);


		if (equipamento != null) {

			txtNomeEquip.setText(equipamento.getNomeEquipamento());
			txtId.setText(equipamento.getIdEquipamento() + "");
			if (equipamento.getDataFabricacao() != null) {
				dataFab.setValue(LocalDate.parse(sdf.format(equipamento.getDataFabricacao()).toString()));
			}
			
			
			if (equipamento.getValorEquipamento() < 0) {
				txtvlEquip.setText("" + equipamento.getValorEquipamento());
;
							} else txtvlEquip.setStyle("-fx-border-color: red;");
			btSalvar.setText("Atualizar");
			btSalvar.setOnAction((e) -> {
				if (txtNomeEquip.getText() == null || txtNomeEquip.getText().trim().equals("")) {
					txtNomeEquip.setStyle("-fx-border-color: red;");
				} else if (dataFab.getValue() == null) {
					dataFab.setStyle("-fx-border-color: red;");
				} else {
					formControl.salvarEquip(txtNomeEquip, txtId, txtvlEquip, dataFab);
					EquipamentoView view = new EquipamentoView();
					try {
						view.start(stage);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			
		} else {
			btSalvar.setOnAction((e) -> {
				if (txtNomeEquip.getText() == null || txtNomeEquip.getText().trim().equals("")) {
					txtNomeEquip.setStyle("-fx-border-color: red;");
				} else if (dataFab.getValue() == null) {
					dataFab.setStyle("-fx-border-color: red;");
				} else {
					formControl.salvarEquip(txtNomeEquip, txtId, txtvlEquip, dataFab);
					EquipamentoView view = new EquipamentoView();
					try {
						view.start(stage);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}

		btnCancelar.setOnAction((e) -> {
			EquipamentoView view = new EquipamentoView();
			try {
				view.start(stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		stage.setTitle("Sistema clinica");
		stage.setScene(scn);
		stage.show();
	}
}
