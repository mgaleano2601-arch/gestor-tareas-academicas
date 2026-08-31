package com.tecnologico.tareas.service;

import com.tecnologico.tareas.model.Tarea;
import com.tecnologico.tareas.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public List<Tarea> listar(String asignatura) {
        if (asignatura != null && !asignatura.isBlank()) {
            return tareaRepository.findByAsignaturaIgnoreCase(asignatura);
        }
        return tareaRepository.findAll();
    }

    public Tarea consultar(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe la tarea con id " + id));
    }

    public Tarea crear(Tarea tarea) {
        tarea.setId(null);
        return tareaRepository.save(tarea);
    }

    public Tarea actualizar(Long id, Tarea datos) {
        Tarea tarea = consultar(id);
        tarea.setTitulo(datos.getTitulo());
        tarea.setDescripcion(datos.getDescripcion());
        tarea.setAsignatura(datos.getAsignatura());
        tarea.setFechaEntrega(datos.getFechaEntrega());
        tarea.setCompletada(datos.isCompletada());
        return tareaRepository.save(tarea);
    }

    public void eliminar(Long id) {
        consultar(id);
        tareaRepository.deleteById(id);
    }
}
