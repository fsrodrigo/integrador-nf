package br.com.oobj.integrador.origem.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import br.com.oobj.integrador.model.NotaFiscal;
import br.com.oobj.integrador.origem.Origem;

public class LeitorDiretorio implements Origem {

	private String path;

	public LeitorDiretorio(String path) {
		this.path = path;
	}

	@Override
	public List<NotaFiscal> obterNotas() {
		System.out.println("Obtendo as notas do diret�rio: " + path);

		List<NotaFiscal> listaDeNotas = new ArrayList<>();

		File diretorio = new File(path);

		for (File arquivoEncontrado : diretorio.listFiles()) {
			String nomeArquivo = arquivoEncontrado.getName();
			System.out.println("Arquivo encontrado: " + nomeArquivo);

			NotaFiscal nota = new NotaFiscal();
			nota.setNomeArquivo(nomeArquivo);

			try (FileInputStream fis = new FileInputStream(arquivoEncontrado);) {

				String conteudoArquivo = IOUtils.toString(fis, "UTF-8");

				nota.setConteudoArquivo(conteudoArquivo);
				nota.setChaveDeAcesso(obterChaveAcesso(conteudoArquivo));

			} catch (IOException e) {
				e.printStackTrace();
				arquivoEncontrado.delete();
			}
//				finally {
//				try {
//					fis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
			listaDeNotas.add(nota);

			// Mover o arquivo para a pasta de processados...
			moverArquivoProcessado(arquivoEncontrado);

		}

		return listaDeNotas;
	}

	private String obterChaveAcesso(String conteudoArquivo) {
		String chaveDeAcesso = null;
		// <chNFe>52180707400611001229650110001263759328055070</chNFe>

		int indexUm = conteudoArquivo.indexOf("<chNFe>");
		int indexDois = conteudoArquivo.lastIndexOf("</chNFe>");
		chaveDeAcesso = conteudoArquivo.substring(indexUm + 7, indexDois);

		System.out.println("Chave de acesso do documento: " + chaveDeAcesso);

		return chaveDeAcesso;
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
//				System.out.println("Deletando o arquivo: " + arquivoEncontrado
//						+ " O mesmo j� existe no diret�rio de processados.");
				arquivoEncontrado.delete();
			}
		} catch (IOException e) {
			System.err.println("falha ao mover o arquivo para o diret�rio de processados..." + e.getMessage());
		}
	}

}
