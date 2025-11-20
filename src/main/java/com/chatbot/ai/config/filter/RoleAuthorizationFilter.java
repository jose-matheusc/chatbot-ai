//package com.async.pdf.config.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class RoleAuthorizationFilter implements GatewayFilter, Ordered {
//
//    private final Map<String, List<String>> roleMap = Map.of(
//            "/api/admin/**", List.of("ROLE_ADMIN"),
//            "/api/pdf/**", List.of("ROLE_USER", "ROLE_ADMIN")
//    );
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String path = exchange.getRequest().getURI().getPath();
//        List<String> requiredRoles = roleMap.entrySet().stream()
//                .filter(e -> path.matches(e.getKey().replace("**", ".*")))
//                .map(Map.Entry::getValue)
//                .findFirst()
//                .orElse(List.of());
//
//        if (requiredRoles.isEmpty()) {
//            return chain.filter(exchange);
//        }
//
//        List<String> userRoles = exchange.getAttribute("roles");
//        if (userRoles.stream().noneMatch(requiredRoles::contains)) {
//            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//            return exchange.getResponse().setComplete();
//        }
//
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
