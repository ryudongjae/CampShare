package project.campshare.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Random;

//자주사용하는 HttpStatus 상수 생성
public class RandomNumberGeneration {
    public static final String makeRandomNumber(){
        Random random = new Random();
        return String.valueOf(10000+ random.nextInt(90000));
    }
}
