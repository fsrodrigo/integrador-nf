package br.com.oobj.integrador.origem.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import br.com.oobj.integrador.dao.NotaFiscalDAO;
import br.com.oobj.integrador.model.NotaFiscal;
import br.com.oobj.integrador.origem.Origem;

public class LeitorBancoDeDados implements Origem {

	private NotaFiscalDAO notaFiscalDAO;
	private String pathLeitura;

	public LeitorBancoDeDados(NotaFiscalDAO notaFiscalDAO, String pathLeitura) {
		this.notaFiscalDAO = notaFiscalDAO;
		this.pathLeitura = pathLeitura;
	}

	public List<NotaFiscal> obterNotas() {
		System.out.println("Lendo diretório de Entrada: " + pathLeitura);

		List<NotaFiscal> listaDeNotas =  new ArrayList<>();
		File diretorio = new File(pathLeitura);
		String chave = null;
		String valor = null;

		for (File arquivoEncontrado : diretorio.listFiles()) {
			String nomeArquivo = arquivoEncontrado.getName();
			System.out.println("Arquivo encontrado: " + nomeArquivo);

			try (FileReader fis = new FileReader(arquivoEncontrado);) {
				BufferedReader conteudoArquivo = new BufferedReader(fis);

				String linha = conteudoArquivo.readLine();
				while (linha != null) {
					// System.out.printf("%s\n", linha);
					int index = linha.indexOf("=");
					chave = linha.substring(0, index);
					valor = linha.substring(index + 1);
					System.out.println("Obtendo nota fiscal: " + chave + "=" + valor);
					listaDeNotas.add(notaFiscalDAO.buscaSimplesLivre(chave, valor));
					linha = conteudoArquivo.readLine();
				}
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			moverArquivoProcessado(arquivoEncontrado);
		}
		
		return listaDeNotas;
	}

	private void moverArquivoProcessado(File arquivoEncontrado) {
		File parentFile = arquivoEncontrado.getParentFile().getParentFile();
		File diretorioProcessados = new File(parentFile.getAbsolutePath(), "processados");
		File arquivoProcessado = new File(diretorioProcessados.getAbsolutePath(), arquivoEncontrado.getName());

		if (!diretorioProcessados.exists()) {
			diretorioProcessados.mkdir();
		}
		try {
			if (!arquivoProcessado.exists()) {
				FileUtils.moveFileToDirectory(arquivoEncontrado, diretorioProcessados, true);
			} else {
				arquivoEncontrado.delete();
			}
		} catch (IOException e) {
			System.err.println("falha ao mover o arquivo para o diretório de processados..." + e.getMessage());
		}
	}
}