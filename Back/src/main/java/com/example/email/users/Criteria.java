package com.example.email.users;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface Criteria {
    JSONArray meetCriteria(String userEmail) throws IOException, ParseException;
}