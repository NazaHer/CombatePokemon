package com.pokemon.combate.service;

import com.pokemon.combate.model.Ataque;
import com.pokemon.combate.model.Combate;
import com.pokemon.combate.model.Pokemon;
import java.util.List;

public interface InCombateService {
    
    public double calcularDano(Ataque ataque, Pokemon atacante, Pokemon defensor);
    
    public Combate iniciarCombate(List<Long> idsPokemonJugador);
    
    public Combate procesarTurno(Long idAtaqueJugador);
    
    public Combate cambiarPokemonActivoJugador(int nuevoIndice);
    
}
