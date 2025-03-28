package com.meuprojetocheckout.pulseStore.relatorio;

import com.meuprojetocheckout.pulseStore.entity.Pedido;
import com.meuprojetocheckout.pulseStore.services.PedidoService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeraNota {

    // Dependência do PedidoService para obter informações completas do pedido
    @Autowired
    private PedidoService pedidoService;

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
