package br.com.rotafood.api.infra.gcp;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class GoogleCloudStorage {

    private final Storage storage;

    public GoogleCloudStorage() {
        try {
            this.storage = StorageOptions.newBuilder()
                                         .setCredentials(GoogleCredentials.getApplicationDefault())
                                         .build()
                                         .getService();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar as credenciais do Google Cloud Storage: " + e.getMessage(), e);
        }
    }

    public void createBucket(String bucketName) {
        storage.create(BucketInfo.of(bucketName));
        System.out.println("Bucket " + bucketName + " criado.");
    }

    public void uploadFile(String bucketName, byte[] fileBytes, String filePath) {
        String blobName = "images/" + Paths.get(filePath).getFileName().toString();
        
        BlobId blobId = BlobId.of(bucketName, blobName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        
        storage.create(blobInfo, fileBytes);
        System.out.println("Arquivo " + blobName + " enviado para o bucket " + bucketName);

    }

    public void deleteFile(String bucketName, String filePath) {
        try {
            BlobId blobId = BlobId.of(bucketName, filePath);
            boolean deleted = storage.delete(blobId);
    
            if (deleted) {
                System.out.println("Arquivo " + filePath + " removido do bucket " + bucketName);
            } else {
                System.err.println("Erro: Arquivo " + filePath + " não encontrado no bucket " + bucketName);
            }
        } catch (Exception e) {
            System.err.println("Erro ao tentar remover o arquivo " + filePath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listBuckets() {
        // Usando StreamSupport para converter Iterable em Stream
        List<String> bucketNames = StreamSupport.stream(storage.list().iterateAll().spliterator(), false)
                .map(bucket -> bucket.getName())
                .collect(Collectors.toList());

        // Exibir os nomes dos buckets
        System.out.println("Buckets encontrados:");
        bucketNames.forEach(System.out::println);

        // Exibir como lista
        System.out.println("Lista de buckets: " + bucketNames);
    }
    
}
