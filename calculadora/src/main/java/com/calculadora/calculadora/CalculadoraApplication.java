package com.calculadora.calculadora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@Controller
public class CalculadoraApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculadoraApplication.class, args);
    }

    @GetMapping("/calculadora")
    public ModelAndView calculadora() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("calculadora");
        return modelAndView;
    }

    @PostMapping("/calcular")
    public String calcular(@RequestParam("num1") double num1,
                           @RequestParam("num2") double num2,
                           @RequestParam("operador") String operador,
                           Model model) {
        double resultado = 0;
        switch (operador) {
            case "+" -> resultado = num1 + num2;
            case "-" -> resultado = num1 - num2;
            case "*" -> resultado = num1 * num2;
            case "/" -> resultado = num1 / num2;
            default -> {
            }
        }

        model.addAttribute("resultado", resultado);

        return "calculadora";
    }
}
