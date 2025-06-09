package com.pokemon.combate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Ataque {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ataque;
    private String nombre;
    private double potencia;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    public Ataque() {
    }

    public Ataque(String nombre, double potencia, Tipo tipo) {
        this.nombre = nombre;
        this.potencia = potencia;
        this.tipo = tipo;
    }

    private void setId_ataque(Long id_ataque) {
        this.id_ataque = id_ataque;
    }

    private void setNombre(String nombre) {
        if(nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    private void setPotencia(double potencia) {
        if(potencia < 1){
             throw new IllegalArgumentException("El valor del ataque no puede ser tan bajo.");
        }
        this.potencia = potencia;
    }

    private void setTipo(Tipo tipo) {
        if(tipo == null){
            throw new IllegalArgumentException("No puede no tener tipo.");
        }
        this.tipo = tipo;
    }
   
}
