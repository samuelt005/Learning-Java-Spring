package com.firstride.firstride;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

@SpringBootApplication
public class FirstrideApplication {

    public static void main(String[] args) {
        int opcao = 1;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Escolha uma opção: \n" +
                    "1 - Calcular média \n" +
                    "2 - Jogo de adivinhar o número \n" +
                    "0 - Sair \n");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> calculaMedia();
                case 2 -> adivinhaNumero();
                default -> {
                    return;
                }
            }
        }
    }

    private static void calculaMedia() {
        Scanner scanner = new Scanner(System.in);
        Set<Double> notas = new HashSet<>();
        double total = 0;
        double media;


        System.out.println("Informe a primeira nota da prova: ");
        notas.add(scanner.nextDouble());
        System.out.println("Informe a segunda nota da prova: ");
        notas.add(scanner.nextDouble());
        System.out.println("Informe a terceira nota da prova: ");
        notas.add(scanner.nextDouble());

        for (Double nota : notas) {
            total += nota;
        }
        media = total / 3;
        System.out.println("Media das notas: " + media + "\n");
    }

    private static void adivinhaNumero() {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        int secretNumber = rand.nextInt(100) + 1;
        int guess;

        System.out.println("Tente adivinhar o número de 1 a 100");

        while (true) {
            System.out.println("Insira seu chute: \n");
            guess = scanner.nextInt();

            if (guess < 1 || guess > 100) {
                System.out.println("Número fora do alcance! \n");
            }

            if (guess == secretNumber) {
                System.out.println("Parabéns você acertou o número! \n");
                return;
            }

            if (guess < secretNumber) {
                System.out.println("O número secreto é maior! \n");
            }

            if (guess > secretNumber) {
                System.out.println("O número secreto é menor! \n");
            }
        }
    }
}
