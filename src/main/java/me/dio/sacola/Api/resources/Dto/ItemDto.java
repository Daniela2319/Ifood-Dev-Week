package me.dio.sacola.Api.resources.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Builder
@Data
@Embeddable
@NoArgsConstructor
public class ItemDto {

    private Long produtoId;
    private int quantidade;
    private Long sacolaId;
}
