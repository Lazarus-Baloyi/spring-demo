package za.co.laz.springdemo.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import za.co.laz.springdemo.domain.CollectionsPaymentRequestDto;
import za.co.laz.springdemo.domain.CollectionsPaymentResponseDto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class RestUtil {

    private Logger LOG = LoggerFactory.getLogger(RestUtil.class);

    @Value("${services.collections.base.url}")
    String collectionsBaseUrl;

    @Value("${services.collections.payment.arrangement.path}")
    String paymentArrangementPath;

    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    CollectionsPaymentResponseDto collectionsPaymentResponseDto;
    /*public RestUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }*/


    private static final String ACCOUNT_NUMBER_URI_VARIABLE = "accountNumber";
    private static final String COMPANY_CODE_URI_VARIABLE = "companyCode";
    private static final String COMPANY_CODE_VALUE = "15";
    private static final String INITITATING_UCN_VARIABLE = "initiatingUcn";

    public CollectionsPaymentResponseDto callCollectionService(CollectionsPaymentRequestDto collectionsPaymentRequestDto, String accountNumber){

        LOG.info("Calling collection service with: "+collectionsPaymentRequestDto.toString());

        HttpEntity requestEntity = buildHttpEntity(collectionsPaymentRequestDto);
        LOG.info("requestEntity: "+requestEntity.toString());
        LOG.info("collectionsBaseUrl+paymentArrangementPath: "+collectionsBaseUrl+paymentArrangementPath);
        Map<String, String> uriVariableValues = new HashMap<>();
        uriVariableValues.put(COMPANY_CODE_URI_VARIABLE, COMPANY_CODE_VALUE);
        uriVariableValues.put(ACCOUNT_NUMBER_URI_VARIABLE, accountNumber);
        uriVariableValues.put(INITITATING_UCN_VARIABLE, "499367");
        try {
            ResponseEntity result = post(uriBuilder(collectionsBaseUrl, paymentArrangementPath, uriVariableValues), null, collectionsPaymentRequestDto, null);
            collectionsPaymentResponseDto = (CollectionsPaymentResponseDto) result.getBody();
        }catch (HttpServerErrorException e){
            LOG.error("Caught Exception: "+e);
            collectionsPaymentResponseDto = new CollectionsPaymentResponseDto(false,false,false,false);
        }

        return collectionsPaymentResponseDto;

    }
    private <R, T> T invoke(String uri, HttpMethod httpMethod, Class<T> response, R request, Map<String, String> headers) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("REQUEST-SYSTEM-ID", "spring-demo");
            httpHeaders.set("REQUEST-CORRELATION-ID", UUID.randomUUID().toString());

            if(!CollectionUtils.isEmpty(headers))
                headers.entrySet().stream().forEach(h -> httpHeaders.add(h.getKey(), h.getValue()));

            HttpEntity<R> requestEntity;
            if (request != null) requestEntity = new HttpEntity<>(request, httpHeaders);
            else requestEntity = new HttpEntity<>(httpHeaders);
            /*TODO assign ucn parameter properly*/
            uri=uri+'?'+INITITATING_UCN_VARIABLE+'='+499367;
            LOG.info("uri: "+uri);
            LOG.info("httpMethod: "+httpMethod);
            LOG.info("requestEntity: "+requestEntity);

            ResponseEntity<T> responseEntity = restTemplate.exchange(uri, httpMethod, requestEntity, response);
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            LOG.error("Error status code received.", e.getStatusCode());
            throw e;
        }

    }

    public static HttpEntity buildHttpEntity(CollectionsPaymentRequestDto collectionsPaymentRequestDto) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("REQUEST-SYSTEM-ID", "collections-service");
        requestHeaders.set("REQUEST-CORRELATION-ID", UUID.randomUUID().toString());
        HttpEntity<CollectionsPaymentRequestDto> httpEntity = new HttpEntity<>(collectionsPaymentRequestDto,requestHeaders);
        return httpEntity;
    }

    public <R, T> T post(String uri, Class<T> response, R request, Map<String, String> headers) {
        return invoke(uri, HttpMethod.POST, response, request, headers);
    }

    public static String uriBuilder(String url, String path, Map<String, String> uriVariableValues) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(url+path)
                .buildAndExpand(uriVariableValues)
                .encode();

        return uriComponents.toString();
    }

}
