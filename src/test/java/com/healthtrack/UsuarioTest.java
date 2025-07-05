package com.healthtrack;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioTest {
    private Usuario u;

    @BeforeEach
    void setUp() {
        u = new Usuario("Ana", 70.0);
    }

    @Test
    void testActualizarPeso_CambiaCorrectamente() {
        u.actualizarPeso(72.5);
        assertEquals(72.5, u.getPeso(), 1e-6);
    }

    @Test
    void testActualizarPeso_Cero() {
        u.actualizarPeso(0.0);
        assertEquals(0.0, u.getPeso());
    }
}