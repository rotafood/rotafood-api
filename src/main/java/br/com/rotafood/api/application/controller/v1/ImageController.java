package br.com.rotafood.api.application.controller.v1;

import br.com.rotafood.api.application.dto.catalog.ImageDto;
import br.com.rotafood.api.application.dto.catalog.ImageUploadDto;
import br.com.rotafood.api.application.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/v1/merchants/{merchantId}/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Value("${gcp.bucket.name}")
    private String bucketName;

    @Value("${gcp.bucket.url}")
    private String bucketUrl;
    
    @GetMapping
    public List<ImageDto> getAllImages(@PathVariable UUID merchantId) {
        return imageService.getAllByMerchantId(merchantId)
                .stream()
                .map(obj -> new ImageDto(obj, bucketUrl, bucketName))
                .toList();
    }

    @GetMapping("/{imageId}")
    public ImageDto getImageById(@PathVariable UUID merchantId, @PathVariable UUID imageId) {
        return new ImageDto(imageService.getByIdAndMerchantId(imageId, merchantId), bucketUrl, bucketName );
    }

    @PutMapping
    public ImageDto uploadImage(
            @PathVariable UUID merchantId,
            @RequestBody @Valid ImageUploadDto imageUploadDto) {
        return new ImageDto(imageService.uploadImage(imageUploadDto.base64Image(), merchantId), bucketUrl, bucketName);
    }

    @DeleteMapping("/{imageId}")
    public void deleteImage(@PathVariable UUID merchantId, @PathVariable UUID imageId) {
        imageService.deleteByIdAndMerchantId(imageId, merchantId);
    }
}
