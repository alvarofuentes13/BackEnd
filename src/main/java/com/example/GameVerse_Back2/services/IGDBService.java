package com.example.GameVerse_Back2.services;

import com.example.GameVerse_Back2.dto.IGDBGameDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class IGDBService {

    @Value("${igdb.api.url}")
    private String igdbApiUrl; // URL de la API de IGDB

    @Value("${igdb.client-id}")
    private String clientId; // ID del cliente para la autenticación

    @Value("${igdb.token}")
    private String token; // Token de acceso para la API

    private final RestTemplate restTemplate = new RestTemplate(); // Cliente REST para realizar solicitudes
    private final ObjectMapper objectMapper = new ObjectMapper(); // Objeto para convertir JSON a objetos Java

    // Método para obtener una lista de juegos con un límite específico
    public List<IGDBGameDTO> getGames(int limit) {
        HttpHeaders headers = createHeaders(); // Crear encabezados para la solicitud
        String query = buildQuery(limit); // Construir la consulta
        HttpEntity<String> request = new HttpEntity<>(query, headers); // Crear entidad de solicitud

        try {
            String responseBody = restTemplate.postForObject(igdbApiUrl, request, String.class); // Realizar la solicitud
            return parseResponse(responseBody); // Analizar la respuesta y devolver la lista de juegos
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Devolver una lista vacía en caso de error
        }
    }

    // Método para buscar juegos por un término de búsqueda
    public List<IGDBGameDTO> searchGames(String query) {
        HttpHeaders headers = createHeaders(); // Crear encabezados para la solicitud

        String body = String.format("""
        search "%s";
        fields name, summary, first_release_date, genres.name, cover.url, involved_companies.company.name;
        limit 20;
    """, query); // Construir el cuerpo de la consulta

        HttpEntity<String> request = new HttpEntity<>(body, headers); // Crear entidad de solicitud

        try {
            String responseBody = restTemplate.postForObject(igdbApiUrl, request, String.class); // Realizar la solicitud
            return parseResponse(responseBody); // Analizar la respuesta y devolver la lista de juegos
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Devolver una lista vacía en caso de error
        }
    }

    // Método para obtener un juego por su ID
    public IGDBGameDTO getGameById(Long id) {
        HttpHeaders headers = createHeaders(); // Crear encabezados para la solicitud
        String query = String.format("""
                    fields name, summary, first_release_date, genres.name, cover.url, involved_companies.company.name;
                    where id = %d;
                    limit 1;
                """, id); // Construir la consulta para un juego específico
        HttpEntity<String> request = new HttpEntity<>(query, headers); // Crear entidad de solicitud

        try {
            String responseBody = restTemplate.postForObject(igdbApiUrl, request, String.class); // Realizar la solicitud
            List<IGDBGameDTO> juegos = parseResponse(responseBody);
            return juegos.isEmpty() ? null : juegos.get(0); // Devolver el primer juego o null si no hay resultados
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Devolver null en caso de error
        }
    }

    // Método para crear los encabezados de la solicitud
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", clientId); // Establecer el Client-ID
        headers.set("Authorization", "Bearer " + token); // Establecer el token de autorización
        headers.setContentType(MediaType.TEXT_PLAIN); // Establecer el tipo de contenido
        return headers;
    }

    // Método para construir la consulta para obtener juegos
    private String buildQuery(int limit) {
        return String.format("""
                    fields name, summary, first_release_date, genres.name, cover.url, involved_companies.company.name;
                    where rating > 85 & platforms = 48;
                    limit %d;
                    sort popularity desc;
                """, limit); // Construir la consulta con los parámetros especificados
    }

    // Método para analizar la respuesta JSON y convertirla en una lista de IGDBGameDTO
    private List<IGDBGameDTO> parseResponse(String responseBody) {
        List<IGDBGameDTO> games = new ArrayList<>();
        if (responseBody != null) {
            try {
                JsonNode root = objectMapper.readTree(responseBody); // Leer el árbol JSON
                for (JsonNode node : root) { // Iterar sobre los nodos
                    IGDBGameDTO dto = new IGDBGameDTO(); // Crear un nuevo DTO
                    dto.setId(node.path("id").asLong()); // Establecer el ID
                    dto.setName(node.path("name").asText()); // Establecer el nombre
                    dto.setSummary(node.path("summary").asText("No description")); // Establecer el resumen

                    long releaseTs = node.path("first_release_date").asLong(); // Obtener la fecha de lanzamiento
                    dto.setReleaseDate(releaseTs > 0 ? formatDate(releaseTs) : "Unknown"); // Formatear la fecha

                    dto.setGenre(extractGenre(node)); // Extraer el género
                    dto.setCoverUrl(extractCoverUrl(node)); // Extraer la URL de la portada
                    dto.setDeveloper(extractDeveloper(node)); // Extraer el desarrollador

                    games.add(dto); // Agregar el DTO a la lista
                }
            } catch (Exception e) {
                e.printStackTrace(); // Manejo de excepciones
            }
        }
        return games; // Devolver la lista de juegos
    }

    // Método para formatear la fecha de lanzamiento
    private String formatDate(long timestamp) {
        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Formato de fecha
    }

    // Método para extraer el género del juego
    private String extractGenre(JsonNode node) {
        JsonNode genres = node.path("genres");
        return genres.isArray() && !genres.isEmpty() ? genres.get(0).path("name").asText() : "No genre"; // Retornar el nombre del género
    }

    // Método para extraer la URL de la portada del juego
    private String extractCoverUrl(JsonNode node) {
        return node.path("cover").has("url")
                ? node.path("cover").path("url").asText().replace("t_thumb", "t_cover_big") // Reemplazar para obtener la imagen grande
                : "";
    }

    // Método para extraer el desarrollador del juego
    private String extractDeveloper(JsonNode node) {
        JsonNode companies = node.path("involved_companies");
        return companies.isArray() && !companies.isEmpty()
                ? companies.get(0).path("company").path("name").asText() // Retornar el nombre de la compañía
                : "Unknown"; // Retornar "Unknown" si no hay desarrollador
    }
}
