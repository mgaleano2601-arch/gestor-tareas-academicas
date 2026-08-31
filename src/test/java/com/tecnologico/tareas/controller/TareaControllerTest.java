package com.tecnologico.tareas.controller;

import com.tecnologico.tareas.model.Tarea;
import com.tecnologico.tareas.service.TareaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TareaController.class)
class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TareaService tareaService;

    @Test
    void debeListarTareasComoJson() throws Exception {
        Tarea tarea = nuevaTarea();
        tarea.setId(1L);
        when(tareaService.listar(null)).thenReturn(List.of(tarea));

        mockMvc.perform(get("/api/tareas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Preparar pipeline"));
    }

    @Test
    void debeCrearUnaTareaValida() throws Exception {
        Tarea creada = nuevaTarea();
        creada.setId(2L);
        when(tareaService.crear(any(Tarea.class))).thenReturn(creada);

        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titulo": "Preparar pipeline",
                                  "descripcion": "Configurar GitHub Actions",
                                  "asignatura": "Web",
                                  "fechaEntrega": "2099-09-15",
                                  "completada": false
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void debeRechazarUnaTareaInvalida() throws Exception {
        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titulo": "",
                                  "asignatura": "",
                                  "fechaEntrega": "2020-01-01"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void debeEliminarUnaTarea() throws Exception {
        mockMvc.perform(delete("/api/tareas/3"))
                .andExpect(status().isNoContent());

        verify(tareaService).eliminar(3L);
    }

    private Tarea nuevaTarea() {
        return new Tarea(
                "Preparar pipeline",
                "Configurar GitHub Actions",
                "Web",
                LocalDate.of(2099, 9, 15)
        );
    }
}
