package com.example.app.helper;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReaderHelper {
    public String getStringFromInputStream(HttpServletRequest req){
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {

        }
        return stringBuilder.toString();
    }
}
