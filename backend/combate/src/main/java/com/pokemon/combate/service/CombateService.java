package com.pokemon.combate.service;

import com.pokemon.combate.model.Ataque;
import com.pokemon.combate.model.Combate;
import com.pokemon.combate.model.EstadoDelPokemon;
import com.pokemon.combate.model.Pokemon;
import com.pokemon.combate.model.Tipo;
import com.pokemon.combate.repository.InPokemonRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CombateService implements InCombateService {
    
    @Autowired
    private InPokemonRepository pokeRepo;
    private Combate combate;
    
    //Calculando el daño del ataque
    public double calcularDano(Ataque ataque, Pokemon atacante, Pokemon defensor){
        double multiplicadorVentaja = calcularBonus(atacante.getTipo(), defensor.getTipo());
        return ataque.getPotencia() * multiplicadorVentaja * calcularStab(ataque, atacante);
    } 
    
    private double calcularBonus(Tipo atacante, Tipo recibe){
        double multiplicador = 0;
        if(atacante.getFortaleza().equalsIgnoreCase(recibe.getNombre())){
            multiplicador = 1.5;
        }else if(atacante.getDebilidad().equalsIgnoreCase(recibe.getNombre())){
            multiplicador = 0.75;
        }else{
            multiplicador = 1;
        }
        return multiplicador;
    } 
    
    private double calcularStab(Ataque a, Pokemon p){
        double stab = 1; //same type attack bonus / Hace referencia a si el pkmn y el atk son del mismo tipo, tiene bonus.
        if(a.getTipo() == p.getTipo()){
            stab = 1.25;
        }
        return stab;
    }
    
    //Logica del combate
    public Combate iniciarCombate(List<Long> idsPokemonJugador){
        //Busco los pokemon elegidos del player
        //List<Pokemon> equipoJugador = pokeRepo.findAllById(idsPokemonJugador); fallo de orden
        
        //Pruba de orden
        List<Pokemon> equipoJugador = idsPokemonJugador.stream().map(id -> pokeRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pokémon no encontrado"))).collect(Collectors.toList());
        
        if(equipoJugador.size() != 3){
            throw new IllegalArgumentException("Debes seleccionar exactamente 3 Pokémon.");
        }
        //Armamos el equipo de la ia
        List<Pokemon> opcionesIa = new ArrayList<>(pokeRepo.findAll());
        Collections.shuffle(opcionesIa);
        List<Pokemon> equipoIA = opcionesIa.subList(0, 3);
        //creo el combate y lo doy
        this.combate = new Combate(equipoJugador, equipoIA);
        return combate;
    }

    //Logica de los turnos
    @Override
    public Combate procesarTurno(Long idAtaqueJugador) {
        //Ataque del jugador
        EstadoDelPokemon objetivoIa = combate.getActivoIA();
        Ataque ataqueJugador = combate.getActivoJugador().getPokemon().getAtaques().stream().filter(a -> a.getId_ataque().equals(idAtaqueJugador)).findFirst().orElseThrow(() -> new RuntimeException("Ataque no válido"));
        //Calcula el daño y sus respectivos bonus
        double danoJugador = calcularDano(ataqueJugador, combate.getActivoJugador().getPokemon(), combate.getActivoIA().getPokemon());
        objetivoIa.recibirDano(danoJugador);
        //Si la ia puede continuar ataca
        if(objetivoIa.getSaludActual() > 0){
            List<Ataque> ataquesIa = combate.getActivoIA().getPokemon().getAtaques();
            Ataque ataqueIa = ataquesIa.get(new Random().nextInt(ataquesIa.size()));
            double danoIa = calcularDano(ataqueIa, combate.getActivoIA().getPokemon(), combate.getActivoJugador().getPokemon());
            combate.getActivoJugador().recibirDano(danoIa);
        }
        // Si el Pokémon de la IA murio tras el ataque del jugador
        if (objetivoIa.getSaludActual() <= 0) {
        // Busc el siguiente Pokémon vivo
        List<EstadoDelPokemon> equipoIA = combate.getEquipoIA();
        for (int i = 0; i < equipoIA.size(); i++) {
        if (equipoIA.get(i).estaVivo()) {
            combate.cambiarPokemonIA(i);  // Método que actualiza el índice del activo
            break;
                }
            }
        }

        return combate;
    }

   public Combate cambiarPokemonActivoJugador(int nuevoIndice) {
        if (combate == null) {
            throw new IllegalStateException("No hay un combate en curso.");
        }
        List<EstadoDelPokemon> equipoJugador = combate.getEquipoJugador();
        if (nuevoIndice < 0 || nuevoIndice >= equipoJugador.size()) {
            throw new IllegalArgumentException("Índice inválido.");
        }
        EstadoDelPokemon nuevoActivo = equipoJugador.get(nuevoIndice);
        if (!nuevoActivo.estaVivo()) {
            throw new IllegalArgumentException("Ese Pokémon está debilitado.");
        }
        if (nuevoIndice == combate.getIndiceJugador()) {
            throw new IllegalArgumentException("Ese Pokémon ya está activo.");
        }
        //Cambia el indice
        combate.cambiarPokemonJugador(nuevoIndice);
        //La ia ataca despues del cambio porque se pierde el turno
        if(!combate.combateFinalizado() && combate.getActivoIA().estaVivo()){
            combate.realizarTurnoIA();
        }
        return combate;
    }
    
}
