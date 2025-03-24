package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.models.Carrinho;
import com.meuprojetocheckout.pulseStore.models.ItemCarrinho;
import com.meuprojetocheckout.pulseStore.models.Usuario;
import com.meuprojetocheckout.pulseStore.repository.CarrinhoRepository;
import com.meuprojetocheckout.pulseStore.repository.ItemCarrinhoRepository; // Supondo que você tenha um repositório para ItemCarrinho
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository; // Para manipular itens no carrinho

    // Criar um novo carrinho
    public Carrinho criarCarrinho(Usuario usuario) {
        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario);
        return carrinhoRepository.save(carrinho);
    }

    // Adicionar um item ao carrinho
    public void adicionarItem(Long carrinhoId, ItemCarrinho item) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        // Atribuindo o carrinho ao item
        item.setCarrinho(carrinho);
        carrinho.adicionarItem(item);
        itemCarrinhoRepository.save(item); // Supondo que você salva o item individualmente
    }

    // Obter o carrinho
    public Carrinho obterCarrinho(Long carrinhoId) {
        return carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
    }

    // Remover um item do carrinho
    public void removerItem(Long carrinhoId, Long itemId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        // Encontrar o item no carrinho pelo ID
        ItemCarrinho item = itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        // Remover o item do carrinho
        carrinho.removerItem(item);
        itemCarrinhoRepository.delete(item); // Remove o item do repositório
        carrinhoRepository.save(carrinho); // Salva o carrinho atualizado
    }
}