package me.dio.sacola.Api.resources;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.Api.model.Item;
import me.dio.sacola.Api.model.Sacola;
import me.dio.sacola.Api.resources.Dto.ItemDto;
import me.dio.sacola.Api.service.SacolaService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ifood-devweek/sacolas")
@RequiredArgsConstructor
public class SacolasResource {

    private final SacolaService sacolaService;



    @PostMapping
    public Item incluirItemNaSacola(@RequestBody ItemDto itemDto){
        return sacolaService.incluirItemNaSacola(itemDto);
    }

    @GetMapping("/{id}")
    public Sacola verSacola(@PathVariable("id") Long id) {
        return sacolaService.verSacola(id);
    }

    @PatchMapping("/fechaSacola/{sacolaId}")
    public Sacola fecharSacola(@PathVariable("sacolaId") Long sacolaId, @RequestParam("formaPagamento") int formaPagamento){
        return sacolaService.fecharSacola(sacolaId, formaPagamento);
    }

}
