package com.bank;

import com.bank.controller.GirokontoController;
import com.bank.inhaber.InhaberID;
import com.bank.konto.Girokonto;
import com.bank.konto.IBAN;
import com.bank.middleware.filter.JwtAuthFilter;
import com.bank.middleware.security.JwtUtil;
import com.bank.service.GirokontoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GirokontoController.class)
@Import(JwtAuthFilter.class)
class JwtAuthFilterTest {

    @Autowired
    private MockMvc mockMvc;

    // Mocke ggf. abhängige Services
    @MockitoBean
    private GirokontoService girokontoService;

    @Test
    void requestWithValidToken_shouldReturnOk() throws Exception {
        // Erzeuge ein gültiges JWT für den Testuser mit Rolle USER
        String jwt = JwtUtil.generateToken("testuser", "USER");

        IBAN iban = new IBAN("DE12345678901234567890");
        InhaberID inhaberId = new InhaberID("INHABER123");
        Girokonto girokonto = new Girokonto(iban, inhaberId);

        // Service-Mock: Gib ein Optional<Girokonto> zurück!
        Mockito.when(girokontoService.findByIban(iban.getValue()))
                .thenReturn(girokonto);

        mockMvc.perform(get("/api/girokonten/DE12345678901234567890")
                .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

    @Test
    void requestWithInvalidToken_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/girokonten/DE12345678901234567890")
                .header("Authorization", "Bearer invalid.token.here"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void requestWithoutToken_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/girokonten/DE12345678901234567890"))
                .andExpect(status().isUnauthorized());
    }
}