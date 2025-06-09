package com.pokemon.combate.model;

import lombok.Getter;

@Getter
public class EstadoDelPokemon {
    
    private Pokemon pokemon;
    private double saludActual;

    public EstadoDelPokemon() {
    }

    public EstadoDelPokemon(Pokemon pokemon, double saludActual) {
        this.pokemon = pokemon;
        this.saludActual = saludActual;
    }
    
    public EstadoDelPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
        this.saludActual = pokemon.getSaludMaxima();
    }

    private void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    private void setSaludActual(double saludActual) {
        this.saludActual = saludActual;
    }
    
    public boolean estaVivo(){
        return getSaludActual() > 0;
    }
    
    public void recibirDano(double dano){
        setSaludActual(getSaludActual() - dano);
    }
    
    
    
}
