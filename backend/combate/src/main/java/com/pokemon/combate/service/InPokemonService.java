package com.pokemon.combate.service;

import com.pokemon.combate.model.Pokemon;
import java.util.List;

public interface InPokemonService{
    
    public void savePokemon(Pokemon p);
    
    public List<Pokemon> getPokemons();
    
}
