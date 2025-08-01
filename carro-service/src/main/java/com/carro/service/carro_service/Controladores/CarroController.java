package com.carro.service.carro_service.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carro.service.carro_service.entidades.Carro;
import com.carro.service.carro_service.servicios.CarroService;

@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping()
    public ResponseEntity<List<Carro>> listarCarros() {
        List<Carro> carros = carroService.getAll();
        if (carros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> getCarroById(@PathVariable int id) {
        Carro carroById = carroService.getCarroById(id);
        if (carroById == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carroById);
    }

    @PostMapping()
    public ResponseEntity<Carro> addCarro(@RequestBody Carro carro) {
        Carro carroSave = carroService.addCarro(carro);
        return ResponseEntity.ok(carroSave);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carro>> getCarrosByIdUsuario (@PathVariable int usuarioId) {
        List<Carro> carros = carroService.getCarroByIdUsuario(usuarioId);
        if (carros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carros);
    }

}
