package org.example;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import com.google.gson.Gson;
import org.snf4j.core.handler.AbstractStreamHandler;
import org.snf4j.core.handler.SessionEvent;

public class ServerHandler extends AbstractStreamHandler {
    Gson gson = new Gson();
    Database database = new Database();
    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void read(Object msg) {
        String jsonStringRequest = new String((byte[]) msg);
        answer(database.getCars());
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    public void event(SessionEvent event) {
        switch (event) {
            case CREATED -> {
                System.out.println(getSession().getId() + "{created}" + getSession());
            }
            case OPENED -> {
                try {
                    System.out.println(getSession().getId() + "{connected}" + database.getCar0());
                    database.getCars();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            case CLOSED -> {
                System.out.println(getSession().getId() + "{disconnected}" + getSession());
            }
        }
    }

    private void answer(Answer answer) {
        String jsonStringAnswer = gson.toJson(answer);
        getSession().write(("%s\n".formatted(jsonStringAnswer)).getBytes(StandardCharsets.UTF_8));
    }
}