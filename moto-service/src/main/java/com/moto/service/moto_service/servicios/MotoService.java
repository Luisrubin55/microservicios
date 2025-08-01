package com.moto.service.moto_service.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moto.service.moto_service.entidades.Moto;
import com.moto.service.moto_service.repositorio.MotoRepository;

import jakarta.transaction.Transactional;

@Service
public class MotoService {
    
    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> getAll() {
        return motoRepository.findAll();
    }

    public Moto getMotoById(int id) {
        return motoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Moto addMoto(Moto moto) {
        Moto newMoto = motoRepository.save(moto);
        return newMoto;
    }

    public List<Moto> getMotoByIdUsuario(int usuarioId) {
        return motoRepository.findByUsuarioId(usuarioId);
    }

}
