package br.com.rotafood.api.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.dto.logistic.VrpOriginDto;
import br.com.rotafood.api.dto.logistic.VrpOutDto;
import br.com.rotafood.api.service.LogisticService;


@RestController
@RequestMapping("/v1/logistic")
public class LogisticController {

    @Autowired
    private LogisticService logisticService;

    @PostMapping("/routes/test/{pointsQuantity}")
    public ResponseEntity<VrpOutDto> routesTest(
            @RequestBody VrpOriginDto origin, 
            @RequestParam int pointsQuantity
    ) {

        var vrpOutDto = this.logisticService.logisticRoutesTest(origin, pointsQuantity);
        System.out.println(vrpOutDto
        );
        return vrpOutDto;
    }
    
}
