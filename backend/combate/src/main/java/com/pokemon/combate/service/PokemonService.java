package com.pokemon.combate.service;

import com.pokemon.combate.model.Pokemon;
import com.pokemon.combate.repository.InPokemonRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokemonService implements InPokemonService{
    
    @Autowired
    private InPokemonRepository pokeRepo;
    
    @Override
    public void savePokemon(Pokemon p) {
        pokeRepo.save(p);
    }

    @Override
    public List<Pokemon> getPokemons() {
        return pokeRepo.findAll();
    }
    
}
