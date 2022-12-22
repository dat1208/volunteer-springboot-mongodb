package com.volunteer.springbootmongo.HDBank.Service;

import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.AppsRegisterRequestData;
import com.volunteer.springbootmongo.models.data.User;
import com.volunteer.springbootmongo.repository.UserRepository;
import com.volunteer.springbootmongo.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author "KhaPhan" on 15-Dec-22
 * @project volunteer-springboot-mongodb
 */

@AllArgsConstructor
@Service
public class ValidateService {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    public boolean isExistLinked(String clientID, String AccountNo) {
        Optional<User> userStored = userRepository.findById(clientID).stream().findFirst();
        boolean isExistedLikedToBank = userStored.get().getHdBankAccountList() != null;
        if (isExistedLikedToBank) {
            if (userStored.get().getHdBankAccountList().size() >= 3) {
                return true;
            }
            for (int index = 0; index < userStored.get().getHdBankAccountList().size(); index++) {
                if (AccountNo.equals(userStored.get().getHdBankAccountList().get(index).getAccountNumber())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOverLinkedAccount(String clientID) {
        Optional<User> userStored = userRepository.findById(clientID).stream().findFirst();
        if (userStored.get().getHdBankAccountList() != null) {
            if (userStored.get().getHdBankAccountList().size() >= 3) {
                return true;
            }
        }
        return false;
    }
    public boolean isValidatedRegisterInput(AppsRegisterRequestData appsRegisterRequestData) {
        if(!isValidPasswordType(appsRegisterRequestData.getPassword())) {
            return false;
        }
        if(appsRegisterRequestData.getFullName().length() == 0 || appsRegisterRequestData.getFullName().length() >= 50) {
            return false;
        }
        if(!userService.emailVal(appsRegisterRequestData.getEmail()) || appsRegisterRequestData.getFullName().length() >= 99) {
            return false;
        }
        if (!userService.phoneVal(appsRegisterRequestData.getPhone())) {
            return false;
        }
        if (!appsRegisterRequestData.getIdentityNumber().matches("\\d+")) {
            return false;
        }
        return true;
    }

    public boolean isValidPasswordType(String password) {
        if(password.contains("^") || password.contains("$") || password.contains("password") ) {
            return false;
        }
        String regexPasswordRule = "\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\"";
        return password.matches(regexPasswordRule);
    }

    public boolean isMatchWithOldPassword(List<String> oldPasswordList, String newPassword) {
        for(String storedPassword : oldPasswordList) {
            if(newPassword.equals(storedPassword)) {
                return true;
            }
        }
        return false;
    }
}
