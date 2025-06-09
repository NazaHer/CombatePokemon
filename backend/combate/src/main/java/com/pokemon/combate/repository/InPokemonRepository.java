package com.pokemon.combate.repository;

import com.pokemon.combate.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InPokemonRepository extends JpaRepository<Pokemon, Long>{
    
}
