# Guia corta para presentar el taller

## 1. Introduccion

El proyecto se llama Gestor de Tareas Academicas. Es una API REST construida con
Java 21 y Spring Boot 4.0.8 para crear, consultar, actualizar y eliminar tareas.

## 2. Demostracion

Levantar la aplicacion:

```bash
docker compose up --build -d
```

Abrir en el navegador:

```text
http://localhost:8088/api/tareas
```

La respuesta debe contener dos tareas de ejemplo.

## 3. Arquitectura actual

- `TareaController` recibe las peticiones HTTP.
- `TareaRepository` accede a H2 mediante Spring Data JPA.
- `Tarea` es la entidad almacenada y tambien se devuelve como JSON.
- No existe capa Service ni DTO; este es el principal hallazgo de arquitectura.

## 4. Pruebas que sustentan el diagnostico

- `GET /api/tareas` responde 200.
- `POST /api/tareas` con datos invalidos responde 400.
- `GET /api/tareas/999` responde 500, aunque deberia ser 404.
- `/swagger-ui.html` responde 404 porque no hay OpenAPI.
- `/h2-console` responde 200 y esta expuesta sin seguridad.
- Maven ejecuta una sola prueba de arranque y finaliza correctamente.

## 5. Conclusion para decir en la exposicion

El proyecto funciona como prototipo, pero su madurez es deficiente. Primero se
debe agregar Service, DTO y manejo global de excepciones. Despues se deben separar
los perfiles, cerrar CORS y H2, agregar seguridad, OpenAPI y pruebas funcionales.

## 6. Antes de entregar

1. Crear un repositorio en GitHub o GitLab.
2. Publicar la rama `diagnostico-arquitectura`.
3. Escribir el enlace en el espacio reservado del PDF.
4. Completar el grupo PA si el docente lo solicita.

No se creo ningun commit automaticamente. Para registrar el proyecto cuando estes
lista, revisa primero los archivos y luego ejecuta:

```bash
git add pom.xml src Dockerfile compose.yaml README.md GUIA-TALLER.md .mvn mvnw mvnw.cmd .gitignore .gitattributes
git commit -m "Crear gestor de tareas academicas para diagnostico tecnico"
```
