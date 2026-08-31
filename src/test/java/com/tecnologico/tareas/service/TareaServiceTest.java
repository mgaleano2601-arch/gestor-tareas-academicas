package com.tecnologico.tareas.service;

import com.tecnologico.tareas.model.Tarea;
import com.tecnologico.tareas.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    private TareaRepository repository;

    private TareaService service;

    @BeforeEach
    void configurar() {
        service = new TareaService(repository);
    }

    @Test
    void debeListarTodasLasTareasSinFiltro() {
        Tarea tarea = nuevaTarea("Preparar taller");
        when(repository.findAll()).thenReturn(List.of(tarea));

        List<Tarea> resultado = service.listar(null);

        assertEquals(1, resultado.size());
        assertEquals("Preparar taller", resultado.getFirst().getTitulo());
        verify(repository).findAll();
    }

    @Test
    void debeFiltrarTareasPorAsignatura() {
        when(repository.findByAsignaturaIgnoreCase("Web"))
                .thenReturn(List.of(nuevaTarea("Configurar CI")));

        List<Tarea> resultado = service.listar("Web");

        assertEquals(1, resultado.size());
        verify(repository).findByAsignaturaIgnoreCase("Web");
    }

    @Test
    void debeCrearUnaTareaComoRegistroNuevo() {
        Tarea tarea = nuevaTarea("Generar cobertura");
        tarea.setId(99L);
        when(repository.save(tarea)).thenReturn(tarea);

        Tarea resultado = service.crear(tarea);

        assertNull(resultado.getId());
        verify(repository).save(tarea);
    }

    @Test
    void debeActualizarTodosLosCampos() {
        Tarea actual = nuevaTarea("Titulo anterior");
        actual.setId(1L);
        Tarea cambios = nuevaTarea("Titulo nuevo");
        cambios.setDescripcion("Descripcion actualizada");
        cambios.setCompletada(true);
        when(repository.findById(1L)).thenReturn(Optional.of(actual));
        when(repository.save(actual)).thenReturn(actual);

        Tarea resultado = service.actualizar(1L, cambios);

        assertEquals("Titulo nuevo", resultado.getTitulo());
        assertEquals("Descripcion actualizada", resultado.getDescripcion());
        assertTrue(resultado.isCompletada());
    }

    @Test
    void debeFallarCuandoLaTareaNoExiste() {
        when(repository.findById(404L)).thenReturn(Optional.empty());

        NoSuchElementException error = assertThrows(
                NoSuchElementException.class,
                () -> service.consultar(404L)
        );

        assertTrue(error.getMessage().contains("404"));
    }

    @Test
    void debeEliminarUnaTareaExistente() {
        Tarea tarea = nuevaTarea("Eliminar tarea");
        tarea.setId(8L);
        when(repository.findById(8L)).thenReturn(Optional.of(tarea));

        service.eliminar(8L);

        verify(repository).deleteById(8L);
    }

    private Tarea nuevaTarea(String titulo) {
        return new Tarea(
                titulo,
                "Descripcion de prueba",
                "Web",
                LocalDate.now().plusDays(7)
        );
    }
}
