package za.co.laz.springdemo.port;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import za.co.laz.springdemo.application.PaymentArrangementService;
import za.co.laz.springdemo.domain.IPaymentArrangementService;
import za.co.laz.springdemo.domain.PaymentArrangementRequestDto;
import za.co.laz.springdemo.domain.PaymentArrangementResponseDto;


@RestController
public class DemoRest {

    @Autowired
    private PaymentArrangementService iPaymentArrangementService;

    @RequestMapping(value = "/ping")
    public String greeting(@RequestParam(name = "name", defaultValue = "World") String name){
        return "Hello "+name+", from the Demo Service";
    }

    @PostMapping(value = "/loadarrangement")
    @ResponseBody
    public PaymentArrangementResponseDto loadPaymentArrangement (@RequestBody PaymentArrangementRequestDto paymentArrangementRequestDto){
        PaymentArrangementResponseDto paymentArrangementResponseDto = null;
        paymentArrangementResponseDto = iPaymentArrangementService.loadPaymentArrangement(paymentArrangementRequestDto);

        return paymentArrangementResponseDto;
    }
}
