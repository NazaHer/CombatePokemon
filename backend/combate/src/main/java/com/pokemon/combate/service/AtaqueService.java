package com.pokemon.combate.service;

import com.pokemon.combate.model.Ataque;
import com.pokemon.combate.repository.InAtaqueRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtaqueService implements InAtaqueService{
    
    @Autowired
    private InAtaqueRepository ataqueRepo;

    @Override
    public void saveAtaque(Ataque a) {
        ataqueRepo.save(a);
    }

    @Override
    public List<Ataque> getAtaques() {
        return ataqueRepo.findAll();
    }
    
    
    
}
