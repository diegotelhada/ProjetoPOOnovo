package view;

import java.util.Date;

import controller.EquipamentoController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entities.Equipamento;

public class EquipamentoView extends Application {

	private TableView<Equipamento> tableViewEquipamento = new TableView<>();
	private TableColumn<Equipamento, Integer> tableColumnId = new TableColumn<>("ID");
	private TableColumn<Equipamento, String>   tableColumnNome = new TableColumn<>("NOME");
	private TableColumn<Equipamento, Float> tableColumnValor = new TableColumn<>("VALOR");
	private TableColumn<Equipamento, Date> tableColumnData = new TableColumn<>("FABRICACAO");
	private TableColumn<Equipamento, Equipamento> tableColumnEDIT = new TableColumn<>("EDIT");
	private TableColumn<Equipamento, Equipamento> tableColumnREMOVE = new TableColumn<>("REMOVE");


	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws Exception {
		EquipamentoController control = new EquipamentoController(stage);
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
			
		menus.getMenuItemMedicacao().setOnAction((e) -> {
			menus.onActionMedic(stage);
		});
		
		// =================================================================
		
		Label lblTitulo = new Label("Cadastro Equipamentos");
		lblTitulo.setStyle("-fx-font-size: 24px;"
				+ " -fx-alignment: center-left; "
				+ "	-fx-pref-height: 30");
		
		ToolBar toolButtons = new ToolBar();
		Button btNovo = new Button("Novo");
		Button btAtualizar = new Button("Pesquisar");
		TextField txtBuscar = new TextField();
		
		toolButtons.getItems().addAll(btNovo, btAtualizar, txtBuscar);
		
		
		btNovo.setOnAction((e) -> {
			control.novo();
		});
		
		btAtualizar.setOnAction((e) -> {
			
			String nomeEquip = "";
			if(txtBuscar.getText() == null || txtBuscar.getText().trim().equals("")) {
				nomeEquip = "";
			}else {
				nomeEquip = txtBuscar.getText();
			}
			control.atualizarEquip(tableColumnId, tableColumnNome, tableColumnValor, tableColumnData, tableViewEquipamento, 
					tableColumnEDIT, tableColumnREMOVE, nomeEquip);
		});
		
		
		
		tableViewEquipamento.getColumns().addAll( 
				tableColumnId, tableColumnNome, tableColumnValor, tableColumnData, tableColumnEDIT, tableColumnREMOVE);
		
		//================================================================
		
		menuBar.getMenus().addAll(menus.getMenuConsulta(), menus.getMenuEquipamento(),
				menus.getMenuEmpresa(), menus.getMenuSobre());
		subPan.getChildren().addAll(menuBar, lblTitulo, toolButtons, tableViewEquipamento);
		
		painel.setMaxHeight(600);
		painel.setContent(subPan);
		stage.setTitle("Sistema clinica");
		stage.setScene(scn);
		stage.show();
	}

}
