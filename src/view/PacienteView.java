package view;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Utils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entities.Paciente;
import model.services.PacienteService;

public class PacienteView extends Application implements Initializable{
	
	private PacienteService paciente;
	private TableView<Paciente> tableViewPaciente = new TableView<>();
	private TableColumn<Paciente, Integer> tableColumnId = new TableColumn<>("ID");
	private TableColumn<Paciente, String> tableColumnNome = new TableColumn<>("NOME");
	private TableColumn<Paciente, Date> tableColumnData = new TableColumn<>("DATA");
	private TableColumn<Paciente, Paciente> tableColumnEDIT = new TableColumn<>();
	private TableColumn<Paciente, Paciente> tableColumnREMOVE = new TableColumn<>();
	private ObservableList<Paciente> obsList;


	@Override
	public void start(Stage stage) throws Exception {
		ScrollPane painel = new ScrollPane();
		Scene scn = new Scene(painel);
		
		VBox subPan = new VBox();
		subPan.setPrefHeight(400);
		subPan.setPrefWidth(600);
		
		MenuBar menuBar = new MenuBar();
		Menu menuConsulta =  new Menu("Consulta");
		Menu menuPaciente =  new Menu("Paciente");
		Menu menuEmpresa =  new Menu("Empresa");
		Menu menuSobre =  new Menu("Sobre");
		
		MenuItem menuItemConsulta = new MenuItem("Pesquisar/Marcar");
		MenuItem menuItemPaciente = new MenuItem("Cadastro");
		MenuItem menuItemEspecializacao = new MenuItem("Especialização");
		MenuItem menuItemMedico = new MenuItem("Medico");
		MenuItem menuItemSobre = new MenuItem("Sistema");
		
		// =================================================================
		// tela com as informações já cadastradas
		
		Label lblTitulo = new Label("Menu Paciente");
		lblTitulo.setStyle("-fx-font-size: 24px;"
				+ " -fx-alignment: center-left; "
				+ "	-fx-pref-height: 30");
		
		ToolBar toolButtons = new ToolBar();
		Button btNovo = new Button("Novo");
		
		toolButtons.getItems().add(btNovo);
		
		tableViewPaciente.getColumns().addAll(tableColumnId, tableColumnNome, tableColumnData,
												tableColumnEDIT, tableColumnREMOVE);
		
		//================================================================
		menuConsulta.getItems().add(menuItemConsulta);
		menuPaciente.getItems().add(menuItemPaciente);
		menuEmpresa.getItems().addAll(menuItemMedico, menuItemEspecializacao);
		menuSobre.getItems().add(menuItemSobre);
		
		menuBar.getMenus().addAll(menuConsulta, menuPaciente, menuEmpresa, menuSobre);
		subPan.getChildren().addAll(menuBar, lblTitulo, toolButtons, tableViewPaciente);
		
		painel.setContent(subPan);
		stage.setTitle("SISTEMA CLINICA");
		stage.setScene(scn);
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(PacienteView.class, args);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		// essa função inicia os valores dentro das tabelas
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("IdPaciente"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dataAniversario"));
		Utils.formatTableColumnDate(tableColumnData, "dd/MM/yyyy");
		
		if (paciente == null) {
			throw new IllegalStateException("Service was null");
		}

		List<Paciente> list = paciente.findAll();
		// somente um ObservableList pode passar parametros para o setItems
		obsList = FXCollections.observableArrayList(list);
		tableViewPaciente.setItems(obsList);

	}
	
}
