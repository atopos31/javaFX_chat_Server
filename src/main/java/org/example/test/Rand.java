package org.example.test;

import jakarta.mail.MessagingException;
import org.example.loginAregis.Sendemail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Rand {
    public static void main(String[] args) throws MessagingException {

        // 获取当前日期和时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        Sendemail.Send("we" , 233);
    }
}
