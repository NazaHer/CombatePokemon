package com.pokemon.combate.repository;

import com.pokemon.combate.model.Ataque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InAtaqueRepository extends JpaRepository<Ataque, Long>{
    
}
