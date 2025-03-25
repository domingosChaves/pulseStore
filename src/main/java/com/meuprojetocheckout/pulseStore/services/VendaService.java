package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.Enum.Pagamento;
import com.meuprojetocheckout.pulseStore.entity.*;
import com.meuprojetocheckout.pulseStore.repository.ItemVendaRepository;
import com.meuprojetocheckout.pulseStore.repository.ProdutoRepository;
import com.meuprojetocheckout.pulseStore.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ItemVendaRepository itemVendaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public Venda realizarVenda(List<ItemVenda> itens, Usuario usuario, Pagamento pagamento, Endereco endereco) {
        Venda venda = new Venda();
        venda.setUsuario(usuario);
        // Aqui você pode calcular o total e outras informações

        Venda savedVenda = vendaRepository.save(venda);

        for (ItemVenda item : itens) {
            Produto produto = produtoRepository.findById(item.getProduto().getId()).orElseThrow();
            // Atualizar o estoque do produto
            produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            produtoRepository.save(produto);
            item.setVenda(savedVenda);
            itemVendaRepository.save(item);
        }

        // Emitir nota fiscal, registrar histórico, etc.

        return savedVenda;
    }

}
