package br.com.oobj.integrador;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.oobj.integrador.dao.NotaFiscalDAO;
import br.com.oobj.integrador.destino.Destino;
import br.com.oobj.integrador.model.NotaFiscal;
import br.com.oobj.integrador.origem.Origem;

public class Main {

	public static void main(String[] args) {
		ApplicationContext contextoDoSpring = new ClassPathXmlApplicationContext("applicationContext.xml");

		Origem origemBean = contextoDoSpring.getBean("origem", Origem.class);
		Destino destinoBean = contextoDoSpring.getBean("destino", Destino.class);

		IntegradorNotaFiscal integrador = new IntegradorNotaFiscal();
		integrador.setOrigem(origemBean);
		integrador.setDestino(destinoBean);

		while (true) {
			integrador.integradorNotaFiscal();
			try {
				Thread.sleep(4000);
			} catch (Exception e) {
	
				e.printStackTrace();
			}
		}
		
// 		NotaFiscalDAO notaFiscalDAO = contextoDoSpring.getBean("notaFiscalDAO", NotaFiscalDAO.class);
//		notaFiscalDAO.contarNotas();
//		notaFiscalDAO.removerNota(1L);
//		notaFiscalDAO.contarNotas();
//		notaFiscalDAO.buscarPeloId(88L);	
// 		NotaFiscal nf = notaFiscalDAO.buscarPeloId(2L);
// 		nf.setNomeArquivo("funfou222222.xml");
// 		nf.setId(3L);
// 		notaFiscalDAO.atualizar(nf);
// 		
// 		notaFiscalDAO.buscarPeloId(3L);
//		integrador.integradorNotaFiscal();
 		
 		
// 		System.out.println("Quantidade de Registros no banco: "+ notaFiscalDAO.contarNotas());
 		
		
	}
}
