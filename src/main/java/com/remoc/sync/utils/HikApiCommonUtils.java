package com.remoc.sync.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.minio.MinioClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class HikApiCommonUtils {
    public static final String SEARCH = "/ISAPI/ContentMgmt/search";
    private static final String DOWNLOAD_URL = "/ISAPI/ContentMgmt/download?playbackURI=";

    private static final RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("admin", "483384a1").build();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, SearchMatchItem.class);

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
    }


    public static List<SearchMatchItem> search(String host, String channelNumber, LocalDate date) {
        String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?><CMSearchDescription><searchID>" + UUID.randomUUID() + "</searchID><trackList><trackID>" + channelNumber + "</trackID></trackList><timeSpanList><timeSpan><startTime>" + date + "T00:00:00Z</startTime><endTime>" + date + "T23:59:59Z</endTime></timeSpan></timeSpanList><maxResults>100</maxResults><searchResultPostion>0</searchResultPostion><metadataList><metadataDescriptor>//recordType.meta.std-cgi.com</metadataDescriptor></metadataList></CMSearchDescription>";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);

        String searchData = restTemplate.postForObject(host + SEARCH, httpEntity, String.class);
        JSONObject xmlJSONObj = XML.toJSONObject(Objects.requireNonNull(searchData));

        try {
            JSONArray jsonObject = xmlJSONObj.getJSONObject("CMSearchResult").getJSONObject("matchList").getJSONArray("searchMatchItem");
            return objectMapper.readValue(jsonObject.toString(), javaType);
        } catch (Exception e) {
            return null;
        }
    }

    public static String download(String hostUrl, String playbackURI, String fileName) {
        RequestCallback requestCallback = request -> {
            request.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        };
        ResponseExtractor<String> responseExtractor = response -> {
            Files.copy(response.getBody(), Paths.get(fileName));
            return fileName;
        };
        return restTemplate.execute(hostUrl + DOWNLOAD_URL + playbackURI, HttpMethod.GET, requestCallback, responseExtractor);
    }

}
