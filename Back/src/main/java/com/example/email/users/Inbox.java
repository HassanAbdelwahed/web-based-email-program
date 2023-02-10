package com.example.email.users;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Inbox implements InboxOperation{
    @Override
    public void makingImportant(String userEmail, Long id) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get("inbox");
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            if (jsonMail.get("id").equals(id)){
                jsonMail.put("important", true);
                mails.remove(i);
                mails.add(jsonMail);
                break;
            }
        }
        account.put("inbox", mails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makingUnImportant(String userEmail, Long id) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get("inbox");
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            if (jsonMail.get("id").equals(id)){
                jsonMail.put("important", false);
                mails.remove(i);
                mails.add(jsonMail);
                break;
            }
        }
        account.put("inbox", mails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makingRead(String userEmail, Long id) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get("inbox");
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            if (jsonMail.get("id").equals(id)){
                jsonMail.put("read", true);
                mails.remove(i);
                mails.add(jsonMail);
                break;
            }
        }
        account.put("inbox", mails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makingUnRead(String userEmail, Long id) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get("inbox");
        for (int i = 0; i < mails.size(); ++i) {
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            if (jsonMail.get("id").equals(id)) {
                jsonMail.put("read", false);
                mails.remove(i);
                mails.add(jsonMail);
                break;
            }
        }
        account.put("inbox", mails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draftingMail(String userEmail, Mail mail) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get("draft");
        mails.add(mail);
        account.put("draft", mails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletingMail(String userEmail, Long id) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get("inbox");
        JSONArray trashMails = (JSONArray) account.get("trash");
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            if (jsonMail.get("id").equals(id)){
                trashMails.add(mails.get(i));
                mails.remove(i);
            }
        }
        account.put("inbox", mails);
        account.put("trash", trashMails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONArray search(String userEmail, String searchKey) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get("inbox");
        JSONArray foundedMails = new JSONArray();
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            String body = (String) jsonMail.get("body");
            String sender = (String) jsonMail.get("sender");
            String receiver = (String) jsonMail.get("receiver");
            String subject = (String) jsonMail.get("subject");
            if (body.contains(searchKey)){
                foundedMails.add(jsonMail);
                continue;
            }else if(subject.contains(searchKey)){
                foundedMails.add(jsonMail);
                continue;
            }else if(sender.contains(searchKey)){
                foundedMails.add(jsonMail);
                continue;
            }else if(receiver.contains(searchKey)){
                foundedMails.add(jsonMail);
                continue;
            }
        }
        return foundedMails;
    }
}
