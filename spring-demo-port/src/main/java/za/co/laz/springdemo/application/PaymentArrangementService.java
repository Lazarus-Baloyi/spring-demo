package za.co.laz.springdemo.application;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import za.co.laz.springdemo.domain.*;
import za.co.laz.springdemo.infrastructure.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;*/

@Component
public class PaymentArrangementService implements IPaymentArrangementService {
    private Logger LOG = LoggerFactory.getLogger(PaymentArrangementService.class);

    @Autowired
    IPaymentArrangementRepository paymentArrangementRepository;

    @Autowired
    PaymentArrangement paymentArrangement;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PaymentArrangementResponseDto paymentArrangementResponseDto;

    @Autowired
    RestUtil restUtil;

    @Autowired
    CollectionsPaymentRequestDto collectionsPaymentRequestDto;

    Date date1;

    @SneakyThrows
    @Override
    public PaymentArrangementResponseDto loadPaymentArrangement(PaymentArrangementRequestDto paymentArrangementRequestDto) {
        LOG.info("Got paymentArrangementRequestDto: " + paymentArrangementRequestDto.toString());

        //try {

            paymentArrangement = mapPaymentArrangememt(paymentArrangementRequestDto);
            paymentArrangement = paymentArrangementRepository.save(paymentArrangement);
        if(paymentArrangement.getId() != null) {
            paymentArrangementResponseDto.setId(paymentArrangement.getId());
            paymentArrangementResponseDto.setLoaded(true);
            paymentArrangementResponseDto.setResponse("Saved payment arrangement");
            collectionsPaymentRequestDto = mapColectionPaymentRequest(paymentArrangementRequestDto);

            CollectionsPaymentResponseDto collectionsPaymentResponseDto = restUtil.callCollectionService(collectionsPaymentRequestDto, String.valueOf(paymentArrangement.getToaccountNumber()));
            paymentArrangementResponseDto = mapCollectionResponse(collectionsPaymentResponseDto, paymentArrangementResponseDto);

            LOG.info("result from collectionservice: "+collectionsPaymentResponseDto);
        }else{
            paymentArrangementResponseDto.setLoaded(false);
            paymentArrangementResponseDto.setResponse("Failed to load payment arrangement");

        }


        LOG.info("Loaded paymentArrangement: "+paymentArrangement);
        return paymentArrangementResponseDto;
    }

    @Override
    public boolean deletePaymentArrangement(PaymentArrangementRequestDto paymentArrangementRequestDto) {
        return false;
    }

    @Override
    public boolean updatePaymentArrangement(PaymentArrangementRequestDto paymentArrangementRequestDto) {
        return false;
    }

    @Override
    public CustomerDto getCustomerDetails(long loanAccountNumber) {
        return null;
    }

    private List<PaymentArrangement> getAllPaymentArrangements(){
        List<PaymentArrangement> paymentArrangementList = new ArrayList<PaymentArrangement>();
        paymentArrangementList = (List<PaymentArrangement>) paymentArrangementRepository.findAll();
        return paymentArrangementList;
    }

    public PaymentArrangement findById(Long id){
        PaymentArrangement paymentArrangement = paymentArrangementRepository.findById(id);



        return paymentArrangement;
    }

    private PaymentArrangement mapPaymentArrangememt(PaymentArrangementRequestDto paymentArrangementRequestDto) throws ParseException {
         paymentArrangement.setTotalRepayment(paymentArrangementRequestDto.getArrangementAmount());
         paymentArrangement.setFromaccountNumber(paymentArrangementRequestDto.getFromaccountNumber());
         paymentArrangement.setToaccountNumber(paymentArrangementRequestDto.getToaccountNumber());

        //date1=stringToDate(paymentArrangementRequestDto.getPaymentDay());
        paymentArrangement.setPaymentDay(date1);

         return paymentArrangement;
    }

    private CollectionsPaymentRequestDto mapColectionPaymentRequest(PaymentArrangementRequestDto paymentArrangementRequestDto) throws ParseException {
        LOG.info(paymentArrangementRequestDto.toString());
        collectionsPaymentRequestDto.setRescheduleAmount(5432.3);
        collectionsPaymentRequestDto.setPaymentTermAmount(paymentArrangementRequestDto.getArrangementAmount());
        collectionsPaymentRequestDto.setPaymentTermPeriod(2);
        collectionsPaymentRequestDto.setReasonInArrears("Covid19");
        //date1=new SimpleDateFormat("yyyy-MM-dd").parse(paymentArrangementRequestDto.getPaymentDay());
        PromiseToPay ptp = new PromiseToPay(date1,paymentArrangementRequestDto.getArrangementAmount());
        List<PromiseToPay> ptpList = new ArrayList<>();
        ptpList.add(ptp);
        collectionsPaymentRequestDto.setPromisesToPay(ptpList);
        collectionsPaymentRequestDto.setProductCode("MLS");
        collectionsPaymentRequestDto.setTotalRepaymentAmount(paymentArrangementRequestDto.getArrangementAmount()*4);
        return collectionsPaymentRequestDto;
    }

    private PaymentArrangementResponseDto mapCollectionResponse(CollectionsPaymentResponseDto collectionsPaymentResponseDto, PaymentArrangementResponseDto paymentArrangementResponseDto){
        paymentArrangementResponseDto.setIlpPaymentArrangementSuccessful(collectionsPaymentResponseDto.getIlpPaymentArrangementSuccessful());
        paymentArrangementResponseDto.setAccountRemarkUpdateSuccessful(collectionsPaymentResponseDto.getAccountRemarkUpdateSuccessful());
        paymentArrangementResponseDto.setOcsPaymentArrangementSuccessful(collectionsPaymentResponseDto.getOcsPaymentArrangementSuccessful());
        paymentArrangementResponseDto.setPushMessageSendSuccessful(collectionsPaymentResponseDto.getPushMessageSendSuccessful());

        return paymentArrangementResponseDto;
    }

    public Date stringToDate(String indate) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(indate);
    }
}
