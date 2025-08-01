package com.carro.service.carro_service.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carro.service.carro_service.entidades.Carro;
import com.carro.service.carro_service.repositorios.CarroRepository;

import jakarta.transaction.Transactional;

@Service
public class CarroService {
    
    @Autowired
    private CarroRepository carroRepository;

     public List<Carro> getAll(){
        return carroRepository.findAll();
    }

    public Carro getCarroById(int id){
        return carroRepository.findById(id).orElse(null);
    }

    @Transactional
    public Carro addCarro(Carro user){
        Carro newUser = carroRepository.save(user);
        return newUser;
    }

    public List<Carro> getCarroByIdUsuario(int usuarioId){
        return carroRepository.findByUsuarioId(usuarioId);
    }

}
