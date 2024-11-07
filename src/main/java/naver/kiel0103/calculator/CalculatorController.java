package naver.kiel0103.calculator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService service;

    @GetMapping("/sum")
    public ResponseEntity<Integer> sum(
            @RequestParam("a") Integer a, @RequestParam("b") Integer b
    ) {
        return ResponseEntity.ok(service.sum(a, b));
    }
}
