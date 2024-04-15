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
                // Rota para "/events", retorna um fluxo de eventos fictícios a intervalos regulares
                .GET("/events", this::handleEvents)
                // Rota para "/hello/{name}", retorna uma mensagem personalizada com base no parâmetro de consulta
                .GET("/hello/{name}", this::handleHelloWithName)
                // Rota para criar um recurso usando uma solicitação POST
                .POST("/create", this::handleCreate)
                // Rota para demonstrar a transformação de fluxo
                .GET("/transform", this::handleTransform)
                // Rota para demonstrar a combinação de fluxos
                .GET("/combine", this::handleCombine)
                .build(); // Constrói as rotas
    }

    // Manipulador de rota para "/hello"
    private Mono<ServerResponse> handleHello(ServerRequest request) {
        // Retorna uma resposta com uma mensagem simples
        return ok().contentType(MediaType.TEXT_PLAIN)
                .bodyValue("Hello, WebFlux!");
    }

    // Manipulador de rota para "/stream"
    private Mono<ServerResponse> handleStream(ServerRequest request) {
        // Cria um fluxo de valores que emite um valor a cada segundo e completa após 10 valores
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1)).take(10);
        // Retorna o fluxo de valores como um fluxo de eventos contínuo
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(interval, Long.class);
    }

    // Manipulador de rota para "/events"
    private Mono<ServerResponse> handleEvents(ServerRequest request) {
        // Cria um fluxo de eventos fictícios que emite um evento a cada segundo e completa após 5 eventos
        Flux<Event> events = Flux.interval(Duration.ofSeconds(1))
                .map(i -> new Event(System.currentTimeMillis(), "Event " + i))
                .take(5);
        // Retorna o fluxo de eventos como um fluxo de eventos contínuo
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(events, Event.class);
    }

    // Manipulador de rota para "/hello/{name}"
    private Mono<ServerResponse> handleHelloWithName(ServerRequest request) {
        // Extrai o parâmetro de consulta "name" da URL
        String name = request.pathVariable("name");
        // Retorna uma mensagem personalizada com base no nome fornecido
        return ok().contentType(MediaType.TEXT_PLAIN)
                .bodyValue("Hello, " + name + "!");
    }

    // Manipulador de rota para "/create"
    private Mono<ServerResponse> handleCreate(ServerRequest request) {
        // Extrai o corpo da solicitação POST
        Mono<String> requestBody = request.bodyToMono(String.class);
        // Manipula o corpo da solicitação para criar um recurso (simulado) e retorna uma mensagem de confirmação
        return requestBody.flatMap(body -> {
            String responseMessage = "Resource created with data: " + body;
            return ok().contentType(MediaType.TEXT_PLAIN)
                    .bodyValue(responseMessage);
        });
    }

    // Manipulador de rota para "/transform"
    private Mono<ServerResponse> handleTransform(ServerRequest request) {
        // Cria um fluxo de números de 1 a 5
        Flux<Integer> numbers = Flux.range(1, 5);
        // Aplica uma função de mapeamento para transformar cada número em seu quadrado
        Flux<Integer> squares = numbers.map(num -> num * num);
        // Retorna o fluxo de quadrados como um fluxo de eventos contínuo
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(squares, Integer.class);
    }

    // Manipulador de rota para "/combine"
    private Mono<ServerResponse> handleCombine(ServerRequest request) {
        // Cria um fluxo de números pares
        Flux<Integer> evenNumbers = Flux.range(1, 5)
                .filter(num -> num % 2 == 0);
        // Cria um fluxo de números ímpares
        Flux<Integer> oddNumbers = Flux.range(1, 5)
                .filter(num -> num % 2 != 0);
        // Combina os fluxos de números pares e ímpares em um único fluxo
        Flux<Integer> combinedNumbers = evenNumbers.mergeWith(oddNumbers);
        // Retorna o fluxo combinado como um fluxo de eventos contínuo
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(combinedNumbers, Integer.class);
    }

    // Classe para representar um evento
        record Event(long timestamp, String message) {
    }
}
