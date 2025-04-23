package br.com.rotafood.api.application.controller.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.application.dto.logistic.VrpOriginDto;
import br.com.rotafood.api.application.dto.logistic.VrpOutDto;
import br.com.rotafood.api.application.service.logistic.LogisticService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;


@RestController
@RequestMapping( ApiVersion.VERSION + "/logistic-test")
public class LogisticTestController {

    @Autowired
    private LogisticService logisticService;


    @PostMapping("/routes/{pointsQuantity}")
    public ResponseEntity<VrpOutDto> routesTest(
            @RequestBody AddressDto address,  
            @PathVariable Long pointsQuantity,
            HttpServletRequest request

    ) {
        if (pointsQuantity < 1) {
            throw new ValidationException("Mínimo de 5 pontos");
        }
        VrpOriginDto vrpOriginDto = new VrpOriginDto(UUID.randomUUID(), address);
        var vrpOutDto = this.logisticService.logisticRoutesTest(vrpOriginDto, pointsQuantity);
        return  ResponseEntity.ok().body(vrpOutDto);
    }
    
}
