package view;

import java.util.Date;

import controller.MedicacaoController;
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
import model.entities.Medicacao;

public class MedicacaoView extends Application {

	private TableView<Medicacao> tableViewMedicacao = new TableView<>();
	private TableColumn<Medicacao, Integer> tableColumnId = new TableColumn<>("ID");
	private TableColumn<Medicacao, String>   tableColumnNome = new TableColumn<>("NOME");
	private TableColumn<Medicacao, Float> tableColumnValor = new TableColumn<>("VALOR");
	private TableColumn<Medicacao, Integer> tableColumnQt = new TableColumn<>("QUANTIDADE");
	private TableColumn<Medicacao, Date> tableColumnData = new TableColumn<>("VALIDADE");
	private TableColumn<Medicacao, Medicacao> tableColumnEDIT = new TableColumn<>("EDIT");
	private TableColumn<Medicacao, Medicacao> tableColumnREMOVE = new TableColumn<>("REMOVE");


	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws Exception {
		MedicacaoController control = new MedicacaoController(stage);
		ScrollPane painel = new ScrollPane();
		Scene scn = new Scene(painel);
		
		VBox subPan = new VBox();
		subPan.setPrefHeight(400);
		subPan.setPrefWidth(700);
		
		MenuBar menuBar = new MenuBar();
		Menus menus = new Menus();
		
		
		menus.getMenuItemSobre().setOnAction((e) -> {
			menus.onActionSobre(stage);
		});
		
		
		
		// =================================================================
		
		Label lblTitulo = new Label("Cadastro Medicacao");
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
			
			String nomeMedic = "";
			if(txtBuscar.getText() == null || txtBuscar.getText().trim().equals("")) {
				nomeMedic = "";
			}else {
				nomeMedic = txtBuscar.getText();
			}
			control.atualizarMedic(tableColumnId, tableColumnNome, tableColumnValor, tableColumnQt, tableColumnData, tableViewMedicacao, 
					tableColumnEDIT, tableColumnREMOVE, nomeMedic);
		});
		
		
		
		tableViewMedicacao.getColumns().addAll( 
				tableColumnId, tableColumnNome, tableColumnValor, tableColumnQt, tableColumnData, tableColumnEDIT, tableColumnREMOVE);
		
		//================================================================
		
		menus.getMenuItemEquipamento().setOnAction((e) -> {
			menus.onActionEquip(stage);
		});
		
		menuBar.getMenus().addAll(menus.getMenuConsulta(), menus.getMenuEquipamento(),
				menus.getMenuEmpresa(), menus.getMenuSobre());
		subPan.getChildren().addAll(menuBar, lblTitulo, toolButtons, tableViewMedicacao);
		
		painel.setMaxHeight(600);
		painel.setContent(subPan);
		stage.setTitle("Sistema clinica");
		stage.setScene(scn);
		stage.show();
	}

}
