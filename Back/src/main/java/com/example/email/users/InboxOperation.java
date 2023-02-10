package com.example.email.users;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface InboxOperation {
    void makingImportant(String userEmail, Long id) throws IOException, ParseException;

    void makingUnImportant(String userEmail, Long id) throws IOException, ParseException;

    void makingRead(String userEmail, Long id) throws IOException, ParseException;

    void makingUnRead(String userEmail, Long id) throws IOException, ParseException;
    public void draftingMail(String userEmail, Mail mail) throws IOException, ParseException;
    public void deletingMail(String userEmail, Long id) throws IOException, ParseException;
    public JSONArray search(String userEmail, String searchKey) throws IOException, ParseException;

}
