package com.usuario.service.usuario_service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.usuario_service.entidades.Usuario;
import com.usuario.service.usuario_service.feignClients.CarroFeignClient;
import com.usuario.service.usuario_service.feignClients.MotoFeignClient;
import com.usuario.service.usuario_service.modelos.Carro;
import com.usuario.service.usuario_service.modelos.Moto;
import com.usuario.service.usuario_service.repositorios.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarroFeignClient carroFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    public List<Usuario> getAll(){
        return userRepository.findAll();
    }

    public Usuario getUsuarioById(int id){
        return userRepository.findById(id).orElse(null);
    }
    @Transactional
    public Usuario addUsuario(Usuario user){
        Usuario newUser = userRepository.save(user);
        return newUser;
    }

    
    public List<Carro> getCarros(int usuarioId){
        List<Carro> carros = restTemplate.getForObject("http://carro-service/usuario/" + usuarioId, List.class);
        return carros;
    }

    public List<Moto> getMotos(int usuarioId){
        List<Moto> motos = restTemplate.getForObject("http://moto-service/usuario/" + usuarioId, List.class);
        return motos;
    }

    public Carro saveCarro(int usuarioId, Carro carro){
        carro.setUsuarioId(usuarioId);
        Carro nuevoCarro = carroFeignClient.save(carro);
        return nuevoCarro; 
    }

    public Moto saveMoto(int usuarioId, Moto moto){
        moto.setUsuarioId(usuarioId);
        Moto nuevaMoto = motoFeignClient.save(moto);
        return nuevaMoto; 
    }

    public Map<String, Object> getUsuarioAndVehiculos(int usuarioId){
        Map<String, Object> resultado = new HashMap<>();
        Usuario usuario = userRepository.findById(usuarioId).orElseThrow();
        if (usuario == null) {
            resultado.put("mensaje", "El usuario no existe");
            return resultado;
        }

        resultado.put("usuario", usuario);
        List<Carro> carros = carroFeignClient.getCarros(usuarioId);
        if (carros.isEmpty()) {
            resultado.put("mensaje", "El resultado no tiene carros");
        }else{
            resultado.put("carros", carros);
        }
        List<Moto> motos = motoFeignClient.getMotos(usuarioId);
        if (motos.isEmpty()) {
             resultado.put("mensaje", "El resultado no tiene motos");
        }else{
            resultado.put("motos", motos);
        }
        return resultado;
    }


}
