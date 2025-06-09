package com.pokemon.combate.service;

import com.pokemon.combate.model.Ataque;
import java.util.List;

public interface InAtaqueService {
    
    public void saveAtaque(Ataque a);
    
    public List<Ataque> getAtaques();
    
}
