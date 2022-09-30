package me.dio.sacola.Api.service;

import me.dio.sacola.Api.model.Item;
import me.dio.sacola.Api.model.Sacola;
import me.dio.sacola.Api.resources.Dto.ItemDto;

public interface SacolaService {

    Item incluirItemNaSacola(ItemDto itemDto);
    Sacola verSacola(Long id);
    Sacola fecharSacola(Long id, int formaPagamento);
}
