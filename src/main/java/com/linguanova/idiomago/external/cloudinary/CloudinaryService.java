package com.linguanova.idiomago.external.cloudinary;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static com.linguanova.idiomago.util.AppRoutes.CLOUDYNARY_URL;

@Service
public class CloudinaryService {

    private final RestTemplate restTemplate = new RestTemplate();

    public CloudinaryResponse upload(MultipartFile file) throws IOException {
        long timestamp = System.currentTimeMillis() / 1000;
        String folder = "avatars";

        String signatureRaw = "folder=" + folder + "&timestamp=" + timestamp + CloudinaryConfig.apiSecret;
        String signature = DigestUtils.sha1Hex(signatureRaw); // NOSONAR S4830: SHA-1 is required by Cloudinary API for signature generation

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());
        body.add("api_key", CloudinaryConfig.apiKey);
        body.add("timestamp", timestamp);
        body.add("folder", folder);
        body.add("signature", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        String uploadUrl = CLOUDYNARY_URL + CloudinaryConfig.cloudName + "/image/upload";
        ResponseEntity<Map> response = restTemplate.postForEntity(uploadUrl, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Map<String, Object> data = response.getBody();
            return new CloudinaryResponse(
                    data.get("secure_url").toString(),
                    data.get("public_id").toString()
            );
        }

        throw new RuntimeException("Cloudinary upload failed: " + response.getStatusCode());
    }


    public void delete(String publicId) {
        long timestamp = System.currentTimeMillis() / 1000;
        String signatureRaw = "public_id=" + publicId + "&timestamp=" + timestamp + CloudinaryConfig.apiSecret;
        String signature = DigestUtils.sha1Hex(signatureRaw); // NOSONAR S4830: SHA-1 is required by Cloudinary API for signature generation

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("public_id", publicId);
        body.add("api_key", CloudinaryConfig.apiKey);
        body.add("timestamp", timestamp);
        body.add("signature", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        String deleteUrl = CLOUDYNARY_URL + CloudinaryConfig.cloudName + "/image/destroy";
        restTemplate.postForEntity(deleteUrl, request, Map.class);
    }
}
