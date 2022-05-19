package view;

import controller.PacienteFormController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PacienteForm extends Application{
	private TextField txtNome = new TextField();
	private TextField txtId = new TextField();
	private DatePicker dateNasc = new DatePicker();

	private Button btSalvar = new Button("Salvar");
	private Button btnPesquisar = new Button("Pesquisar");
	
	private PacienteFormController form = new PacienteFormController();
	
	@Override
	public void start(Stage stage) throws Exception {
		GridPane pane = new GridPane();
		Scene scn = new Scene(pane, 500, 300);
		pane.setVgap(20);
		pane.setHgap(20);
		pane.add(new Label("Id"), 0, 0);
		txtId.setEditable(false);
        pane.add(txtId, 1, 0);
        pane.add(new Label("Nome"), 0, 1);
        pane.add(txtNome, 1, 1);
        pane.add(new Label("Nascimento"), 0, 2);
        pane.add(dateNasc, 1, 2);
        
        pane.add(btSalvar, 0, 4);
        pane.add(btnPesquisar, 1, 4);
        
        btSalvar.setOnAction((e) -> {
        	form.salvar(txtNome, txtId, dateNasc);
        	PacienteView view = new PacienteView();
			try {
				view.start(stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        });
		
		stage.setTitle("SISTEMA CLINICA");
		stage.setScene(scn);
		stage.show();
	}
}
