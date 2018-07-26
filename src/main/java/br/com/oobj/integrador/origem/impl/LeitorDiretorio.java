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
		System.out.println("Obtendo as notas do diretório: " + path);

		List<NotaFiscal> listaDeNotas = new ArrayList<>();

		File diretorio = new File(path);

		for (File arquivoEncontrado : diretorio.listFiles()) {
			String nomeArquivo = arquivoEncontrado.getName();
			System.out.println("Arquivo encontrado: " + nomeArquivo);

			NotaFiscal nota = new NotaFiscal();
			nota.setNomeArquivo(nomeArquivo);

			try(FileInputStream fis = new FileInputStream(arquivoEncontrado);) {
				
				nota.setConteudoArquivo(IOUtils.toString(fis, "UTF-8"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
//				finally {
//				try {
//					fis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
			listaDeNotas.add(nota);

			//Mover o arquivo para a pasta de processados...
			moverArquivoProcessado(arquivoEncontrado);

		}

		return listaDeNotas;
	}

	private void moverArquivoProcessado(File arquivoEncontrado) {
		File parentFile = arquivoEncontrado.getParentFile().getParentFile();
		File diretorioProcessados = new File(parentFile.getAbsolutePath(),"processados");
		
		if(!diretorioProcessados.exists()) {
			diretorioProcessados.mkdir();
		}
		try {
			FileUtils.moveFileToDirectory(arquivoEncontrado, diretorioProcessados, true);
		} catch (IOException e) {
			System.err.println("falha ao mover o arquivo para o diretório de processados..." + e.getMessage());
		}
	}

}
