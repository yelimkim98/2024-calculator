package naver.kiel0103.calculator;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

  public int sum(int a, int b) {
    return a + b;
  }

  public int subtract(int a, int b) {
    return a - b;
  }
}
