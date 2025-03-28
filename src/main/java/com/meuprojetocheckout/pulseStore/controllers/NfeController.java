package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.relatorio.GeraNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("/api/nfe")
public class NfeController {

    @Autowired
    private GeraNota relatorioService;

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
}