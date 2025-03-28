package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.entity.Pedido;
import com.meuprojetocheckout.pulseStore.relatorio.GeraNota;
import com.meuprojetocheckout.pulseStore.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NfeControllerTest {

    @InjectMocks
    private NfeController nfeController;

    @Mock
    private GeraNota relatorioService;

    @Mock
    private PedidoService pedidoService;

    private Long pedidoId = 1L;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setId(pedidoId);
    }

    @Test
    void testarGerarNFE() throws Exception {
        byte[] pdfBytes = "PDF content".getBytes();
        when(relatorioService.gerarNFE(pedidoId)).thenReturn(new ByteArrayInputStream(pdfBytes));

        ResponseEntity<byte[]> response = nfeController.gerarNFE(pedidoId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pdfBytes.length, response.getBody().length);
        assertEquals("inline; filename=nfe.pdf", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
    }

    @Test
    void testarGerarNFEComErro() throws Exception {
        when(relatorioService.gerarNFE(pedidoId)).thenThrow(new RuntimeException("Erro ao gerar NFE"));

        ResponseEntity<byte[]> response = nfeController.gerarNFE(pedidoId);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Erro ao gerar NFE: Erro ao gerar NFE", new String(response.getBody()));
    }

    @Test
    void testarDownloadPdf() throws IOException {
        byte[] pdfBytes = "PDF content".getBytes();
        when(pedidoService.obterPedido(pedidoId)).thenReturn(pedido);
        when(relatorioService.gerarNFE(any(Pedido.class))).thenReturn(new ByteArrayInputStream(pdfBytes));

        ResponseEntity<ByteArrayResource> response = nfeController.downloadPdf(pedidoId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pdfBytes.length, response.getBody().contentLength());
        assertEquals("attachment; filename=nota_fiscal.pdf", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
    }

    @Test
    void testarDownloadPdfComErro() throws IOException {
        when(pedidoService.obterPedido(pedidoId)).thenThrow(new RuntimeException("Pedido não encontrado"));

        ResponseEntity<ByteArrayResource> response = nfeController.downloadPdf(pedidoId);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }
}