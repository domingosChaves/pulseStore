package com.meuprojetocheckout.pulseStore.relatorio;

import com.meuprojetocheckout.pulseStore.entity.ItemCarrinho;
import com.meuprojetocheckout.pulseStore.entity.Pedido;
import com.meuprojetocheckout.pulseStore.entity.Produto;
import com.meuprojetocheckout.pulseStore.services.PedidoService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeraNota {

    // Dependência do PedidoService para obter informações completas do pedido
    @Autowired
    private PedidoService pedidoService;


    public InputStream gerarNFE(Pedido pedido) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Criando um content stream para escrever no PDF
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Adicionando texto ao PDF
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        float yPosition = page.getMediaBox().getHeight() - 50;

        contentStream.beginText();

        // Escrita das informações do cliente
        contentStream.newLineAtOffset(50, yPosition);
        contentStream.showText("Nota Fiscal");
        yPosition -= 20;
        contentStream.newLineAtOffset(0, -20); // Nova linha
        contentStream.showText("Cliente: " + pedido.getCliente().getNome());
        yPosition -= 20;
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("CPF: " + pedido.getCliente().getCpf());

        // Adicionando informações do pedido
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("ID do Pedido: " + pedido.getId());
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Total Frete: R$ " + pedido.getTotalFrete());
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Transportadora: " + pedido.getTransportadora().getNome());

        // Itens do carrinho
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Itens do Carrinho:");
        yPosition -= 20;

        for (ItemCarrinho item : pedido.getCarrinho().getItens()) {
            Produto produto = item.getProduto(); // Assumindo que o produto é carregado corretamente
            float subTotal = produto.getPreco().floatValue() * item.getQuantidade();
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText(produto.getNome() + " - Qtd: " + item.getQuantidade() + " - R$ " + subTotal);
        }

        contentStream.endText();
        contentStream.close();

        // Salvando o documento em uma saída de byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }


    // Método para gerar NFE
    public InputStream gerarNFE(Long pedidoId) throws JRException {
        // Obter o pedido usando o PedidoService
        Pedido pedido = pedidoService.obterPedido(pedidoId);

        // Adicionando informações necessárias para a NFE
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("emitenteNome", "Nome do Emitente"); // Substitua pelo nome real do emitente
        parametros.put("emitenteCNPJ", "CNPJ do Emitente"); // Substitua pelo CNPJ real do emitente
        parametros.put("destinatarioNome", pedido.getCliente().getNome()); // Supondo que você tem esse método em Pedido
        parametros.put("destinatarioCPF", pedido.getCliente().getCpf()); // Supondo que você tem esse método em Pedido
        parametros.put("total", pedido.getTotalFrete()); // Assumindo que o total está na entidade Pedido

        // Carregar o arquivo JRXML
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("./jasper/nfe_template.jasper");
        // Usar a fonte de dados
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(List.of(pedido));

        // Preencher o relatório
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, jrBeanCollectionDataSource);

        // Criar um ByteArrayOutputStream para exportar o relatório
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        // Retornar o InputStream do ByteArrayOutputStream
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

}
