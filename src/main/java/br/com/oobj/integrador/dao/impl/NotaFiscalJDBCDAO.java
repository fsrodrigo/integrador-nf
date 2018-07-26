package br.com.oobj.integrador.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
				PreparedStatement ps = con.prepareStatement("INSERT INTO notas_fiscais (nome_arquivo, conteudo_xml) values(?,?)");) {

			ps.setString(1, nota.getNomeArquivo());
			ps.setString(2, nota.getConteudoArquivo());

			ps.execute();

		} catch (Exception e) {
			System.err.println("Erro: " + e.getMessage());
		}

	}
	


}
