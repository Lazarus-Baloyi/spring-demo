/*
package test.java.za.co.laz.springdemo;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.laz.springdemo.application.PaymentArrangementService;
import za.co.laz.springdemo.infrastructure.PaymentArrangement;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;



@SpringBootTest
class SpringDemoApplicationTests {

	private Logger LOG = LoggerFactory.getLogger(SpringDemoApplicationTests.class);

	@Mock
	PaymentArrangementService paymentArrangementService =  mock(PaymentArrangementService.class);

	@Test
	void contextLoads() {
	}

	@Test
	public void findByIdTest(){
		LOG.info("loadArrangementTest()");
		//PaymentArrangementService paymentArrangementService = mock(PaymentArrangementService.class);
		when(paymentArrangementService.findById(1l)).thenReturn(new PaymentArrangement());
	}



}
*/
