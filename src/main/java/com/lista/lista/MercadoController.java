package com.lista.lista;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/mercado")
@RequiredArgsConstructor
public class MercadoController {
    private final MercadoService mercadoService;

    @GetMapping
    public List<MercadoDados> listar(Principal principal) {
        return mercadoService.listar(principal.getName());
    }

    @PostMapping("/usar")
    @ResponseStatus(HttpStatus.CREATED)
    public MercadoResponse criar(@RequestBody MercadoRequest request, Principal principal) {
        return mercadoService.criar(request, principal.getName());
    }

    @DeleteMapping("/{id}/deletar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id, Principal principal) {
        mercadoService.delete(id, principal.getName());
    }

    @GetMapping("/produto")
    public List<MercadoResponse> buscarProduto(@RequestParam String produto, Principal principal) {
        return mercadoService.produto(produto, principal.getName());
    }

    @GetMapping("/valor")
    public List<MercadoResponse> buscarValor(
            @RequestParam BigDecimal valor,
            Principal principal) {
        return mercadoService.valor(valor, principal.getName());
    }

    @GetMapping("/limite")
    public List<MercadoResponse> buscarLimite(@RequestParam BigDecimal limite, Principal principal) {
        return mercadoService.limite(limite, principal.getName());
    }
}