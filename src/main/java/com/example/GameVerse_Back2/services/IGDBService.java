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
    private String igdbApiUrl;

    @Value("${igdb.client-id}")
    private String clientId;

    @Value("${igdb.token}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<IGDBGameDTO> getGames(int limit) {
        HttpHeaders headers = createHeaders();
        String query = buildQuery(limit);
        HttpEntity<String> request = new HttpEntity<>(query, headers);

        try {
            String responseBody = restTemplate.postForObject(igdbApiUrl, request, String.class);
            return parseResponse(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Devolver una lista vacía en caso de error
        }
    }

    public List<IGDBGameDTO> searchGames(String query) {
        HttpHeaders headers = createHeaders();

        String body = String.format("""
        search "%s";
        fields name, summary, first_release_date, genres.name, cover.url, involved_companies.company.name;
        limit 20;
    """, query);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        try {
            String responseBody = restTemplate.postForObject(igdbApiUrl, request, String.class);
            return parseResponse(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public IGDBGameDTO getGameById(Long id) {
        HttpHeaders headers = createHeaders();
        String query = String.format("""
                    fields name, summary, first_release_date, genres.name, cover.url, involved_companies.company.name;
                    where id = %d;
                    limit 1;
                """, id);
        HttpEntity<String> request = new HttpEntity<>(query, headers);

        try {
            String responseBody = restTemplate.postForObject(igdbApiUrl, request, String.class);
            List<IGDBGameDTO> juegos = parseResponse(responseBody);
            return juegos.isEmpty() ? null : juegos.getFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", clientId);
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.TEXT_PLAIN);
        return headers;
    }

    private String buildQuery(int limit) {
        return String.format("""
                    fields name, summary, first_release_date, genres.name, cover.url, involved_companies.company.name;
                    where rating > 85 & platforms = 48;
                    limit %d;
                    sort popularity desc;
                """, limit);
    }

    private List<IGDBGameDTO> parseResponse(String responseBody) {
        List<IGDBGameDTO> games = new ArrayList<>();
        if (responseBody != null) {
            try {
                JsonNode root = objectMapper.readTree(responseBody);
                for (JsonNode node : root) {
                    IGDBGameDTO dto = new IGDBGameDTO();
                    dto.setId(node.path("id").asLong());
                    dto.setName(node.path("name").asText());
                    dto.setSummary(node.path("summary").asText("No description"));

                    long releaseTs = node.path("first_release_date").asLong();
                    dto.setReleaseDate(releaseTs > 0 ? formatDate(releaseTs) : "Unknown");

                    dto.setGenre(extractGenre(node));
                    dto.setCoverUrl(extractCoverUrl(node));
                    dto.setDeveloper(extractDeveloper(node));

                    games.add(dto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return games;
    }

    private String formatDate(long timestamp) {
        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private String extractGenre(JsonNode node) {
        JsonNode genres = node.path("genres");
        return genres.isArray() && !genres.isEmpty() ? genres.get(0).path("name").asText() : "No genre";
    }

    private String extractCoverUrl(JsonNode node) {
        return node.path("cover").has("url")
                ? node.path("cover").path("url").asText().replace("t_thumb", "t_cover_big")
                : "";
    }

    private String extractDeveloper(JsonNode node) {
        JsonNode companies = node.path("involved_companies");
        return companies.isArray() && !companies.isEmpty()
                ? companies.get(0).path("company").path("name").asText()
                : "Unknown";
    }


}
