package com.pokemon.combate.controller;

import com.pokemon.combate.model.Ataque;
import com.pokemon.combate.service.InAtaqueService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtaqueController {
    
    @Autowired
    private InAtaqueService ataqServ;
    
    @PostMapping("/ataque/crear")
    public String createAtaque(@RequestBody Ataque a){
        ataqServ.saveAtaque(a);
        return "El ataque " + a.getNombre() + " fue guardado con éxito";
    }
    
    @GetMapping("/ataque/lista")
    public List<Ataque> getAtaques(){
        return ataqServ.getAtaques();
    }
    
    
    
}
