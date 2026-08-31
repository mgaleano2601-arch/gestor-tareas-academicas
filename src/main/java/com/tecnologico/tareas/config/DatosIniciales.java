package com.tecnologico.tareas.config;

import com.tecnologico.tareas.model.Tarea;
import com.tecnologico.tareas.repository.TareaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarDatos(TareaRepository repository) {
        return args -> {
            repository.save(new Tarea(
                    "Preparar diagnostico tecnico",
                    "Revisar arquitectura, configuracion y seguridad",
                    "Desarrollo Web Avanzado",
                    LocalDate.now().plusDays(7)
            ));
            repository.save(new Tarea(
                    "Exponer proyecto",
                    "Preparar una demostracion corta de la API",
                    "Desarrollo Web Avanzado",
                    LocalDate.now().plusDays(14)
            ));
        };
    }
}
