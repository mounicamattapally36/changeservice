package com.adp.changeservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ChangeServiceApplicationTest {
    @Test
    void main() {
        assertDoesNotThrow(() -> ChangeServiceApplication.main(new String[] {}));
    }
}
