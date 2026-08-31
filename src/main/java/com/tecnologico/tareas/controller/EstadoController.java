package com.tecnologico.tareas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/estado")
public class EstadoController {

    @GetMapping
    public Map<String, String> consultarEstado() {
        return Map.of(
                "estado", "activo",
                "aplicacion", "gestor-tareas-academicas"
        );
    }
}
