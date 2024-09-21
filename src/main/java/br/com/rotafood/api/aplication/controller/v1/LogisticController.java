package br.com.rotafood.api.aplication.controller.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.aplication.dto.address.AddressDto;
import br.com.rotafood.api.aplication.dto.logistic.VrpOriginDto;
import br.com.rotafood.api.aplication.dto.logistic.VrpOutDto;
import br.com.rotafood.api.aplication.service.LogisticService;


@RestController
@RequestMapping("/v1/logistic")
public class LogisticController {

    @Autowired
    private LogisticService logisticService;

    @PostMapping("/routes/test/{pointsQuantity}")
    public ResponseEntity<VrpOutDto> routesTest(
            @RequestBody AddressDto address, 
            @PathVariable Long pointsQuantity
    ) {
        VrpOriginDto vrpOriginDto = new VrpOriginDto(UUID.randomUUID(), address);
        var vrpOutDto = this.logisticService.logisticRoutesTest(vrpOriginDto, pointsQuantity);

        return  ResponseEntity.ok().body(vrpOutDto);
    }
    
}
