package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.entity.Pedido;
import com.meuprojetocheckout.pulseStore.relatorio.GeraNota;
import com.meuprojetocheckout.pulseStore.services.PedidoService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/nfe")
public class NfeController {

    @Autowired
    private GeraNota relatorioService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{pedidoId}")
    public ResponseEntity<byte[]> gerarNFE(@PathVariable Long pedidoId) {
        try {
            // Chama o serviço para gerar a NFE
            InputStream nfeStream = relatorioService.gerarNFE(pedidoId);
            byte[] bytes = nfeStream.readAllBytes();

            // Configura os cabeçalhos da resposta
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=nfe.pdf");

            // Retorna a resposta com o PDF
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(bytes);
        } catch (Exception e) {
            // Em caso de erro, retorna um status de erro
            return ResponseEntity.status(500).body(("Erro ao gerar NFE: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/download-pdf/{pedidoId}")
    public ResponseEntity<ByteArrayResource> downloadPdf(@PathVariable Long pedidoId) throws IOException {
        try {
            // Obtém o pedido (você deve implementar essa lógica)
            Pedido pedido = pedidoService.obterPedido(pedidoId);

            // Gera o PDF
            InputStream nfeStream = relatorioService.gerarNFE(pedido);

            // Converte o InputStream para um byte array
            byte[] pdfBytes = nfeStream.readAllBytes();

            // Cria o ByteArrayResource
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);

            // Define os headers da resposta
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=nota_fiscal.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(pdfBytes.length)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}