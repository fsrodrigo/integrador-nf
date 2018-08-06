package br.com.oobj.integrador.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import br.com.oobj.integrador.model.NotaFiscal;

public class NotaFiscalRowMapper implements RowMapper<NotaFiscal> {
	
	@Override
	public NotaFiscal mapRow(ResultSet rs, int rowNumber) throws SQLException {
		
		NotaFiscal nf = new NotaFiscal();
		
		nf.setId(rs.getLong("id"));
		nf.setChaveDeAcesso(rs.getString("chave_acesso"));
		nf.setConteudoArquivo(rs.getString("conteudo_xml"));
		nf.setDataHoraEmissao(rs.getTimestamp("data_hora_emissao"));
		nf.setNomeArquivo(rs.getString("nome_arquivo"));
		
		return nf;
	}

}
