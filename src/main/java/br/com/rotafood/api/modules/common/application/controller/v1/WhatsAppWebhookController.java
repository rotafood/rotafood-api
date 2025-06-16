package br.com.rotafood.api.modules.common.application.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.modules.common.application.dto.WhatsAppWebhookRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiVersion.VERSION + "/webhook/whatsapp")
@Validated
public class WhatsAppWebhookController {

    @PostMapping
    public ResponseEntity<Void> post(
            @RequestBody @Valid WhatsAppWebhookRequest payload) {  

        System.err.println("\n\n"  + payload + "\n\n");
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Void> get() {
        return ResponseEntity.ok().build();
    }

}
