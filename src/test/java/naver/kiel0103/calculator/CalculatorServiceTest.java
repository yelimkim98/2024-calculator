package naver.kiel0103.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void sum() {
        assertEquals(calculatorService.sum(10, 20), 30);
    }

    @Test
    void subtract() {
        assertEquals(calculatorService.subtract(10, 20), -10);
    }
}