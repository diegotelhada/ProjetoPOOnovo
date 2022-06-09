package view;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import controller.PacienteFormController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.entities.Paciente;

public class PacienteForm extends Application {
	private TextField txtNome = new TextField();
	private TextField txtId = new TextField();
	private DatePicker dateNasc = new DatePicker();
	private Paciente paciente;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private Button btSalvar = new Button("Salvar");
	private Button btnCancelar = new Button("Cancelar");

	private PacienteFormController formControl = new PacienteFormController();

	public PacienteForm() {
	}

	public PacienteForm(Paciente paciente) {
		this.paciente = paciente;
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
		pane.add(txtNome, 1, 1);
		pane.add(new Label("Nascimento"), 0, 2);
		pane.add(dateNasc, 1, 2);

		pane.add(btSalvar, 0, 4);
		pane.add(btnCancelar, 1, 4);


		if (paciente != null) {

			txtNome.setText(paciente.getNomePaciente());
			txtId.setText(paciente.getIdPaciente() + "");
			if (paciente.getDataAniversario() != null) {
				dateNasc.setValue(LocalDate.parse(sdf.format(paciente.getDataAniversario()).toString()));
			}
			btSalvar.setText("Atualizar");
			btSalvar.setOnAction((e) -> {
				if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
					txtNome.setStyle("-fx-border-color: red;");
				} else if (dateNasc.getValue() == null) {
					dateNasc.setStyle("-fx-border-color: red;");
				} else {
					formControl.salvar(txtNome, txtId, dateNasc);
					PacienteView view = new PacienteView();
					try {
						view.start(stage);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		} else {
			btSalvar.setOnAction((e) -> {
				if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
					txtNome.setStyle("-fx-border-color: red;");
				} else if (dateNasc.getValue() == null) {
					dateNasc.setStyle("-fx-border-color: red;");
				} else {
					formControl.salvar(txtNome, txtId, dateNasc);
					PacienteView view = new PacienteView();
					try {
						view.start(stage);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}

		btnCancelar.setOnAction((e) -> {
			PacienteView view = new PacienteView();
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
