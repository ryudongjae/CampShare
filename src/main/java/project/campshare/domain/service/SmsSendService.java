package project.campshare.domain.service;

import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import java.util.HashMap;

import static project.campshare.util.UserConstants.CERTIFICATION_SESSION_KEY;

@RequiredArgsConstructor
@Service
public class SmsSendService {
    public void sendMessage(String phoneNumber,String certificationNumber){

        String api_key = "NCS6ZIOLCULQXVIT";
        String api_secret = "GDWQZCFZYLXYPIMEEFVPPZIFCUPEQN5T";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "01079422492");    // 발신전화번호
        params.put("type", "SMS");
        params.put("text", "camp_share 휴대폰인증  : 인증번호는" + "["+certificationNumber+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }
}


