package com.ifsc.tds.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.ifsc.tds.entity.CadastroEmp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CadastroEmprestimoEditController {

    @FXML
    private Label lblNome;

    @FXML
    private TextField txtNome;

    @FXML
    private Label lblDescricao;

    @FXML
    private TextField txtDescricao;

    @FXML
    private Label lblStatus;

    @FXML
    private TextField txtStatus;
    
    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancela;

    private Stage janelaCadastroEmpEdit;
	private CadastroEmp cadastroEmp;
	private boolean okClick = false;
	
    @FXML
    void clickCancela(ActionEvent event) {
    	this.janelaCadastroEmpEdit.close();
    }

    @FXML
    void clickOK(ActionEvent event) {
    	if (validarCampos()) {
			this.cadastroEmp.setNome(this.txtNome.getText());
			this.cadastroEmp.setDescricao(this.txtDescricao.getText());
			this.cadastroEmp.setStatus(this.txtStatus.getText());

			this.okClick = true;
			this.janelaCadastroEmpEdit.close();
		}
    }

	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * Atribui a janela ao controle
	 * 
	 * @param janelaTipoContaEdit
	 */
	public void setJanelaCadastroEmpEdit(Stage janelaCadastroEmpEdit) {
		this.janelaCadastroEmpEdit = janelaCadastroEmpEdit;
	}

	/**
	 * Atribui a conta que será editado na janela.
	 * 
	 * @param tipoConta
	 */
	public void setCadastroEmp(CadastroEmp cadastroEmp) {
		this.cadastroEmp = cadastroEmp;

		this.txtNome.setText(this.cadastroEmp.getNome());
		this.txtDescricao.setText(this.cadastroEmp.getDescricao());
		this.txtStatus.setText(this.cadastroEmp.getStatus());
	}


	/**
	 * Retorna verdadeiro se o usuário clicou o botão OK, senão retorna false.
	 * 
	 * @return boolean
	 */
	public boolean isOkClick() {
		return okClick;
	}

	/**
	 * Valida os dados digitados nos campos da tela.
	 * 
	 * @return true se os dados forem válidos
	 */
	private boolean validarCampos() {
		String mensagemErros = "";

		if (this.txtNome.getText() == null || this.txtNome.getText().length() == 0) {
			mensagemErros += "Informe o Nome!\n";
		}
		if (this.txtDescricao.getText() == null || this.txtDescricao.getText().length() == 0) {
			mensagemErros += "Informe a descrição!\n";
		}
		if (this.txtStatus.getText() == null || this.txtStatus.getText().length() == 0) {
			mensagemErros += "Informe o status!\n";
		}

		if (mensagemErros.length() == 0) {
			return true;
		} else {
			// Mostrando os erros.
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.initOwner(this.janelaCadastroEmpEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações:");
			alerta.setContentText(mensagemErros);

			alerta.showAndWait();

			return false;
		}
	}

}
