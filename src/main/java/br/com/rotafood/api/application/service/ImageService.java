package br.com.rotafood.api.application.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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

    @Value("${gcp.bucket.url}")
    private String bucketUrl;

    @Transactional
    public Image uploadImage(MultipartFile file, UUID merchantId) {
    String contentType = file.getContentType();
    if (contentType == null || (!contentType.equals("image/png") && !contentType.equals("image/jpeg"))) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Apenas arquivos PNG ou JPEG são permitidos.");
    }

        try {
            UUID id = UUID.randomUUID();
            String fileExtension = contentType.equals("image/png") ? ".png" : ".jpeg";
            String fileName = id.toString() + fileExtension;
            String filePath = "images/" + fileName;

            googleCloudStorage.uploadFile(bucketName, file.getBytes(), filePath);

            Merchant merchant = this.merchantRepository.getReferenceById(merchantId);
            Image image = new Image();
            image.setPath(this.bucketUrl + "/" + this.bucketName + "/" + filePath);
            image.setId(id);
            image.setMerchant(merchant);

            return imageRepository.save(image);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar o arquivo.", e);
        }
    }

    @SuppressWarnings("unused")
    @Transactional
    public void deleteByIdAndMerchantId(UUID imageId, UUID merchantId) {
        Image image = imageRepository.findByIdAndMerchantId(imageId, merchantId);
    
        if (image == null) {
            throw new IllegalArgumentException("Imagem não encontrada ou não pertence ao Merchant especificado.");
        }
    
        String imagePath = image.getPath();
        String basePath = "rotafood-api/";
        int startIndex = imagePath.indexOf(basePath);
        String result = imagePath.substring(startIndex + basePath.length());
     
        if (imagePath == null) {
            throw new IllegalArgumentException("Caminho relativo não encontrado na URL: " + result);
        }
    
        googleCloudStorage.deleteFile(bucketName, result);
    
        imageRepository.delete(image);
    }
    
    public Image getByIdAndMerchantId(UUID imageId, UUID merchantId) {
        return imageRepository.findByIdAndMerchantId(imageId, merchantId);
    }

    public List<Image> getAllByMerchantId(UUID merchantId) {
        return imageRepository.findAllByMerchantId(merchantId);
    }

}
