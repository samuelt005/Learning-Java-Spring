package com.calculadora.calculadora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@SpringBootApplication
@Controller
public class ConversorDeTemperaturaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConversorDeTemperaturaApplication.class, args);
    }

    @GetMapping("/conversor")
    public ModelAndView calculadora() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("conversor");
        return modelAndView;
    }

    @PostMapping("/converter")
    public String calcular(@RequestParam("temperatura") double temperatura,
                           @RequestParam("tempOriginal") String tempOriginal,
                           @RequestParam("tempConvertida") String tempConvertida,
                           Model model) {

        double resultado = 0;

        if (!Objects.equals(tempOriginal, tempConvertida)) {
            if (tempOriginal.equals("C")) {
                if (tempConvertida.equals("F")) {
                    resultado = (temperatura * 9 / 5) + 32;
                } else if (tempConvertida.equals("K")) {
                    resultado = temperatura + 273.15;
                }
            } else if (tempOriginal.equals("F")) {
                if (tempConvertida.equals("C")) {
                    resultado = (temperatura - 32) * 5 / 9;
                } else if (tempConvertida.equals("K")) {
                    resultado = (temperatura - 32) * 5 / 9 + 273.15;
                }
            } else if (tempOriginal.equals("K")) {
                if (tempConvertida.equals("C")) {
                    resultado = temperatura - 273.15;
                } else if (tempConvertida.equals("F")) {
                    resultado = (temperatura - 273.15) * 9 / 5 + 32;
                }
            }
        } else {
            resultado = temperatura;
        }

        model.addAttribute("original", temperatura);
        model.addAttribute("temperaturaOriginal", tempOriginal);
        model.addAttribute("resultado", resultado);
        model.addAttribute("temperaturaResultado", tempConvertida);

        return "conversor";
    }
}
