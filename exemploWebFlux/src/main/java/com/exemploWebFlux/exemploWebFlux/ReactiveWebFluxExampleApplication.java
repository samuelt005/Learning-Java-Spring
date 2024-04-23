package com.exemploWebFlux.exemploWebFlux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class ReactiveWebFluxExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveWebFluxExampleApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> routes() {
        // Define as rotas da aplicação usando o RouterFunction
        return route()
                // Rota para "/hello", retorna uma mensagem simples
                .GET("/hello", this::handleHello)
                // Rota para "/stream", retorna um fluxo de valores a intervalos regulares
                .GET("/stream", this::handleStream)
                // Rota para "/hello/{name}", retorna uma mensagem personalizada com base no parâmetro de consulta
                .GET("/hello/{name}", this::handleHelloWithName)
                // Rota para demonstrar a transformação de fluxo
                .GET("/transform", this::handleTransform)
                // Rota para demonstrar a combinação de fluxos
                .GET("/combine", this::handleCombine)
                .build(); // Constrói as rotas
    }

    // Essa rota retorna uma mensagem simples "Hello, WebFlux!".
    // Utiliza o método ok() e bodyValue() para retornar uma resposta Mono que produz um único valor.
    private Mono<ServerResponse> handleHello(ServerRequest request) {
        return ok().contentType(MediaType.TEXT_PLAIN)
                .bodyValue("Hello, WebFlux!");
    }

    // Retorna um fluxo de valores que emitem um valor a cada segundo e completa após 10 valores.
    // Produz um fluxo assíncrono e contínuo de valores.
    // Os valores são emitidos em intervalos regulares de tempo.
    private Mono<ServerResponse> handleStream(ServerRequest request) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1)).take(10);
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(interval, Long.class);
    }

    // Retorna uma mensagem personalizada com base no parâmetro de consulta "name" fornecido na URL.
    // Utiliza o método pathVariable() para extrair o valor do parâmetro de
    // consulta da solicitação e retornar uma resposta Mono com base nesse valor.
    private Mono<ServerResponse> handleHelloWithName(ServerRequest request) {
        String name = request.pathVariable("name");
        return ok().contentType(MediaType.TEXT_PLAIN)
                .bodyValue("Hello, " + name + "!");
    }

    // Cria um fluxo de números de 1 a 5 e aplica uma função de mapeamento para transformar cada número em seu quadrado.
    // Utiliza métodos de transformação (map) em um Flux para manipular os dados de forma assíncrona.
    private Mono<ServerResponse> handleTransform(ServerRequest request) {
        Flux<Integer> numbers = Flux.range(1, 5);
        Flux<Integer> squares = numbers
                .delayElements(Duration.ofSeconds(1))
                .map(num -> num * num);
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(squares, Integer.class);
    }

    // Combina fluxos de números pares e ímpares em um único fluxo.
    // Utiliza métodos de combinação (mergeWith) em Flux para combinar fluxos assíncronos em um único fluxo assíncrono.
    private Mono<ServerResponse> handleCombine(ServerRequest request) {
        Flux<Integer> evenNumbers = Flux.range(1, 5)
                .delayElements(Duration.ofSeconds(1))
                .filter(num -> num % 2 == 0);
        Flux<Integer> oddNumbers = Flux.range(1, 5)
                .delayElements(Duration.ofSeconds(1))
                .filter(num -> num % 2 != 0);
        Flux<Integer> combinedNumbers = evenNumbers.mergeWith(oddNumbers);
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(combinedNumbers, Integer.class);
    }

    // Classe para representar um evento
    record Event(long timestamp, String message) {
    }
}
