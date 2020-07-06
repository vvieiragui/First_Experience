package com.ifsc.tds.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ifsc.tds.entity.CadastroEmp;

public class CadastroEmpDAO {
	public CadastroEmp get(Long id) {
		CadastroEmp cadastroEmp = null;
		String sql = "select * from cadastro where id = ?";

		// Recupera uma conexão com o banco
		Connection conexao = null;
		// Cria uma instância de PreparedStatment, classe usada para executar a operação
		// SQL (query)
		PreparedStatement stm = null;

		// Classe que vai recuperar os dados do banco de dados
		ResultSet rset = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id.intValue());
			rset = stm.executeQuery();

			// Enquanto existir dados (registros) no banco de dados, recupera
			while (rset.next()) {

				cadastroEmp = new CadastroEmp();
				// Recupera o id do banco e atribui ele ao objeto
				cadastroEmp.setId(rset.getInt("id"));

				// Recupera a descrição do banco e atribui ele ao objeto
				cadastroEmp.setNome(rset.getString("nome"));

				// Recupera o login do banco e atribui ele ao objeto
				cadastroEmp.setDescricao(rset.getString("descricao"));
				
				cadastroEmp.setStatus(rset.getString("status"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}
				if (conexao != null) {
					conexao.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cadastroEmp;
	}

	public List<CadastroEmp> getAll() {
		List<CadastroEmp> cadastroEmp = new ArrayList<CadastroEmp>();

		String sql = "select * from cadastro";
		Connection conexao = null;
		PreparedStatement stm = null;

		// Classe que vai recuperar os dados do banco de dados
		ResultSet rset = null;

		try {
			conexao = new Conexao().getConnection();
			stm = conexao.prepareStatement(sql);

			rset = stm.executeQuery();

			// Enquanto existir dados (registros) no banco de dados, recupera
			while (rset.next()) {
				CadastroEmp CadastroEmp = new CadastroEmp();
				// Recupera o id do banco e atribui ele ao objeto
				CadastroEmp.setId(rset.getInt("id"));

				// Recupera o nome do banco e atribui ele ao objeto
				CadastroEmp.setNome(rset.getString("nome"));

				// Recupera o login do banco e atribui ele ao objeto
				CadastroEmp.setDescricao(rset.getString("descricao"));

				// Recupera a data de cadastro do banco e atribui ela ao objeto
				CadastroEmp.setStatus(rset.getString("status"));
				// Adiciono o contato recuperado, a lista de contatos
				cadastroEmp.add(CadastroEmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				if (stm != null) {
					stm.close();
				}
				if (conexao != null) {
					conexao.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cadastroEmp;
	}

	public int save(CadastroEmp cadastroEmp) {
		/*
		 * Isso é uma sql comum, os ? são os parâmetros que nós vamos adicionar na base
		 * de dados
		 */
		String sql = "insert into cadastro (nome, descricao, status)" + " values (?, ?, ?)";
		System.out.println(sql);
		Connection conexao = null;
		PreparedStatement stm = null;

		try {
			// Recupera uma conexão com o banco
			conexao = new Conexao().getConnection();

			// Cria uma instância de PreparedStatment, classe usada para executar a operação
			// SQL (query)
			stm = conexao.prepareStatement(sql);

			// Adiciona o valor do primeiro parâmetro da sql
			stm.setString(1, cadastroEmp.getNome());
			// Adicionar o valor do segundo parâmetro da sql
			stm.setString(2, cadastroEmp.getDescricao());
			// Adiciona o valor do quarto parâmetro da sql
			stm.setString(3, cadastroEmp.getStatus());
			//stm.setDate(4, new Date(System.currentTimeMillis()));

			// Executa a sql para inserção dos dados
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Fecha as conexões
			try {
				if (stm != null) {
					stm.close();
				}
				if (conexao != null) {
					conexao.close();
				}
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	
	public boolean update(CadastroEmp cadastroEmp, String[] params) {
		//feito removi alguns campos que não era necessario para o tipo_conta e aju7stei os stm
		String sql = "UPDATE cadastro SET nome = ?, descricao = ?, status = ? WHERE id = ? ";

		Connection conexao = null;
		PreparedStatement stm = null;

		try {
			// Recupera uma conexão com o banco
			conexao = new Conexao().getConnection();

			// Cria uma instância de PreparedStatment, classe usada para executar a operação
			// SQL (query)
			stm = conexao.prepareStatement(sql);

			// Adiciona o valor do primeiro parâmetro da sql
			stm.setString(1, cadastroEmp.getNome());
			// Adicionar o valor do segundo parâmetro da sql
			stm.setString(2, cadastroEmp.getDescricao());
			// Adiciona o valor do quarto parâmetro da sql
			stm.setInt(3, cadastroEmp.getId());
			stm.setString(4, cadastroEmp.getStatus());
			// Executa a sql para inserção dos dados
			System.out.println(stm);
			stm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Fecha as conexões
			try {
				if (stm != null) {
					stm.close();
				}
				if (conexao != null) {
					conexao.close();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean delete(CadastroEmp cadastroEmp) {
		//feito o sql estava para deletar do usuario, eu mudei para tipo_conta
		String sql = "delete from cadastro where id = ?";

		// Recupera uma conexão com o banco
		Connection conexao = null;
		// Cria uma instância de PreparedStatment, classe usada para executar a operação
		// SQL (query)
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, cadastroEmp.getId());
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}
				if (conexao != null) {
					conexao.close();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
