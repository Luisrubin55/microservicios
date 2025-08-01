package com.moto.service.moto_service.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moto.service.moto_service.entidades.Moto;
import com.moto.service.moto_service.servicios.MotoService;

@RestController
@RequestMapping("/moto")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping()
    public ResponseEntity<List<Moto>> listarMotos() {
        List<Moto> motos = motoService.getAll();
        if (motos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> getCarroById(@PathVariable int id) {
        Moto motoById = motoService.getMotoById(id);
        if (motoById == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(motoById);
    }

    @PostMapping()
    public ResponseEntity<Moto> addCarro(@RequestBody Moto moto) {
        Moto motoSave = motoService.addMoto(moto);
        return ResponseEntity.ok(motoSave);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Moto>> getMotosByIdUsuario(@PathVariable int usuarioId) {
        List<Moto> motos = motoService.getMotoByIdUsuario(usuarioId);
        if (motos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(motos);
    }
}
