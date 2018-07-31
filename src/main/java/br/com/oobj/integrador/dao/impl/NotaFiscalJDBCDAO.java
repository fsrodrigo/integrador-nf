package br.com.oobj.integrador.dao.impl;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;

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
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO notas_fiscais (nome_arquivo, conteudo_xml, chave_acesso, data_hora_emissao) values(?,?,?,?)");) {

			ps.setString(1, nota.getNomeArquivo());
			ps.setString(2, nota.getConteudoArquivo());
			ps.setString(3, nota.getChaveDeAcesso());
			ps.setDate(4, (java.sql.Date) nota.getDataHoraEmissao());

			System.out.println("Linhas Inseridas: " + ps.executeUpdate());

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
				String chaveDeAcesso = resultSet.getString("chave_acesso");
				Date dataHoraEmissao = resultSet.getTimestamp("data_hora_emissao");

				NotaFiscal nf = new NotaFiscal();

				nf.setId(id);
				nf.setConteudoArquivo(conteudoArquivo);
				nf.setNomeArquivo(nomeArquivo);
				nf.setChaveDeAcesso(chaveDeAcesso);

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
		NotaFiscal notaFiscalUpd = null;
		String sqlUpdate = "UPDATE notas_fiscais set nome_arquivo=?, chave_acesso=?, conteudo_xml=?, data_hora_emissao=? WHERE id = ?;";

		if (buscarPeloId(notaFiscal.getId()) != null) {

			try (Connection con = dataSource.getConnection();
					PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {

				psUpdate.setString(1, notaFiscal.getNomeArquivo());
				psUpdate.setString(2, notaFiscal.getChaveDeAcesso());
				psUpdate.setString(3, notaFiscal.getConteudoArquivo());
				psUpdate.setDate(4, (java.sql.Date) notaFiscal.getDataHoraEmissao());
				psUpdate.setLong(4, notaFiscal.getId());
				
				psUpdate.executeUpdate();
			} catch (Exception e) {
				System.err.println("Erro: " + e.getMessage());
			}
		} else {
			System.out.println("A nota fiscal que tentou alterar não existe... ");
			return notaFiscalUpd;
		}
		notaFiscalUpd = buscarPeloId(notaFiscal.getId());
		return notaFiscalUpd;
	}

	@Override
	public NotaFiscal buscarPeloId(Long id) {

		NotaFiscal notaFiscal = null;
		String sqlSelectById = "SELECT * FROM notas_fiscais WHERE id = ?;";

		try (Connection con = dataSource.getConnection();
				PreparedStatement psSelectById = con.prepareStatement(sqlSelectById)) {

			psSelectById.setLong(1, id);
			ResultSet resultSet = psSelectById.executeQuery();

			if (resultSet.next()) {
				notaFiscal = new NotaFiscal();

				notaFiscal.setId(resultSet.getLong("id"));
				notaFiscal.setConteudoArquivo(resultSet.getString("conteudo_xml"));
				notaFiscal.setNomeArquivo(resultSet.getString("nome_arquivo"));
				notaFiscal.setChaveDeAcesso(resultSet.getString("chave_acesso"));
				notaFiscal.setDataHoraEmissao(resultSet.getTimestamp("data_hora_emissao"));

				System.out.println("Foi encontrado 1 documento com o id: " + resultSet.getLong("id"));
				System.out.println("Nome do arquivo: " + notaFiscal.getNomeArquivo());
				System.out.println("Chave de Acesso: " + notaFiscal.getChaveDeAcesso());
				// System.out.println(notaFiscal.getConteudoArquivo());

			}

		} catch (Exception e) {
			System.err.println("Erro: " + e.getMessage());
		}

		return notaFiscal;
	}

	@Override
	public NotaFiscal buscaSimplesLivre(String chave, String valor) {

		NotaFiscal notaFiscal = null;
		String sqlSelectLivre = "SELECT * FROM notas_fiscais WHERE " + chave + " = ?;";

		try (Connection con = dataSource.getConnection();
				PreparedStatement psSelectLivre = con.prepareStatement(sqlSelectLivre)) {
			if (!chave.equalsIgnoreCase("id")) {
				psSelectLivre.setString(1, valor);
			} else
				psSelectLivre.setLong(1, Long.parseLong(valor));

			ResultSet resultSet = psSelectLivre.executeQuery();

			if (resultSet.next()) {
				notaFiscal = new NotaFiscal();

				notaFiscal.setId(resultSet.getLong("id"));
				notaFiscal.setConteudoArquivo(resultSet.getString("conteudo_xml"));
				notaFiscal.setNomeArquivo(resultSet.getString("nome_arquivo"));
				notaFiscal.setChaveDeAcesso(resultSet.getString("chave_acesso"));
				notaFiscal.setDataHoraEmissao(resultSet.getTimestamp("data_hora_emissao"));

				System.out.println("Encontrado no banco o documento: " + resultSet.getString("chave_acesso"));

			}

		} catch (Exception e) {
			System.err.println("Erro: " + e.getMessage());
		}

		return notaFiscal;
	}

}
