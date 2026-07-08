package com.cs2031.eventhub.auth.application;

import com.cs2031.eventhub.auth.domain.AuthService;
import com.cs2031.eventhub.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Inicio de sesión. Devuelve un token JWT.")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    record LoginRequest(
            @Schema(description = "Correo registrado", example = "vecino@municipalidad.gob.pe")
            String email,
            @Schema(description = "Contraseña del usuario", example = "miPassword123")
            String password) {}

    record TokenResponse(
            @Schema(description = "Token JWT. Enviarlo en el header Authorization: Bearer <token>",
                    example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2ZWNpbm9AbXVuaWNpcGFsaWRhZC5nb2IucGUifQ.abc123")
            String token) {}

    @Operation(summary = "Iniciar sesión",
            description = "Valida las credenciales y devuelve un token JWT.")
    @ApiResponse(responseCode = "200", description = "Login exitoso")
    @ApiResponse(responseCode = "400", description = "Credenciales inválidas",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = "{\"error\": \"Invalid credentials\"}")))
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
        String token = authService.login(req.email(), req.password());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
