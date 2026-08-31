# Gestor de Tareas Academicas

Proyecto academico Spring Boot que expone una API REST para registrar, consultar,
actualizar y eliminar tareas de estudiantes.

## Tecnologias

- Java 21
- Spring Boot 4.0.8
- Spring Web MVC
- Spring Data JPA
- Bean Validation
- H2 en memoria
- Maven

## Ejecucion con Docker

```bash
docker compose up --build -d
```

La API queda disponible en `http://localhost:8088/api/tareas`.

## Endpoints

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | `/api/tareas` | Lista tareas; acepta `?asignatura=` |
| GET | `/api/tareas/{id}` | Consulta una tarea |
| POST | `/api/tareas` | Crea una tarea |
| PUT | `/api/tareas/{id}` | Actualiza una tarea |
| DELETE | `/api/tareas/{id}` | Elimina una tarea |

Ejemplo de cuerpo JSON:

```json
{
  "titulo": "Preparar exposicion",
  "descripcion": "Organizar las diapositivas",
  "asignatura": "Desarrollo Web Avanzado",
  "fechaEntrega": "2026-09-15",
  "completada": false
}
```

## Nota academica

Este proyecto es el objeto de estudio del diagnostico tecnico. Su estructura inicial
es deliberadamente sencilla para que los hallazgos y la posterior refactorizacion
puedan explicarse durante el taller.

## Integracion continua

El workflow `.github/workflows/ci.yml` se ejecuta en cada push a `main`, `develop`
o `diagnostico-arquitectura`, y en cada Pull Request dirigido a `main`.

El pipeline:

1. Configura Java 21.
2. Compila el proyecto con Maven Wrapper.
3. Ejecuta las pruebas JUnit.
4. Genera cobertura con JaCoCo.
5. Exige al menos 60% de cobertura de instrucciones.
6. Publica los reportes JaCoCo y JUnit como artefactos.

Verificacion local del 31 de agosto de 2026: 11 pruebas correctas, 0 fallos,
88.32% de cobertura de instrucciones y 89.23% de cobertura de lineas.
