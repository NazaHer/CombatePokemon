package com.pokemon.combate.model;

import java.util.List;

public class Combate {
    
    private List<EstadoDelPokemon> equipoJugador;
    private List<EstadoDelPokemon> equipoIA;
    private int indiceJugador = 0;
    private int indiceIA = 0;
    
    public Combate(List<Pokemon> jugador, List<Pokemon> ia){
        this.equipoJugador = convertirAPokemonEstado(jugador);
        this.equipoIA = convertirAPokemonEstado(ia);
    }
    
    private List<EstadoDelPokemon> convertirAPokemonEstado(List<Pokemon> lista) {
        return lista.stream().map(EstadoDelPokemon::new).toList();
    }
    
    public EstadoDelPokemon getActivoJugador() {
        return equipoJugador.get(indiceJugador);
    }
    
    public EstadoDelPokemon getActivoIA() {
        return equipoIA.get(indiceIA);
    }
    
    public void revisarCambiosDePokemon() {
        if (!getActivoJugador().estaVivo()) {
            indiceJugador = buscarProximoVivo(equipoJugador);
        }
        if (!getActivoIA().estaVivo()) {
            indiceIA = buscarProximoVivo(equipoIA);
        }
    }
    
    private int buscarProximoVivo(List<EstadoDelPokemon> equipo) {
        for (int i = 0; i < equipo.size(); i++) {
            if (equipo.get(i).estaVivo()) return i;
        }
        return -1; // ninguno vivo
    }
    
     public boolean combateFinalizado() {
        return buscarProximoVivo(equipoJugador) == -1 || buscarProximoVivo(equipoIA) == -1;
    }

    public List<EstadoDelPokemon> getEquipoJugador() {
        return equipoJugador;
    }

    public List<EstadoDelPokemon> getEquipoIA() {
        return equipoIA;
    }

    public void cambiarPokemonJugador(int nuevoIndice) {
        if (nuevoIndice < 0 || nuevoIndice >= equipoJugador.size()) {
            throw new IllegalArgumentException("Índice fuera de rango");
        }
        if (!equipoJugador.get(nuevoIndice).estaVivo()) {
            throw new IllegalArgumentException("Ese Pokémon está debilitado");
        }
        this.indiceJugador = nuevoIndice;
    }
    
    public void cambiarPokemonIA(int nuevoIndice) {
        this.indiceIA = nuevoIndice;
    }

    public int getIndiceJugador() {
        return indiceJugador;
    }
    
    public void realizarTurnoIA(){
        EstadoDelPokemon atacante = this.getActivoIA();
        EstadoDelPokemon defensor = this.getActivoJugador();
        
        if (!atacante.estaVivo() || !defensor.estaVivo()) return;
         
        List<Ataque> ataques = atacante.getPokemon().getAtaques();
        if (ataques.isEmpty()) return;
         
        Ataque ataque = ataques.get(new java.util.Random().nextInt(ataques.size()));
        double dano = ataque.getPotencia();
        defensor.recibirDano(dano);
        
        this.revisarCambiosDePokemon();
    }
    
}
