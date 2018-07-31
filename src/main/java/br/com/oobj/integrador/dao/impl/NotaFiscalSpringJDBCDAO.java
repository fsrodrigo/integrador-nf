package br.com.oobj.integrador.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.oobj.integrador.dao.NotaFiscalDAO;
import br.com.oobj.integrador.model.NotaFiscal;

public class NotaFiscalSpringJDBCDAO implements NotaFiscalDAO {

	private JdbcTemplate jdbcTemplate;

	public NotaFiscalSpringJDBCDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void inserirNotaFiscal(NotaFiscal nota) {
		String sqlInsert = "INSERT INTO notas_fiscais (nome_arquivo, conteudo_xml, chave_acesso, data_hora_emissao) values(?,?,?,?)";

		int linhasAfetadas = jdbcTemplate.update(sqlInsert, nota.getNomeArquivo(), nota.getConteudoArquivo(), nota.getChaveDeAcesso(),
				nota.getDataHoraEmissao());
		
		System.out.println("Linhas inseridas: "+ linhasAfetadas);

	}

	@Override
	public int contarNotas() {
		String sqlCount = "SELECT COUNT(*) FROM notas_fiscais;";
		return jdbcTemplate.queryForObject(sqlCount, Integer.class);
	}

	@Override
	public List<NotaFiscal> listarTodas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int removerNota(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public NotaFiscal atualizar(NotaFiscal notaFiscal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NotaFiscal buscarPeloId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NotaFiscal buscaSimplesLivre(String chave, String valor) {
		// TODO Auto-generated method stub
		return null;
	}

}
