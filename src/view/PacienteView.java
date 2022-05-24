package view;

import java.util.Date;

import controller.PacienteController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entities.Paciente;

public class PacienteView extends Application{
	
	private TableView<Paciente> tableViewPaciente = new TableView<>();
	private TableColumn<Paciente, Integer> tableColumnId = new TableColumn<>("ID");
	private TableColumn<Paciente, String>   tableColumnNome = new TableColumn<>("NOME");
	private TableColumn<Paciente, Date> tableColumnData = new TableColumn<>("DATA");
	private TableColumn<Paciente, Paciente> tableColumnEDIT = new TableColumn<>("EDIT");
	private TableColumn<Paciente, Paciente> tableColumnREMOVE = new TableColumn<>("REMOVER");
	


	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws Exception {
		PacienteController control = new PacienteController(stage);
		ScrollPane painel = new ScrollPane();
		Scene scn = new Scene(painel);
		
		VBox subPan = new VBox();
		subPan.setPrefHeight(400);
		subPan.setPrefWidth(600);
		
		MenuBar menuBar = new MenuBar();
		Menus menus = new Menus();
		
		
		menus.getMenuItemSobre().setOnAction((e) -> {
			menus.onActionSobre(stage);
		});
		
		
		
		// =================================================================
		// tela com as informações já cadastradas
		
		Label lblTitulo = new Label("Menu Paciente");
		lblTitulo.setStyle("-fx-font-size: 24px;"
				+ " -fx-alignment: center-left; "
				+ "	-fx-pref-height: 30");
		
		ToolBar toolButtons = new ToolBar();
		Button btNovo = new Button("Novo");
		Button btAtualizar = new Button("Atualizar");
		
		toolButtons.getItems().addAll(btNovo, btAtualizar);
		
		btNovo.setOnAction((e) -> {
			control.novo();
		});
		
		btAtualizar.setOnAction((e) -> {
			control.atualizar(tableColumnId, tableColumnNome, tableColumnData, tableViewPaciente, 
					tableColumnEDIT, tableColumnREMOVE);
		});
		
		
		
		tableViewPaciente.getColumns().addAll(tableColumnId, tableColumnNome, tableColumnData,
												tableColumnEDIT, tableColumnREMOVE);
		
		//================================================================
		
		menuBar.getMenus().addAll(menus.getMenuConsulta(), menus.getMenuPaciente(),
				menus.getMenuEmpresa(), menus.getMenuSobre());
		subPan.getChildren().addAll(menuBar, lblTitulo, toolButtons, tableViewPaciente);
		
		painel.setContent(subPan);
		stage.setTitle("Sistema clinica");
		stage.setScene(scn);
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(PacienteView.class, args);
	}
	
}
