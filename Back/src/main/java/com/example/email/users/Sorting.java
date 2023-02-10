package com.example.email.users;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface Sorting {
    public JSONArray sortImportance(String userEmail) throws IOException, ParseException;
    public JSONArray sortInbox(String userEmail) throws IOException, ParseException;
}
