package com.volunteer.springbootmongo.Donation.Service;

import com.google.api.Http;
import com.volunteer.springbootmongo.Donation.Model.DonateHistory;
import com.volunteer.springbootmongo.Donation.Model.DonateModel;
import com.volunteer.springbootmongo.Donation.Request.RequireDonateRequest;
import com.volunteer.springbootmongo.HDBank.Service.ValidateService;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.HDBankRequest.HDBankRequest;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.BalanceRequest;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.BalanceRequestData;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.TransferRequestData;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Request;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.TransferRequest;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import com.volunteer.springbootmongo.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author "KhaPhan" on 16-Dec-22
 * @project volunteer-springboot-mongodb
 */

@AllArgsConstructor
@Service
public class DonateService {
    @Autowired
    private ValidateService validateService;

    @Autowired
    private HDBankRequest hdBankRequest;

    public ResponseObject donation_require(RequireDonateRequest donateRequest){

        String clientID = donateRequest.getClientID();
        String organizationID = donateRequest.getOrganizationID();
        String postID = donateRequest.getPostID();
        Long amountDonate = Long.valueOf(donateRequest.getAmountDonate());
        String bankAccUsername = donateRequest.getBankAccUsername();
        String fromBankAccNo = donateRequest.getFromBankAccNo();
        String toBankAccNo = donateRequest.getToBankAccNo();
        String desc = donateRequest.getDesc();
        String wishMessage = donateRequest.getWishMessage();

        if(!validateService.isExistLinked(clientID,fromBankAccNo))
            return new ResponseObject(HttpStatus.NOT_ACCEPTABLE.name(), "bankno_not_linked");

        Request request = new Request();
        BalanceRequestData balanceRequestData = new BalanceRequestData(fromBankAccNo);
        BalanceRequest balanceRequest = new BalanceRequest(request, balanceRequestData);
        Double bankBalance = Double.valueOf(hdBankRequest.getBalanceHDBankAccount(balanceRequest).getBody().getData().getAmount());
        if( bankBalance < amountDonate){
            return new ResponseObject(HttpStatus.NOT_ACCEPTABLE.name(), "low_balance");
        }
        TransferRequestData transferRequestData = new TransferRequestData(fromBankAccNo,toBankAccNo,amountDonate.toString(),desc);

        DonateHistory donateHistory = new DonateHistory(clientID,organizationID,amountDonate.toString(),bankAccUsername,fromBankAccNo,toBankAccNo,"ok",desc);
        DonateModel donateModel = new DonateModel();
        return new ResponseObject(HttpStatus.OK.toString(),hdBankRequest.transferHDBankAccount(new TransferRequest(request,transferRequestData)));
    }
}
