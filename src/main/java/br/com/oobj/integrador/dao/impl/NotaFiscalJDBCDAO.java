package br.com.oobj.integrador.dao.impl;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.oobj.integrador.dao.NotaFiscalDAO;
import br.com.oobj.integrador.model.NotaFiscal;

public class NotaFiscalJDBCDAO implements NotaFiscalDAO {

	private DataSource dataSource;

	public NotaFiscalJDBCDAO(DataSource dataSource) {

		this.dataSource = dataSource;
	}

	@Override
	public void inserirNotaFiscal(NotaFiscal nota) {

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con
						.prepareStatement("INSERT INTO notas_fiscais (nome_arquivo, conteudo_xml) values(?,?)");) {

			ps.setString(1, nota.getNomeArquivo());
			ps.setString(2, nota.getConteudoArquivo());

			ps.execute();

		} catch (Exception e) {
			System.err.println("Erro: " + e.getMessage());
		}

	}

	@Override
	public int contarNotas() {
		int totalDeRegistros = 0;
		String sqlCount = "SELECT COUNT(*) FROM notas_fiscais;";

		try (Connection connection = dataSource.getConnection()) {

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlCount);

			resultSet.next();
			totalDeRegistros = resultSet.getInt("COUNT");

		} catch (Exception e) {
			System.err.println("Falha ao listar todos os resultados: " + e.getMessage());
		}
		System.out.println("Obtidos: " + totalDeRegistros + " Registros");

		return totalDeRegistros;
	}

	@Override
	public List<NotaFiscal> listarTodas() {

		List<NotaFiscal> todasNotas = new ArrayList<>();
		String sqlSelect = "SELECT * FROM notas_fiscais;";

		try (Connection connection = dataSource.getConnection()) {

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlSelect);

			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String nomeArquivo = resultSet.getString("nome_arquivo");
				String conteudoArquivo = resultSet.getString("conteudo_xml");

				NotaFiscal nf = new NotaFiscal();

				nf.setId(id);
				nf.setConteudoArquivo(conteudoArquivo);
				nf.setNomeArquivo(nomeArquivo);

				todasNotas.add(nf);
			}

		} catch (Exception e) {
			System.err.println("Falha ao listar todos os resultados: " + e.getMessage());
		}
		System.out.println("Obtidos: " + todasNotas.size() + " Registros");
		return todasNotas;
	}

	@Override
	public int removerNota(Long id) {
		int qtdRegistrosDeletados = 0;
		String sqlDelete = "DELETE FROM notas_fiscais WHERE id = ?;";

		try (Connection con = dataSource.getConnection();
				PreparedStatement psDelete = con.prepareStatement(sqlDelete)) {

			psDelete.setLong(1, id);
			qtdRegistrosDeletados = psDelete.executeUpdate();

		} catch (Exception e) {
			System.err.println("Erro: " + e.getMessage());
		}
		System.out.println("removido: " + qtdRegistrosDeletados + "Registros...");
		return qtdRegistrosDeletados;
	}

	@Override
	public NotaFiscal atualizar(NotaFiscal notaFiscal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NotaFiscal buscarPeloId(Long id) {

		NotaFiscal notaFiscal = null;
		String sqlSelectById = "SELECT * FROM notas_fiscais WHERE id = ?;";

		try (Connection con = dataSource.getConnection();
				PreparedStatement psSelectById = con.prepareStatement(sqlSelectById)) {

			psSelectById.setLong(1, id);
			ResultSet resultSet = psSelectById.executeQuery();

			if(resultSet.next()) {
				notaFiscal = new NotaFiscal();
				
				notaFiscal.setId(resultSet.getLong("id"));
				notaFiscal.setConteudoArquivo(resultSet.getString("conteudo_xml"));
				notaFiscal.setNomeArquivo(resultSet.getString("nome_arquivo"));
				
				System.out.println("Foi encontrado 1 documento com o id: " + resultSet.getLong("id") );
				System.out.println("Nome do arquivo: "+ notaFiscal.getNomeArquivo());
				
				System.out.println(notaFiscal);

			}

		} catch (Exception e) {
			System.err.println("Erro: " + e.getMessage());
		}
		

		return notaFiscal;
	}

}
