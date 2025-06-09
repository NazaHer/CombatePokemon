# Proyecto Combate Pokémon - React + Spring Boot

Este proyecto es una aplicación web que permite seleccionar un equipo de 3 Pokémon y comenzar un combate simulando un enfrentamiento contra una IA como en los videojuegos.  

## Tecnologías usadas
- Frontend: React.js  
- Backend: Spring Boot (API REST)  
- Comunicación: Axios para llamadas HTTP  
- Estilos: CSS personalizado

## Funcionalidades
- Listado dinámico de Pokémon obtenidos desde la API hacia una base de datos
- Selección de 3 Pokémon para iniciar combate  
- Validación para asegurar que se elijan 3 Pokémon  
- Visualización de los Pokémon activos al iniciar el combate

## Cómo ejecutar
1. Clonar el repositorio  
2. Ejecutar el backend Spring Boot en `http://localhost:8080`  
3. En la carpeta frontend, ejecutar:
   - `npm install`  
   - `npm run dev`  
4. Abrir `http://localhost:3000` en el navegador  

## Notas
- El backend debe estar corriendo para que el frontend funcione correctamente  
- Se usa CORS para permitir la comunicación entre frontend y backend  

