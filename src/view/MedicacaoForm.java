package view;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import controller.MedicacaoFormController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.entities.Medicacao;

public class MedicacaoForm extends Application {
	private TextField txtNomeMedic = new TextField();
	private TextField txtId = new TextField();
	private TextField txtvlMedic = new TextField();
	private TextField txtQtMedic = new TextField();
	private DatePicker dataVal = new DatePicker();
	private Medicacao medicacao;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private Button btSalvar = new Button("Salvar");
	private Button btnCancelar = new Button("Cancelar");

	private MedicacaoFormController formControl = new MedicacaoFormController();

	public MedicacaoForm() {
	}

	public MedicacaoForm(Medicacao medicacao) {
		this.medicacao = medicacao;
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
		pane.add(txtNomeMedic, 1, 1);
		pane.add(new Label("Validade"), 0, 2);
		pane.add(dataVal, 1, 2);
		pane.add(new Label ("Valor"), 0, 3);
		pane.add(txtvlMedic, 1, 3);
		pane.add(new Label ("Quantidade"), 0, 4);
		pane.add(txtQtMedic, 1, 4);
		
		pane.add(btSalvar, 0, 5);
		pane.add(btnCancelar, 1, 5);


		if (medicacao != null) {

			txtNomeMedic.setText(medicacao.getNomeMedicacao());
			txtId.setText(medicacao.getIdMedicacao() + "");
			if (medicacao.getDataValidade() != null) {
				dataVal.setValue(LocalDate.parse(sdf.format(medicacao.getDataValidade()).toString()));
			}
			
			
			if (medicacao.getValorMedicacao() < 0) {
				txtvlMedic.setText("" + medicacao.getValorMedicacao());
							} else txtvlMedic.setStyle("-fx-border-color: red;");
			
			if (medicacao.getQuantidadeMedicacao() < 0) {
				txtvlMedic.setText("" + medicacao.getQuantidadeMedicacao());
							} else txtvlMedic.setStyle("-fx-border-color: red;");
			
			btSalvar.setText("Atualizar");
			btSalvar.setOnAction((e) -> {
				if (txtNomeMedic.getText() == null || txtNomeMedic.getText().trim().equals("")) {
					txtNomeMedic.setStyle("-fx-border-color: red;");
				} else if (dataVal.getValue() == null) {
					dataVal.setStyle("-fx-border-color: red;");
				} else {
					formControl.salvarMedic(txtNomeMedic, txtId, txtvlMedic, txtQtMedic, dataVal);
					MedicacaoView view = new MedicacaoView();
					try {
						view.start(stage);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			
		} else {
			btSalvar.setOnAction((e) -> {
				if (txtNomeMedic.getText() == null || txtNomeMedic.getText().trim().equals("")) {
					txtNomeMedic.setStyle("-fx-border-color: red;");
				} else if (dataVal.getValue() == null) {
					dataVal.setStyle("-fx-border-color: red;");
				} else {
					formControl.salvarMedic(txtNomeMedic, txtId, txtvlMedic, txtQtMedic, dataVal);
					MedicacaoView view = new MedicacaoView();
					try {
						view.start(stage);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}

		btnCancelar.setOnAction((e) -> {
			MedicacaoView view = new MedicacaoView();
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
