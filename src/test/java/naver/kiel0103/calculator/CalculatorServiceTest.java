package naver.kiel0103.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void sum() {
        assertEquals(calculatorService.sum(10, 20), 30);
    }
}