package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.models.Produto;
import com.meuprojetocheckout.pulseStore.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto cadatrarProduto(Produto produto){
        return produtoRepository.save(produto);
    }


    public List<Produto> buscarProdutos(String nome){
        return produtoRepository.findByNomeContaining(nome);
    }

    public List<Produto> consultarProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> consultarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public void atualizarEstoque(Long id, int quantidade) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            int estoqueAtualizado = produto.getEstoque() + quantidade;
            produto.setEstoque(estoqueAtualizado);
            produtoRepository.save(produto);
        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }
}
