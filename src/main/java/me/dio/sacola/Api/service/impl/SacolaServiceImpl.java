package me.dio.sacola.Api.service.impl;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.Api.enumeration.FormaPagamento;
import me.dio.sacola.Api.model.Item;
import me.dio.sacola.Api.model.Restaurante;
import me.dio.sacola.Api.model.Sacola;
import me.dio.sacola.Api.repository.ItemRepository;
import me.dio.sacola.Api.repository.ProdutoRepository;
import me.dio.sacola.Api.repository.SacolaRepository;
import me.dio.sacola.Api.resources.Dto.ItemDto;
import me.dio.sacola.Api.service.SacolaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {

    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemRepository itemRepository;



    @Override
    public Item incluirItemNaSacola(ItemDto itemDto) {
        Sacola sacola = verSacola(itemDto.getIdSacola());

      if(sacola.isFechada()){
          throw new RuntimeException("Esta sacola está fechada.");
      }

        Item itemParaSerInserido = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Essa produto não existe!");
                        }
                ))
                .build();
        List<Item> itensDaSacola = sacola.getItens();
        if (itensDaSacola.isEmpty()){
            itensDaSacola.add(itemParaSerInserido);
        } else {
            Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItemParaAdicionar = itemParaSerInserido.getProduto().getRestaurante();
            if (restauranteAtual.equals(restauranteDoItemParaAdicionar)){
                itensDaSacola.add(itemParaSerInserido);
            }else {
                throw new RuntimeException("Não possível adicional produto de restaurante diferentes. Feche a sacola ou esvazie.");
            }

        }
         sacolaRepository.save(sacola);
        return itemRepository.save(itemParaSerInserido);
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
            ()-> {
                    throw new RuntimeException("Essa sacola não existe!");
        }
        );
    }

    @Override
    public Sacola fecharSacola(Long id, int numeroFormaPagamento) {
        Sacola sacola = verSacola(id);

        if (sacola.getItens().isEmpty()){
            throw new RuntimeException("Inclua ítens na sacola!");
        }


        FormaPagamento formaPagamento =
                numeroFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);

    }
}
