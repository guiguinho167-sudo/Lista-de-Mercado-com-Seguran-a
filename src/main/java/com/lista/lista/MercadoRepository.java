package com.lista.lista;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface MercadoRepository extends JpaRepository<MercadoDados, Long> {

    List<MercadoDados> findByUsuarioGmail(String gmail);

    @Transactional
    List<MercadoDados> findByValorAndUsuarioGmail(BigDecimal valor, String gmail);

    @Transactional
    List<MercadoDados> findByProdutoAndUsuarioGmail(String produto, String gmail);

    @Transactional
    List<MercadoDados> findByLimiteAndUsuarioGmail(BigDecimal limite, String gmail);

    @Query("""
            SELECT COALESCE(SUM(m.valor), 0)
            FROM MercadoDados m
            WHERE m.usuario.gmail = :gmail
            """)
    BigDecimal somarComprasPorUsuario(String gmail);

}
