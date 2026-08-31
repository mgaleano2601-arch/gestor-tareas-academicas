package com.tecnologico.tareas.repository;

import com.tecnologico.tareas.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByAsignaturaIgnoreCase(String asignatura);
}
