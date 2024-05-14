package com.tree.gdhealth.branch.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tree.gdhealth.domain.Branch;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**<p>branchRESTapi의 테스트</p>
 * @author 정인호
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BranchRestApiControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    RestTemplate restTemplate;

    @Test
    void 하나의_지점정보를_가져온다() {
        String UrlStr = "http://localhost:"+port+"/api/v1/branch/2";
        URI uri = UriComponentsBuilder.fromUriString(UrlStr).build().toUri();
        ResponseEntity<Branch> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                Branch.class);
        log.debug(responseEntity.getBody().toString());
    }
    @Test
    void HttpCode404() {
        String UrlStr = "http://localhost:"+port+"/api/v1/branch/-1";
        URI uri = UriComponentsBuilder.fromUriString(UrlStr).build().toUri();
        try {
            ResponseEntity<Object> responseEntity = restTemplate.exchange(
                    uri, HttpMethod.GET, null,
                    Object.class);
            log.debug("" + responseEntity.getStatusCode());
        }catch (HttpClientErrorException exception){
            assertEquals(exception.getStatusCode().value(), 404);

        }
    }

    @Test
    void 모든지점정보를가져온다() {
        String UrlStr = "http://localhost:"+port+"/api/v1/branch";
        URI uri = UriComponentsBuilder.fromUriString(UrlStr).build().toUri();
        ResponseEntity<List<Branch>> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Branch>>(){});
        log.debug(responseEntity.getBody().toString());
    }
}