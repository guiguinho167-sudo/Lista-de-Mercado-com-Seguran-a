package com.lista.lista;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SegurancaRepository extends JpaRepository<DadosSeguranca, Long> {
    Optional<DadosSeguranca> findByGmail(String gmail);

}
