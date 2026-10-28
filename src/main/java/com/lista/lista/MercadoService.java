package com.lista.lista;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lista.lista.MercadoErros.UssuarioException;
import com.lista.lista.MercadoErros.UssuarioException.BuscasException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MercadoService {
    private final MercadoRepository mercadoRepository;
    private final SegurancaRepository segurancaRepository;

    public List<MercadoDados> listar(String gmail) {
        if (isAdmin()) {
            return mercadoRepository.findAll();
        }
        return mercadoRepository.findByUsuarioGmail(gmail);
    }

    private boolean isAdmin() {
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();

        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    @Transactional
    public MercadoResponse criar(MercadoRequest mercadoRequest, String gmail) {
        BigDecimal valor = mercadoRequest.getValor();
        BigDecimal limite = mercadoRequest.getLimite();

        BigDecimal totalAtual = mercadoRepository.somarComprasPorUsuario(gmail);
        BigDecimal valortotal = totalAtual.add(valor);

        if (valortotal.compareTo(limite) > 0) {

            throw new UssuarioException("O total das compras ultrapassa o limite estabelecido.");
        }

        MercadoDados salva = new MercadoDados();
        salva.setValor(valor);
        salva.setProduto(mercadoRequest.getProduto());
        salva.setLimite(limite);

        DadosSeguranca usuario = segurancaRepository.findByGmail(gmail)
                .orElseThrow(() -> new RuntimeException("Ussuario não encontrado"));

        salva.setUsuario(usuario);

        MercadoDados salvo = mercadoRepository.save(salva);
        return new MercadoResponse(salvo.getValor(), salvo.getProduto(), salvo.getLimite());
    }

    @Transactional
    public void delete(Long id, String gmail) {
        MercadoDados registro = mercadoRepository.findById(id)
                .orElseThrow(() -> new BuscasException("Registro de compra não encontrado"));

        if (!isAdmin() && !registro.getUsuario().getGmail().equals(gmail)) {
            throw new BuscasException("Você não pode deletar esse registro");
        }
        mercadoRepository.deleteById(id);
    }

    public List<MercadoResponse> produto(String produto, String gmail) {
        return mercadoRepository.findByProdutoAndUsuarioGmail(produto, gmail)
                .stream()
                .map(salvo -> new MercadoResponse(salvo.getValor(), salvo.getProduto(), salvo.getLimite()))
                .toList();
    }

    public List<MercadoResponse> valor(BigDecimal valor, String gmail) {
        return mercadoRepository.findByValorAndUsuarioGmail(valor, gmail)
                .stream()
                .map(salvo -> new MercadoResponse(salvo.getValor(), salvo.getProduto(), salvo.getLimite()))
                .toList();
    }

    public List<MercadoResponse> limite(BigDecimal limite, String gmail) {
        return mercadoRepository.findByLimiteAndUsuarioGmail(limite, gmail)
                .stream()
                .map(salvo -> new MercadoResponse(salvo.getValor(), salvo.getProduto(), salvo.getLimite()))
                .toList();
    }

}
