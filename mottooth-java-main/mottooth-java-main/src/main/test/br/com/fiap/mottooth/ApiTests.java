package br.com.fiap.mottooth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTests {

    private static String BASE_URL;
    private static String USER;
    private static String PASS;
    private static String BEARER;

    @BeforeAll
    static void setup() {
        // Permite sobrescrever por variável de ambiente (útil no CI)
        BASE_URL = getenvOrDefault("API_BASE_URL", "http://localhost:8080");
        USER     = getenvOrDefault("API_USER",     "joao@ex.com");
        PASS     = getenvOrDefault("API_PASS",     "fiap25");

        RestAssured.baseURI = BASE_URL;
        BEARER = tryLoginAndGetBearer();

        System.out.println("Base URL........: " + BASE_URL);
        System.out.println("User for login..: " + USER);
        System.out.println("Token acquired?.: " + (BEARER != null));
    }

    private static String getenvOrDefault(String key, String def) {
        String v = System.getenv(key);
        return (v == null || v.isBlank()) ? def : v;
    }

    /** Faz login usando DTO {username,password}. Falha rápido se não retornar 200. */
    private static String tryLoginAndGetBearer() {
        String body = """
            { "username": "%s", "password": "%s" }
        """.formatted(USER, PASS);

        Response resp = given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/api/auth/login")
                .then()
                .extract()
                .response();

        if (resp.statusCode() != 200) {
            throw new AssertionError(
                    "Login falhou: HTTP " + resp.statusCode() + " | Body: " + resp.asString()
            );
        }

        JsonPath jp = resp.jsonPath();
        String token = jp.getString("token");
        assertNotNull(token, "Resposta de login não contém 'token'.");
        assertFalse(token.isBlank(), "Token vazio.");

        return "Bearer " + token;
    }

    // ---------- AUTENTICAÇÃO ----------
    @Test
    @Order(1)
    @DisplayName("Auth | Deve autenticar e retornar token")
    void testLogin_JWT() {
        assertNotNull(BEARER, "Token não obtido.");
    }

    // ---------- MOTOS ----------
    @Test
    @Order(2)
    @DisplayName("Motos | Deve listar todas as motos (200)")
    void testListMotos() {
        assumeTrue(BEARER != null, "Sem token — teste pulado.");
        Response resp = given()
                .header("Authorization", BEARER)
                .get("/api/motos")
                .then()
                .extract()
                .response();

        assertEquals(200, resp.statusCode(), "Listar motos deve retornar 200.");
    }

    @Test
    @Order(3)
    @DisplayName("Motos | Deve buscar moto por ID (200 ou 404)")
    void testGetMotoById() {
        assumeTrue(BEARER != null, "Sem token — teste pulado.");
        Response resp = given()
                .header("Authorization", BEARER)
                .get("/api/motos/{id}", 1)
                .then()
                .extract()
                .response();

        assertTrue(resp.statusCode() == 200 || resp.statusCode() == 404,
                "Esperado 200 (existe) ou 404 (não existe). Veio: " + resp.statusCode());
    }

    // ---------- BEACONS ----------
    @Test
    @Order(4)
    @DisplayName("Beacons | Deve listar todos (200)")
    void testListBeacons() {
        assumeTrue(BEARER != null, "Sem token — teste pulado.");
        Response resp = given()
                .header("Authorization", BEARER)
                .get("/api/beacons")
                .then()
                .extract()
                .response();

        assertEquals(200, resp.statusCode(), "Listar beacons deve retornar 200.");
    }

    @Test
    @Order(5)
    @DisplayName("Beacons | Deve buscar por ID (200 ou 404)")
    void testGetBeaconById() {
        assumeTrue(BEARER != null, "Sem token — teste pulado.");
        Response resp = given()
                .header("Authorization", BEARER)
                .get("/api/beacons/{id}", 1)
                .then()
                .extract()
                .response();

        assertTrue(resp.statusCode() == 200 || resp.statusCode() == 404,
                "Esperado 200 ou 404. Veio: " + resp.statusCode());
    }

    // ---------- LOCALIZAÇÕES ----------
    @Test
    @Order(6)
    @DisplayName("Localizações | Deve listar todas (200)")
    void testListLocalizacoes() {
        assumeTrue(BEARER != null, "Sem token — teste pulado.");
        Response resp = given()
                .header("Authorization", BEARER)
                .get("/api/localizacoes")
                .then()
                .extract()
                .response();

        assertEquals(200, resp.statusCode(), "Listar localizações deve retornar 200.");
    }

    @Test
    @Order(7)
    @DisplayName("Localizações | Deve buscar por ID (200 ou 404)")
    void testGetLocalizacaoById() {
        assumeTrue(BEARER != null, "Sem token — teste pulado.");
        Response resp = given()
                .header("Authorization", BEARER)
                .get("/api/localizacoes/{id}", 1)
                .then()
                .extract()
                .response();

        assertTrue(resp.statusCode() == 200 || resp.statusCode() == 404,
                "Esperado 200 ou 404. Veio: " + resp.statusCode());
    }
}
