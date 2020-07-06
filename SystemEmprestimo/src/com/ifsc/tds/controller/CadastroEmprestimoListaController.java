package com.ifsc.tds.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ifsc.tds.dao.CadastroEmpDAO;
import com.ifsc.tds.entity.CadastroEmp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CadastroEmprestimoListaController implements Initializable {

	@FXML
    private TableView<CadastroEmp> tbvCadastro;

    @FXML
    private TableColumn<CadastroEmp, String> tbcNome;
    
    @FXML
    private TableColumn<CadastroEmp, Integer> tbcCodigo;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblStatusValor;
    
    @FXML
    private Label lblStatus;
    
    @FXML
    private Label lblDescricaoEmprestimo;

    @FXML
    private Label lblNomeEmprestimoValor;

    @FXML
    private Label lblCodigoEmprestimoValor;

    @FXML
    private Label lblDescricaoEmprestimoValor;

    @FXML
    private Button btnIncluir;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnExcluir;


    /**
	 * Lista de cadastro
	 */
	private List<CadastroEmp> listaCadastroEmp;
	private ObservableList<CadastroEmp> observableListaCadastroEmp = FXCollections.observableArrayList();
	private CadastroEmpDAO cadastroEmpDAO;


	public static final String CADASTRO_EDITAR = " - Editar";
	public static final String CADASTRO_INCLUIR = " - Incluir";
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.setCadastroEmpDAO(new CadastroEmpDAO());
		this.carregarTableViewCadastroEmp();
		this.selecionarItemTableViewCadastroEmp(null);

		// Adicionado evento diante de quaisquer alteração na seleção de itens do
		// TableView
		this.tbvCadastro.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewCadastroEmp(newValue));
	}

	public void carregarTableViewCadastroEmp() {
		// preparando as colunas que irão aparecer na tabela
		this.tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));

		// Consulta os tipo de conta da base e depois joga para tela
		this.setListaCadastroEmp(this.getCadastroEmpDAO().getAll());
		this.setObservableListaCadastroEmp(FXCollections.observableArrayList(this.getListaCadastroEmp()));

		this.tbvCadastro.setItems(this.getObservableListaCadastroEmp());
	}

	public void selecionarItemTableViewCadastroEmp(CadastroEmp cadastroEmp) {
		if (cadastroEmp != null) {
			this.lblNomeEmprestimoValor.setText(cadastroEmp.getNome());
			this.lblDescricaoEmprestimoValor.setText(cadastroEmp.getDescricao());
			this.lblStatusValor.setText(cadastroEmp.getStatus());
			this.lblCodigo.setText(cadastroEmp.getId().toString());
		}
	}
	
	public boolean onCloseQuery() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Pergunta");
		alert.setHeaderText("Deseja sair da lista de tipo de emprestimo?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public CadastroEmpDAO getCadastroEmpDAO() {
		return cadastroEmpDAO;
	}

	public void setCadastroEmpDAO(CadastroEmpDAO CadastroEmpDAO) {
		this.cadastroEmpDAO = CadastroEmpDAO;
	}

	public List<CadastroEmp> getListaCadastroEmp() {
		return listaCadastroEmp;
	}

	public void setListaCadastroEmp(List<CadastroEmp> listaCadastroEmp) {
		this.listaCadastroEmp = listaCadastroEmp;
	}

	public ObservableList<CadastroEmp> getObservableListaCadastroEmp() {
		return observableListaCadastroEmp;
	}

	public void setObservableListaCadastroEmp(ObservableList<CadastroEmp> observableListaCadastroEmp) {
		this.observableListaCadastroEmp = observableListaCadastroEmp;
	}

	
	@FXML
	void onClickBtnEditar(ActionEvent event) {
		CadastroEmp cadastroEmp = this.tbvCadastro.getSelectionModel().getSelectedItem();
		if (cadastroEmp != null) {
			boolean btnConfirmarClic = this.showTelaCadastroEmpEditar(cadastroEmp, CadastroEmprestimoListaController.CADASTRO_EDITAR);
			if (btnConfirmarClic) {
				this.getCadastroEmpDAO().update(cadastroEmp, null);
				this.carregarTableViewCadastroEmp();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, selecione uma opção na Tabela!");
			alert.show();
		}
	}

	@FXML
	public void onClickBtnExcluir(ActionEvent event) {
		CadastroEmp cadastroEmp = this.tbvCadastro.getSelectionModel().getSelectedItem();
		if (cadastroEmp != null) {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Pergunta");
			alert.setHeaderText("Confirma a exclusão do emprestimo?\n" + cadastroEmp.getNome());

			ButtonType buttonTypeNO = ButtonType.NO;
			ButtonType buttonTypeYES = ButtonType.YES;
			alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);

			Optional<ButtonType> resultado = alert.showAndWait();
			if (resultado.get() == ButtonType.YES) {
				this.getCadastroEmpDAO().delete(cadastroEmp);
				this.carregarTableViewCadastroEmp();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um emprestimo na Tabela!");
			alert.show();
		}
	}

	@FXML
	public void onClickBtnIncluir(ActionEvent event) {
		CadastroEmp cadastroEmp = new CadastroEmp();
//		cadastroEmp.setNome("");
//		cadastroEmp.setDescricao("");
//		cadastroEmp.setStatus("");
		boolean btnConfirmarClic = this.showTelaCadastroEmpEditar(cadastroEmp, CadastroEmprestimoListaController.CADASTRO_INCLUIR);
		if (btnConfirmarClic) {		
			this.getCadastroEmpDAO().save(cadastroEmp);
			this.carregarTableViewCadastroEmp();
		}
	}

	public boolean showTelaCadastroEmpEditar(CadastroEmp cadastroEmp, String operacao) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ifsc/tds/view/CadastroEmprestimoEdit.fxml"));
			Parent cadastroEmpEditXML = loader.load();

			// Criando uma janela e colocando o layout do xml nessa janela
			Stage janelaCadastroEmpEditar = new Stage();
			janelaCadastroEmpEditar.setTitle("Cadastro de emprestimo" + operacao);
			janelaCadastroEmpEditar.initModality(Modality.APPLICATION_MODAL);
			janelaCadastroEmpEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene CadastroEmpEditLayout = new Scene(cadastroEmpEditXML);
			janelaCadastroEmpEditar.setScene(CadastroEmpEditLayout);

			// Setando o cliente no Controller.
			CadastroEmprestimoEditController cadastroEmpEditController = loader.getController();
			cadastroEmpEditController.setJanelaCadastroEmpEdit(janelaCadastroEmpEditar);
			cadastroEmpEditController.setCadastroEmp(cadastroEmp);
			// Mostra o Dialog e espera até que o usuário feche
			janelaCadastroEmpEditar.showAndWait();

			return cadastroEmpEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
