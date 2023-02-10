package com.example.email.users;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
public class UsersService implements IUsersService {
    private InboxOperation inboxOperation;
    private  Criteria criteria;
    private FactoryCriteria factoryCriteria;
    private ContactOperation contactOperation;

    private Sorting sorting;
    private static UsersService instance = new UsersService();
    private AttachmentsManipulation attachmentsManipulation = new AttachmentsManipulation();
    private ConcreteAccountsManipulator accountsManipulator = new ConcreteAccountsManipulator();
    private String filename;
    private JSONArray users;
    private JSONArray mails;
    private JSONArray contacts;
    private JSONObject account;
    private String accountBody = "{\n" +
            "\"inbox\": [],\n" +
            "\"sent\": [],\n" +
            "\"draft\": [],\n" +
            "\"trash\": [],\n" +
            "\"contacts\": []\n" +
            "}";
    String datePattern = "dd/MM/yyyy HH:mm:ss ";
    DateTimeFormatter df = DateTimeFormatter.ofPattern(datePattern);

    private UsersService() {
        this.inboxOperation = new Inbox();
        this.factoryCriteria = new FactoryCriteria();
        this.contactOperation = new Contact();
        this.sorting = new Sort();
    }
    public static UsersService getInstance(){
        return instance;
    }

    @Override
    public boolean signUp(User user) throws IOException, ParseException {
        return accountsManipulator.signUp(user);
    }

    @Override
    public boolean logIn(User user) throws IOException, ParseException {
        return accountsManipulator.logIn(user);
    }

    @Override
    public JSONArray getInbox(String userEmail) throws IOException, ParseException {
        this.criteria = factoryCriteria.getCriteria("Inbox");
        return this.criteria.meetCriteria(userEmail);
    }

    @Override
    public JSONArray getSent(String userEmail) throws IOException, ParseException {
        this.criteria = factoryCriteria.getCriteria("Sent");
        return this.criteria.meetCriteria(userEmail);
    }

    @Override
    public JSONArray getDraft(String userEmail) throws IOException, ParseException {
        this.criteria = factoryCriteria.getCriteria("Draft");
        return this.criteria.meetCriteria(userEmail);
    }
    @Override
    public JSONArray getTrash(String userEmail) throws IOException, ParseException {
        this.criteria = factoryCriteria.getCriteria("Trash");
        return this.criteria.meetCriteria(userEmail);
    }
    @Override
    public JSONArray getImportant(String userEmail) throws IOException, ParseException {
        this.criteria = factoryCriteria.getCriteria("Important");
        return this.criteria.meetCriteria(userEmail);
    }
    @Override
    public JSONArray getRead(String userEmail) throws IOException, ParseException {
        this.criteria = factoryCriteria.getCriteria("Read");
        return this.criteria.meetCriteria(userEmail);
    }
    @Override
    public JSONArray getunRead(String userEmail) throws IOException, ParseException {
        this.criteria = factoryCriteria.getCriteria("UnRead");
        return this.criteria.meetCriteria(userEmail);
    }

    @Override
    public JSONArray getcontacts(String userEmail) throws IOException, ParseException {
        this.criteria = factoryCriteria.getCriteria("Contact");
        return this.criteria.meetCriteria(userEmail);
    }

    @Override
    public void draftingMail(String userEmail, Mail mail) throws IOException, ParseException {
        this.inboxOperation.draftingMail(userEmail, mail);
    }

    @Override
    public void deletingMail(String userEmail, Long id) throws IOException, ParseException {
        this.inboxOperation.deletingMail(userEmail, id);
    }

    @Override
    public void makingImportant(String userEmail, Long id) throws IOException, ParseException {
        this.inboxOperation.makingImportant(userEmail, id);
    }

    @Override
    public void makingUnImportant(String userEmail, Long id) throws IOException, ParseException {
        this.inboxOperation.makingUnImportant(userEmail, id);
    }
    @Override
    public void makingRead(String userEmail, Long id) throws IOException, ParseException {
       this.inboxOperation.makingRead(userEmail, id);
    }
    public void makingUnread(String userEmail, Long id) throws IOException, ParseException {
        this.inboxOperation.makingUnRead(userEmail, id);
    }

    @Override
    public JSONArray search(String userEmail, String searchKey) throws IOException, ParseException {
        return this.inboxOperation.search(userEmail, searchKey);
    }
    public JSONArray sortInbox(String userEmail) throws IOException, ParseException {
        return this.sorting.sortInbox(userEmail);
    }
    public JSONArray sortImportance(String userEmail) throws IOException, ParseException {
        return this.sorting.sortImportance(userEmail);
    }

    @Override
    public boolean addContact(String userEmail, String contactEmail) throws IOException, ParseException {
        return this.contactOperation.addContact(userEmail, contactEmail);
    }

    @Override
    public void deleteContact(String userEmail, String contactEmail) throws IOException, ParseException {
        this.contactOperation.deleteContact(userEmail, contactEmail);
    }
    @Override
    public boolean containContact(String userEmail, String contactEmail) throws IOException, ParseException {
        return this.contactOperation.containContact(userEmail, contactEmail);
    }
    // ---------------------
    // attachments manipulation
    @Override
    public void uploadAttachments(List<MultipartFile> multipartFiles, String senderEmail, String receiverEmail, long id) throws IOException, ParseException {
        attachmentsManipulation.uploadAttachments(multipartFiles, senderEmail, receiverEmail, id);
    }

    @Override
    public ResponseEntity<Resource> downloadAttachment(String userEmail, long id) throws IOException {
        return  attachmentsManipulation.downloadAttachment(userEmail, id);
    }
    @Override
    public boolean sendingMail(String senderEmail, Mail mail) throws IOException, ParseException {
        filename = "users.json";
        Object objc = new JSONParser().parse(new FileReader(this.filename));
        users = (JSONArray) objc;

        boolean flag = false;
        for(int i = 0; i < users.size(); i++){
            Object userData = users.get(i);
            JSONObject jsonUser = (JSONObject) userData;
            if (jsonUser.get("userEmail").equals(mail.getReceiver())) flag = true;
        }

        LocalDateTime now = LocalDateTime.now();
        String dateNow = df.format(now);
        mail.setDate(dateNow);
        mail.setHasAttachment(false);
        if(flag == true) {
            filename = "accounts/" + senderEmail + "/" + senderEmail + ".json";
            objc = new JSONParser().parse(new FileReader(filename));
            account = (JSONObject) objc;
            mails = (JSONArray) account.get("sent");
            mails.add(mail);
            account.put("sent", mails);

            try {
                Files.write(Paths.get(filename), account.toJSONString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            filename = "accounts/" + mail.getReceiver() + "/" + mail.getReceiver() + ".json";
            objc = new JSONParser().parse(new FileReader(filename));
            account = (JSONObject) objc;
            mails = (JSONArray) account.get("inbox");
            mails.add(mail);
            account.put("inbox", mails);

            try {
                Files.write(Paths.get(filename), account.toJSONString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // checking Emails so that it can be added to contacts
            if(!containContact(senderEmail, mail.getReceiver())){
                filename = "accounts/" + senderEmail + "/" + senderEmail + ".json";
                objc = new JSONParser().parse(new FileReader(filename));
                account = (JSONObject) objc;
                contacts = (JSONArray) account.get("contacts");
                addContact(senderEmail, mail.getReceiver());
                account.put("contacts", contacts);
                try {
                    Files.write(Paths.get(filename), account.toJSONString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(!containContact(mail.getReceiver(), senderEmail)){
                filename = "accounts/" + mail.getReceiver() + "/" + mail.getReceiver() + ".json";
                objc = new JSONParser().parse(new FileReader(filename));
                account = (JSONObject) objc;
                contacts = (JSONArray) account.get("contacts");
                addContact(mail.getReceiver(), senderEmail);
                account.put("contacts", contacts);
                try {
                    Files.write(Paths.get(filename), account.toJSONString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

}
