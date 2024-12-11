// package br.com.rotafood.api.infra.security;

// import java.io.IOException;
// import java.util.UUID;

// import org.springframework.core.annotation.Order;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import br.com.rotafood.api.application.dto.merchant.MerchantUserDto;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// @Order(2)
// public class MerchantValidationFilter extends OncePerRequestFilter {

//     @SuppressWarnings("null")
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
//             throws ServletException, IOException {
        
//         String requestURI = request.getRequestURI();
//         if (requestURI.matches(".*/merchants/[^/]+")) {
//             try {
//                 String[] segments = requestURI.split("/");
//                 String merchantIdFromUrl = segments[segments.length - 1];
//                 UUID merchantId = UUID.fromString(merchantIdFromUrl);

//                 var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//                 if (principal instanceof MerchantUserDto merchantUserDto) {
//                     if (!merchantUserDto.merchant().id().equals(merchantId)) {
//                         response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                         response.getWriter().write("Access Denied: Merchant ID mismatch.");
//                         return;
//                     }
//                 } else {
//                     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                     return;
//                 }
//             } catch (Exception e) {
//                 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                 response.getWriter().write("Invalid merchant ID or request.");
//                 return;
//             }
//         }

//         filterChain.doFilter(request, response);
//     }
// }
