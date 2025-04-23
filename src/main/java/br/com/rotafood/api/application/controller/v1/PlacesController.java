package br.com.rotafood.api.application.controller.v1;

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.application.service.PlacesService;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/places")
@Validated
public class PlacesController {

    private final PlacesService placesService;

    public PlacesController(PlacesService placesService) {
        this.placesService = placesService;
    }

    @GetMapping("/cep/{cep}")
    public AddressDto getAddressByCep(
        @PathVariable
        @Pattern(regexp = "\\d{8,9}", message = "CEP deve conter 8 ou 9 dígitos numéricos")
        String cep
    ) {
        var address = placesService.findOrCreateByCep(cep);
        System.err.println(address + "\n\n\n");
        return address;
    }
}
