package br.com.rotafood.api.application.service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.rotafood.api.domain.entity.catalog.Image;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.ImageRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import br.com.rotafood.api.infra.gcp.GoogleCloudStorage;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private GoogleCloudStorage googleCloudStorage;
    @Autowired
    private MerchantRepository merchantRepository;

    @Value("${gcp.bucket.name}")
    private String bucketName;

    @Transactional
    public Image uploadImage(String base64Image, UUID merchantId) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image.split(",")[1]);
        UUID id = UUID.randomUUID();
        String fileName = id.toString() + ".png";
        String filePath =  "images/" + fileName;

        googleCloudStorage.uploadFile(bucketName, decodedBytes, filePath);

        Merchant merchant = this.merchantRepository.getReferenceById(merchantId);
        Image image = new Image();
        image.setPath(filePath);
        image.setId(id);
        image.setMerchant(merchant);
        return imageRepository.save(image);
    }

    @Transactional
    public void deleteByIdAndMerchantId(UUID imageId, UUID merchantId) {
        Image image = imageRepository.findByIdAndMerchantId(imageId, merchantId);
    
        if (image == null) {
            throw new IllegalArgumentException("Imagem não encontrada ou não pertence ao Merchant especificado.");
        }
    
        String imagePath = image.getPath();
    
        if (imagePath == null) {
            throw new RuntimeException("Caminho relativo não encontrado na URL: " + imagePath);
        }
    
        googleCloudStorage.deleteFile(bucketName, imagePath);
    
        imageRepository.delete(image);
    }
    
    public Image getByIdAndMerchantId(UUID imageId, UUID merchantId) {
        return imageRepository.findByIdAndMerchantId(imageId, merchantId);
    }

    public List<Image> getAllByMerchantId(UUID merchantId) {
        return imageRepository.findAllByMerchantId(merchantId);
    }

}
