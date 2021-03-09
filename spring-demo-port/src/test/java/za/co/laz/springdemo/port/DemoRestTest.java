package za.co.laz.springdemo.port;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import za.co.laz.springdemo.application.PaymentArrangementService;
import za.co.laz.springdemo.domain.PaymentArrangementRequestDto;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class DemoRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DemoRest demoRest;

    private Logger LOG = LoggerFactory.getLogger(DemoRestTest.class);

    @Mock
    PaymentArrangementService paymentArrangementService =  mock(PaymentArrangementService.class);


    @Test
    void greeting() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/ping");
        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals("Hello World, from the Demo Service", result.getResponse().getContentAsString());
        LOG.info("Result: "+result.getResponse().getContentAsString());
    }

    @Test
    void loadPaymentArrangement() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        PaymentArrangementRequestDto paymentArrangementAgreementRequestDto = new PaymentArrangementRequestDto();
        paymentArrangementAgreementRequestDto.setFromaccountNumber(62006565284l);
        paymentArrangementAgreementRequestDto.setToaccountNumber(3000022645213l);
        paymentArrangementAgreementRequestDto.setArrangementAmount(300d);
        paymentArrangementAgreementRequestDto.setPaymentDay("2020-09-13");

        String paymentArrangementAgreementJson = mapper.writeValueAsString(paymentArrangementAgreementRequestDto);
        LOG.info(paymentArrangementAgreementJson);
        when(paymentArrangementService.stringToDate(paymentArrangementAgreementRequestDto.getPaymentDay())).thenReturn(new Date());
        MvcResult result = mockMvc.perform(post("/loadarrangement").contentType(MediaType.APPLICATION_JSON).content(paymentArrangementAgreementJson))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        LOG.info("Result: "+result.getResponse().getContentAsString());
    }
}
