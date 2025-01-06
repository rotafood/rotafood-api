package br.com.rotafood.api.application.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.LogDto;

@RestController
@RequestMapping( ApiVersion.VERSION + "/logs")
public class LogController {

    @PostMapping
    public ResponseEntity<Void> logMessage(@RequestBody LogDto logDto) {
        System.out.println("Received Log\n\n");
        System.out.println("URL: " + logDto.url());
        System.out.println("Date: " + logDto.date());
        System.out.println("Location: " + logDto.location());
        return ResponseEntity.ok().build();
    }
}
