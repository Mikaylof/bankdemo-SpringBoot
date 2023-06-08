package az.mushfigm.bankdemoboot.util;

import az.mushfigm.bankdemoboot.dto.request.ReqToken;
import az.mushfigm.bankdemoboot.entity.User;
import az.mushfigm.bankdemoboot.entity.UserToken;
import az.mushfigm.bankdemoboot.enums.EnumAvailableStatus;
import az.mushfigm.bankdemoboot.exception.BankException;
import az.mushfigm.bankdemoboot.exception.ExceptionConstants;
import az.mushfigm.bankdemoboot.repository.UserRepository;
import az.mushfigm.bankdemoboot.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@RequiredArgsConstructor
public class Utility {

    private final UserTokenRepository userTokenRepository;
    private final UserRepository userRepository;

    public UserToken checkToken(ReqToken reqToken) {
        Long userId = reqToken.getUserId();
        String token = reqToken.getToken();
        if (userId == null || token == null) {
            throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
        }
        User user = userRepository.findUserByIdAndActive(userId, EnumAvailableStatus.ACTIVE.value);
        if (user == null) {
            throw new BankException(ExceptionConstants.USER_NOT_FOUND, "User not found");
        }
        UserToken userToken = userTokenRepository.findUserTokenByUserAndTokenAndActive(user, token, EnumAvailableStatus.ACTIVE.value);
        if (userToken == null) {
            throw new BankException(ExceptionConstants.INVALID_TOKEN, "Invalid token");
        }
        return userToken;
    }
   /* private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("mikayilzademusviq98@gmail.com");
        mailSender.setPassword("myogzdtevijuufmv");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }*/
    /*@Autowired
    private JavaMailSender emailSender;*/

    @Autowired
    private final JavaMailSender mailSender;
    public void sendEmail(String to, String activationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mikayilzademusviq98@gmail.com");
        message.setTo(to);
        message.setSubject("Email Activation");
        message.setText("Please activate your email address by clicking the link:\n" +
                "http://localhost:8082/bank/customer/activate/"+activationCode);
        mailSender.send(message);
    }
}