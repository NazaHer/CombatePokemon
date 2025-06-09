package com.pokemon.combate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
public class Pokemon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pokemon;
    private String nombre;
    private double saludMaxima;
    private double saludActual;
    private int velocidad;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @ManyToMany
    @JoinTable(
        name = "pokemon_ataque",
        joinColumns = @JoinColumn(name = "id_pokemon"),
        inverseJoinColumns = @JoinColumn(name = "id_ataque")
    )
    private List<Ataque> ataques = new ArrayList<>();

    public Pokemon() {
    }

    public Pokemon(String nombre,  double saludMaxima, int velocidad, Tipo tipo, List<Ataque> ataques) {
        setNombre(nombre);
        setSaludMaxima(saludMaxima);
        setSaludActual(saludMaxima);
        setVelocidad(velocidad);
        setTipo(tipo);
        setAtaques(ataques);
    }

    private void setId_pokemon(Long id_pokemon) {
        this.id_pokemon = id_pokemon;
    }

    private void setNombre(String nombre) {
        if(nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }
    
    private void setSaludMaxima(double saludMaxima) {
        if(saludMaxima < 1){
            throw new IllegalArgumentException("No se puede tener 0 de vida.");
        }
        this.saludMaxima = saludMaxima;
    }
    
    private void setSaludActual(double saludActual) {
        //Ya se valida arriba porque toma el mismo valor
        this.saludActual = saludActual;
    }

    private void setVelocidad(int velocidad) {
        if(velocidad < 1){
            throw new IllegalArgumentException("No se puede tener 0 de velocidad.");
        }
        this.velocidad = velocidad;
    }

    private void setTipo(Tipo tipo) {
        if(tipo == null){
            throw new IllegalArgumentException("No se puede no tener tipo.");
        }
        this.tipo = tipo;
    }

    private void setAtaques(List<Ataque> ataques) {
        if(ataques == null || ataques.size() < 1 || ataques.size() > 4){
            throw new IllegalArgumentException("Se debe tener al menos un ataque y no mas de cuatro.");
        }
        this.ataques = ataques;
    }
    
}
