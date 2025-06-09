package com.pokemon.combate.controller;

import com.pokemon.combate.dto.TurnoRequest;
import com.pokemon.combate.model.Combate;
import com.pokemon.combate.service.InCombateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CombateController {
    
    @Autowired
    private InCombateService combServ;
    
    //Endpoint para iniciar el combate
    @PostMapping("/combate/iniciar")
    public ResponseEntity<Combate> iniciarCombate(@RequestBody List<Long> idsPokemonJugador){
        try{
            Combate combate = combServ.iniciarCombate(idsPokemonJugador);
            return ResponseEntity.ok(combate);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PostMapping("/combate/turno")
    public ResponseEntity<Combate> procesarTurno(@RequestBody Long idAtaqueJugador){
        Combate combateActual = combServ.procesarTurno(idAtaqueJugador);
        return ResponseEntity.ok(combateActual);
    }
    
    @PostMapping("/combate/cambiar")
    public ResponseEntity<Combate> cambiarPokemon(@RequestBody int nuevoIndice) {
        Combate resultado = combServ.cambiarPokemonActivoJugador(nuevoIndice);
        return ResponseEntity.ok(resultado);
    }

}
