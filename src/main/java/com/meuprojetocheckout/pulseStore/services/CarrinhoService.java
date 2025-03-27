package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.entity.Carrinho;
import com.meuprojetocheckout.pulseStore.entity.ItemCarrinho;
import com.meuprojetocheckout.pulseStore.repository.CarrinhoRepository;
import com.meuprojetocheckout.pulseStore.repository.ItemCarrinhoRepository;
import com.meuprojetocheckout.pulseStore.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarrinhoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

    // Criar um novo carrinho
    @Transactional
    public Carrinho criarCarrinho(Long usuarioId) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!!"));

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuarioId(usuarioId); // Armazenamos apenas o ID do usuário
        return carrinhoRepository.save(carrinho);
    }

    // Consultar carrinho por ID
    public Carrinho consultarCarrinhoPorId(Long carrinhoId) {
        return carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
    }

    // Consultar carrinhos por ID de usuário
    public List<Carrinho> consultarCarrinhosPorUsuarioId(Long usuarioId) {
        return carrinhoRepository.findByUsuarioId(usuarioId);
    }

    // Consultar carrinho por ID de carrinho e ID de usuário
    public Carrinho consultarCarrinhoPorIdEUsuario(Long carrinhoId, Long usuarioId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        if (!carrinho.getUsuarioId().equals(usuarioId)) {
            throw new RuntimeException("O carrinho não pertence ao usuário especificado");
        }

        return carrinho;
    }

    // Adicionar um item ao carrinho
    @Transactional
    public void adicionarItem(Long carrinhoId, Long produtoId, int quantidade) {
        Carrinho carrinho = consultarCarrinhoPorId(carrinhoId);

        // Criar um novo ItemCarrinho
        ItemCarrinho item = new ItemCarrinho(carrinhoId, produtoId, quantidade);
        carrinho.adicionarItem(item);

        // Salvar o item no repositório
        itemCarrinhoRepository.save(item);
    }

    // Remover um item do carrinho
    @Transactional
    public void removerItem(Long carrinhoId, Long itemId) {
        Carrinho carrinho = consultarCarrinhoPorId(carrinhoId);

        // Encontrar o item no carrinho pelo ID
        ItemCarrinho item = itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        // Verificar se o item realmente pertence ao carrinho
        if (!item.getCarrinhoId().equals(carrinhoId)) {
            throw new RuntimeException("O item não pertence ao carrinho especificado");
        }

        // Remover o item do carrinho
        carrinho.removerItem(item);
        itemCarrinhoRepository.delete(item); // Remove o item do repositório
    }

    // Apagar carrinho
    @Transactional
    public void apagarCarrinho(Long carrinhoId) {
        Carrinho carrinho = consultarCarrinhoPorId(carrinhoId);
        carrinhoRepository.delete(carrinho); // Remove o carrinho do repositório
    }

}