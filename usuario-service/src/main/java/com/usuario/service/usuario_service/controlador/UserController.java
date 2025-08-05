package com.usuario.service.usuario_service.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.usuario_service.entidades.Usuario;
import com.usuario.service.usuario_service.modelos.Carro;
import com.usuario.service.usuario_service.modelos.Moto;
import com.usuario.service.usuario_service.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> users = userService.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserioById(@PathVariable int id) {
        Usuario userById = userService.getUsuarioById(id);
        if (userById == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userById);
    } 

    @PostMapping()
    public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario user) {
       Usuario userSave = userService.addUsuario(user);
       return ResponseEntity.ok(userSave);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackGetCarros")
    @GetMapping("/carros/{usuarioId}")
    public ResponseEntity<List<Carro>> listarCarros(@PathVariable int usuarioId) {
        Usuario usuario = userService.getUsuarioById(usuarioId);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Carro> carros = userService.getCarros(usuarioId);
        return ResponseEntity.ok(carros);
    }
    
    @CircuitBreaker(name = "motodCB", fallbackMethod = "fallBackGetMotos")
    @GetMapping("/motos/{usuarioId}")
    public ResponseEntity<List<Moto>> listarMotos(@PathVariable int usuarioId) {
        Usuario usuario = userService.getUsuarioById(usuarioId);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Moto> motos = userService.getMotos(usuarioId);
        return ResponseEntity.ok(motos);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveCarro")
    @PostMapping("/carro/{usuarioId}")
    public ResponseEntity<Carro> guardarCarro(@PathVariable int usuarioId, @RequestBody Carro carro) {
        Carro nuevoCarro = userService.saveCarro(usuarioId, carro);
        return ResponseEntity.ok(nuevoCarro);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveMoto")
    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<Moto> guardarMoto(@PathVariable int usuarioId, @RequestBody Moto moto) {
        Moto nuevaMoto = userService.saveMoto(usuarioId, moto);
        return ResponseEntity.ok(nuevaMoto);
    }
    
    @CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetTodos")
    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable int usuarioId) {
        Map<String, Object> resultado = userService.getUsuarioAndVehiculos(usuarioId);
        return ResponseEntity.ok(resultado);
    }
    
    private ResponseEntity<List<Carro>> fallBackGetCarros(@PathVariable int usuarioId, RuntimeException exception){
        return new ResponseEntity("El usuario : " + usuarioId + "tiene los carros en el taller", HttpStatus.OK);
    }

    private ResponseEntity<List<Carro>> fallBackSaveCarro(@PathVariable int usuarioId, @RequestBody Carro carro, RuntimeException exception){
        return new ResponseEntity("El usuario : " + usuarioId + " no tiene dinero para los carros ", HttpStatus.OK);
    }
    
    private ResponseEntity<List<Moto>> fallBackGetMotos(@PathVariable int usuarioId, RuntimeException exception){
        return new ResponseEntity("El usuario : " + usuarioId + "tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<List<Moto>> fallBackSaveMoto(@PathVariable int usuarioId, @RequestBody Moto moto, RuntimeException exception){
        return new ResponseEntity("El usuario : " + usuarioId + " no tiene dinero para las motos ", HttpStatus.OK);
    }

    private ResponseEntity<List<Moto>> fallBackGetTodos(@PathVariable int usuarioId, @RequestBody Moto moto, RuntimeException exception){
        return new ResponseEntity("El usuario : " + usuarioId + " tiene los vehiculos en el taller ", HttpStatus.OK);
    }
}
