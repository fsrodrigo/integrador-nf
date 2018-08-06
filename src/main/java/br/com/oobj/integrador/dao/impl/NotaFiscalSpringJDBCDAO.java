package br.com.oobj.integrador.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.oobj.integrador.dao.NotaFiscalDAO;
import br.com.oobj.integrador.model.NotaFiscal;

@Repository
public class NotaFiscalSpringJDBCDAO implements NotaFiscalDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public NotaFiscalSpringJDBCDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void inserirNotaFiscal(NotaFiscal nota) {
		String sqlInsert = "INSERT INTO notas_fiscais (nome_arquivo, conteudo_xml, chave_acesso, data_hora_emissao, numero_documento) values(?,?,?,?,?)";

		int linhasAfetadas = jdbcTemplate.update(sqlInsert, nota.getNomeArquivo(), nota.getConteudoArquivo(),
				nota.getChaveDeAcesso(), nota.getDataHoraEmissao(), nota.getNumeroDocumento());

		System.out.println("Linhas inseridas: " + linhasAfetadas);

	}

	@Override
	public int contarNotas() {
		String sqlCount = "SELECT COUNT(*) FROM notas_fiscais;";
		return jdbcTemplate.queryForObject(sqlCount, Integer.class);
	}

	@Override
	public List<NotaFiscal> listarTodas() {

		String sqlSelectAll = "SELECT * FROM notas_fiscais;";
		List<NotaFiscal> nf = jdbcTemplate.query(sqlSelectAll, new NotaFiscalRowMapper());

		System.out.println(nf);

		return nf;
	}

	@Override
	public int removerNota(Long id) {

		if (buscarPeloId(id) != null) {
			String sqlDelete = "DELETE FROM notas_fiscais WHERE id = ?;";
			int linhasAfetadas = jdbcTemplate.update(sqlDelete, id);

			if (linhasAfetadas == 1)
				System.out.println("Registro excluído com sucesso");
			else
				System.out.println("ops.. Algo deu errado.");

			return linhasAfetadas;
		} else
			System.out.println("Documento com id: " + id + " não existe...");
		return 0;

	}

	@Override
	public NotaFiscal atualizar(NotaFiscal notaFiscal) {
		String sqlUpdate = "UPDATE notas_fiscais SET nome_arquivo = ?, conteudo_xml = ?, chave_acesso = ?, data_hora_emissao = ? "
				+ "where id = ?;";

		if (buscarPeloId(notaFiscal.getId()) != null) {

			int linhasAtualizadas = jdbcTemplate.update(sqlUpdate, notaFiscal.getNomeArquivo(),
					notaFiscal.getConteudoArquivo(), notaFiscal.getChaveDeAcesso(), notaFiscal.getDataHoraEmissao(),
					notaFiscal.getId());

			System.out.println("Linhas atualizadas: " + linhasAtualizadas);

			if (linhasAtualizadas == 1) {
				return buscarPeloId(notaFiscal.getId());
			}
		}

		return null;
	}

	@Override
	public NotaFiscal buscarPeloId(Long id) {
		String sqlSelectAll = "SELECT * FROM notas_fiscais where id = ?;";
		NotaFiscal nf = new NotaFiscal();
		try {
			nf = jdbcTemplate.queryForObject(sqlSelectAll, new Object[] { id }, new NotaFiscalRowMapper());
		} catch (RuntimeException e) {
			System.out.println("\"Não existe registro com o ID: " + id);
		}
		return nf;
	}

	@Override
	public NotaFiscal buscaSimplesLivre(String chave, String valor) {
		// TODO Auto-generated method stub
		return null;
	}

}
