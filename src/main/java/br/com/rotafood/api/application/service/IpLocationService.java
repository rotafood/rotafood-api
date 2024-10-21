package br.com.rotafood.api.application.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class IpLocationService {

    private String ipInfoUrl = "https://ipinfo.io/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public String getLocationFromIp(String ip) {
        try {
            String url = this.ipInfoUrl + "/" + ip + "/geo";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            Map<String, Object> jsonResponse = objectMapper.readValue(
                response.getBody(), new TypeReference<Map<String, Object>>() {}
            );

            String city = (String) jsonResponse.get("city");
            String region = (String) jsonResponse.get("region");
            String country = (String) jsonResponse.get("country");
            LocalDateTime currentTime = LocalDateTime.now();


            return String.format("\n\n\nIP: %s \nCity: %s \nRegion: %s \nCountry: %s \nTime: %s", ip, city, region, country, currentTime);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to retrieve location";
        }
    }
}
 