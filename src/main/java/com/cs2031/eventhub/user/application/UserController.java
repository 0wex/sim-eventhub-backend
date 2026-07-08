package com.cs2031.eventhub.user.application;

import com.cs2031.eventhub.auth.domain.AuthService;
import com.cs2031.eventhub.exception.ErrorResponse;
import com.cs2031.eventhub.user.domain.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios y Mi Agenda",
     description = "Registro (público) y gestión de Mi Agenda (protegido: Authorization: Bearer <token>; sin token devuelve 403).")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    record RegisterRequest(
            @Schema(description = "Correo electrónico válido", example = "vecino@municipalidad.gob.pe")
            String email,
            @Schema(description = "Contraseña del usuario", example = "miPassword123")
            String password,
            @Schema(description = "Nombre completo del usuario", example = "María García")
            String name) {}

    record TokenResponse(
            @Schema(description = "Token JWT. Enviarlo en el header Authorization: Bearer <token>",
                    example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2ZWNpbm9AbXVuaWNpcGFsaWRhZC5nb2IucGUifQ.abc123")
            String token) {}

    record AgendaRequest(
            @Schema(description = "ID del evento", example = "1")
            Long eventId) {}

    @Operation(summary = "Registrar usuario",
            description = "Crea una cuenta nueva y devuelve un token JWT listo para usar. Endpoint público.")
    @ApiResponse(responseCode = "200", description = "Usuario registrado")
    @ApiResponse(responseCode = "400", description = "Correo ya registrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest req) {
        String token = authService.register(req.email(), req.password(), req.name());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @Operation(summary = "Agregar evento a Mi Agenda")
    @ApiResponse(responseCode = "200", description = "Agregado (sin cuerpo)")
    @ApiResponse(responseCode = "400", description = "El evento no existe",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping("/agenda")
    public ResponseEntity<Void> addAgenda(@AuthenticationPrincipal UserDetails user,
                                          @RequestBody AgendaRequest req) {
        userService.addAgenda(user.getUsername(), req.eventId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remover evento de Mi Agenda")
    @ApiResponse(responseCode = "200", description = "Removido (sin cuerpo)")
    @DeleteMapping("/agenda")
    public ResponseEntity<Void> removeAgenda(@AuthenticationPrincipal UserDetails user,
                                             @RequestBody AgendaRequest req) {
        userService.removeAgenda(user.getUsername(), req.eventId());
        return ResponseEntity.ok().build();
    }
}
