package com.lista.lista;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SecurityController {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SegurancaRepository segurancaRepository;
    private final JwtToken jwtToken;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Request request) {

        DadosSeguranca usuario = new DadosSeguranca();

        usuario.setGmail(request.gmail());
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        usuario.setRole("ADMIN");
        segurancaRepository.save(usuario);

        return ResponseEntity.ok("Usuario Logado");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody Request request) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.gmail(), request.senha()));
            return ResponseEntity.ok(jwtToken.gerartoken(request.gmail()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Gmail ou Senha invalida");

        }

    }

}
