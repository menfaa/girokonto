package com.bank;

import com.bank.konto.Girokonto;
import com.bank.konto.GirokontoService;
import com.bank.konto.IBAN;
import com.bank.controller.GirokontoController;
import com.bank.inhaber.InhaberID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GirokontoController.class) // Startet nur den Web-Layer und den angegebenen Controller
class GirokontoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Wird von Spring für HTTP-Request-Simulation injiziert

    @MockitoBean
    private GirokontoService girokontoService; // Mockt den Service, damit keine echte DB benötigt wird

    @Test
    void testGetGirokontoByIban() throws Exception {
        // Arrange: Mock das Service-Verhalten
        IBAN iban = new IBAN("DE12345678901234567890"); // Erstelle eine IBAN für den Test
        InhaberID inhaberId = new InhaberID("INHABER123"); // Erstelle eine InhaberID für den Test
        Girokonto girokonto = new Girokonto(iban, inhaberId); // Erstelle ein Girokonto-Objekt

        Mockito.when(girokontoService.findByIban(iban.getValue())).thenReturn(girokonto); // Definiere das
                                                                                          // Mock-Verhalten

        // Act & Assert: Führe GET-Request aus und prüfe das Ergebnis
        mockMvc.perform(get("/api/girokonten/DE12345678901234567890") // Simuliere einen GET-Request
                .accept(MediaType.APPLICATION_JSON)) // Akzeptiere JSON als Antwort
                .andExpect(status().isOk()) // Erwartet HTTP 200 OK
                .andExpect(jsonPath("$.iban.value").value("DE12345678901234567890")) // Erwartet die richtige IBAN im
                                                                                     // JSON
                .andExpect(jsonPath("$.inhaberId.value").value("INHABER123")); // Erwartet die richtige InhaberID im
                                                                               // JSON
    }

}