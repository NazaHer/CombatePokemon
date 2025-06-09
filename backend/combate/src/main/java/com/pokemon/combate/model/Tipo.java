package com.pokemon.combate.model;

public enum Tipo {
    
    FUEGO("Fuego", "Agua", "Planta"),
    AGUA("Agua", "Planta", "Fuego"),
    PLANTA("Planta", "Fuego", "Agua"),
    VOLADOR("Volador", "Electrico", "Tierra"),
    ELECTRICO("Electrico", "Tierra", "Volador"),
    TIERRA("Tierra", "Voladora", "Electrico");
    
    private final String nombre;
    private final String debilidad;
    private final String fortaleza;

    private Tipo(String nombre, String debilidad, String fortaleza) {
        this.nombre = nombre;
        this.debilidad = debilidad;
        this.fortaleza = fortaleza;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDebilidad() {
        return debilidad;
    }

    public String getFortaleza() {
        return fortaleza;
    }
    
}
