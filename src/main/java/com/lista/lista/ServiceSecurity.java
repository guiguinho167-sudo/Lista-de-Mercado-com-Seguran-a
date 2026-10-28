package com.lista.lista;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceSecurity implements UserDetailsService {
    private final SegurancaRepository segurancaRepository;

    @Override
    public UserDetails loadUserByUsername(String gmail) throws UsernameNotFoundException {
        return segurancaRepository.findByGmail(gmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
    }

}
