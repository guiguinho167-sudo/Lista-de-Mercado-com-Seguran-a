package com.lista.lista;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.lista.lista.MercadoErros.UssuarioException.BuscasException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ListaApplicationTests {

	@Mock
	private MercadoRepository repository;

	@InjectMocks
	private MercadoService mercadoService;

	@Test
	void salvavalor() {
		MercadoDados item = new MercadoDados();
		item.setValor(new BigDecimal(1000));

		when(repository.save(item)).thenReturn(item);

		MercadoDados resultado = repository.save(item);

		assertEquals(item, resultado);

	}

	@Test
	void delete() {
		MercadoDados item = new MercadoDados();
		item.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(BuscasException.class,
				() -> mercadoService.delete(1L, "teste@gmail.com"));

		verify(repository, never()).deleteById(anyLong());

	}

	@Test
	void valor() {
		MercadoDados salvo = new MercadoDados();
		salvo.setValor(new BigDecimal("50.00"));
		salvo.setProduto("Arroz");

	}
}