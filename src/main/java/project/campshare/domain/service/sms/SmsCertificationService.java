package project.campshare.domain.service.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import project.campshare.exception.smscertification.FailedToSendMessage;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static project.campshare.util.UserConstants.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class SmsCertificationService {

    private final ConcurrentHashMap<String,String>certificationInformation =  new ConcurrentHashMap<>();


    public boolean certificationNumberInspection(String certificationNumber, String phoneNumber){
        return certificationInformation.get(phoneNumber).equals(certificationNumber);
    }

    /**
     * coolsms API
     * @param phoneNumber
     * @param certificationNumber
     */
    private void sendMessage(String phoneNumber, String certificationNumber) {

        Message coolsms = new Message(API_KEY, API_SECRET);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "01020180103");    // 발신전화번호
        params.put("type", "SMS");
        params.put("text", "shoe-action 휴대폰인증  : 인증번호는" + "[" + certificationNumber + "]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            log.error("Message transmission failure : error code{}",e.getCode());
            throw new FailedToSendMessage();
        }

    }

    /**
     * 인증번호 랜덤으로 생성
     * @return
     */
    private String createdRandomNumber() {
        Random rand = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < NUMBER_GENERATIONS_COUNT; i++) {
            stringBuilder.append((rand.nextInt(10)));
        }
        return stringBuilder.toString();
    }
}


