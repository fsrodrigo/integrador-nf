package br.com.oobj.integrador.origem.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

import br.com.oobj.integrador.model.NotaFiscal;
import br.com.oobj.integrador.origem.Origem;
import br.com.oobj.integrador.utils.Utilitarios;

public class LeitorDiretorio implements Origem {

	private Utilitarios utils = new Utilitarios();
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
			

			try (FileInputStream fis = new FileInputStream(arquivoEncontrado);) {

				String conteudoArquivo = IOUtils.toString(fis, "UTF-8");

				nota.setConteudoArquivo(conteudoArquivo);
				nota.setNomeArquivo(nomeArquivo);
				nota.setChaveDeAcesso(obterChaveAcesso(conteudoArquivo));
				nota.setDataHoraEmissao(obterDataHEmissao(conteudoArquivo));
				nota.setNumeroDocumento(obterNumeroDoc(conteudoArquivo));

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
			utils.moverArquivoProcessado(arquivoEncontrado);

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

	private Date obterDataHEmissao(String conteudoArquivo) {
		Date dataHEmissao = null;
		// String dataHEmissao = null;
		// <chNFe>52180707400611001229650110001263759328055070</chNFe>
		// <dhEmi>2018-07-29T20:00:27-03:00</dhEmi>

		int indexUm = conteudoArquivo.indexOf("<dhEmi>");
		int indexDois = conteudoArquivo.lastIndexOf("</dhEmi>");

		// DateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		DateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

		String dataHEmissao2 = conteudoArquivo.substring(indexUm + 7, indexDois);
		dataHEmissao2 = dataHEmissao2.replace("T", " ");

		try {
			dataHEmissao = (Date) formatter.parse(dataHEmissao2);
		} catch (ParseException e) {
			System.out.println("Não foi possível obter a data ");
			e.printStackTrace();
		}

		System.out.println("Data de emissão do documento: " + dataHEmissao);

		return dataHEmissao;
	}

	private String obterNumeroDoc(String conteudoArquivo) {
		// <nNF>126375</nNF>

		String pattern = "<nNF>(.*?)<\\/nNF>";

		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(conteudoArquivo);
		if (m.find()) {
			String numeroDoc = m.group(1);
			System.out.println("numero do doc: " + numeroDoc);
			return String.valueOf(numeroDoc);
		}else {
			System.out.println("Ops.. Algo deu errado");
		}
		return "";

	}
}
