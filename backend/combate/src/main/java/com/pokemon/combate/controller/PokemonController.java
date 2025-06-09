package com.pokemon.combate.controller;

import com.pokemon.combate.model.Pokemon;
import com.pokemon.combate.service.InPokemonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class PokemonController {
    
    @Autowired
    private InPokemonService pokeServ;
    
    @PostMapping("/pokemon/crear")
    public String createPokemo(@RequestBody Pokemon p){
        pokeServ.savePokemon(p);
        return "El pokemon " + p.getNombre() + " fue registrado en la Pokedex";
    }
    
    @GetMapping("/pokemon/lista")
    public List<Pokemon> getPokemons(){
        return pokeServ.getPokemons();
    }
    
}
