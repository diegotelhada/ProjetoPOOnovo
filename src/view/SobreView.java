package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SobreView extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		ScrollPane painel = new ScrollPane();
		Scene scn = new Scene(painel);

		VBox subPan = new VBox();
		subPan.setPrefHeight(400);
		subPan.setPrefWidth(600);
		subPan.setAlignment(Pos.TOP_CENTER);
		MenuBar menuBar = new MenuBar();
		Menus menus = new Menus();
		
		Label lblDescrição = new Label("Sistema criado por \n Henrique Amorim \n Matheus \n Alex \n Diego Telhada");
		
		Label lblSistema = new Label("SOBRE O SISTEMA");
		lblSistema.setStyle("-fx-font: 45px Ariel;"
				+ " -fx-stroke: black;"
				+ " -fx-stroke-width: 1;"
				+ " -fx-background-color: red;");
		lblDescrição.setStyle("-fx-font: 20px Ariel;");
		
		menuBar.getMenus().addAll(menus.getMenuConsulta(), menus.getMenuPaciente(),
				menus.getMenuEmpresa(), menus.getMenuSobre());
		subPan.getChildren().addAll(menuBar, lblSistema, lblDescrição);
		painel.setContent(subPan);
		stage.setTitle("Sistema clinica");
		stage.setScene(scn);
		stage.show();
	}

}
