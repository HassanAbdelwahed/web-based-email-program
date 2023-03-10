package com.example.email.users;


import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class UsersController {
    private final UsersService usersService;
    private Criteria criteria;

    public UsersController() {
        this.usersService = UsersService.getInstance();
    }

    @PostMapping("signup")
    public boolean signUp(@RequestBody User user) throws IOException, ParseException {
        return usersService.signUp(user);
    }
    @PostMapping("login")
    public boolean logIn(@RequestBody User user) throws IOException, ParseException {
        return usersService.logIn(user);
    }
    @PostMapping("sendingmail/{senderEmail}")
    public boolean sendingMail(@PathVariable String senderEmail, @RequestBody Mail mail) throws IOException, ParseException {
//        System.out.println(mail);
        return usersService.sendingMail(senderEmail, mail);
    }
    @GetMapping("inbox/{userEmail}")
    public JSONArray getInbox(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getInbox(userEmail);
    }
    @GetMapping("sent/{userEmail}")
    public JSONArray getSent(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getSent(userEmail);
    }
    @GetMapping("draft/{userEmail}")
    public JSONArray getDraft(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getDraft(userEmail);
    }
    @GetMapping("trash/{userEmail}")
    public JSONArray getTrash(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getTrash(userEmail);
    }
    @GetMapping("important/{userEmail}")
    public JSONArray getImportant(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getImportant(userEmail);
    }
    @GetMapping("read/{userEmail}")
    public JSONArray getRead(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getRead(userEmail);
    }
    @GetMapping("unread/{userEmail}")
    public JSONArray getunRead(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getunRead(userEmail);
    }
    @PostMapping("draftingmail/{userEmail}")
    public void draftingMail(@PathVariable String userEmail, @RequestBody Mail mail) throws IOException, ParseException {
        usersService.draftingMail(userEmail, mail);
    }
    @DeleteMapping("deletingmail/{userEmail}/{id}")
    public void deletingMail(@PathVariable String userEmail, @PathVariable Long id) throws IOException, ParseException {
        usersService.deletingMail(userEmail, id);
    }
    @GetMapping("makingimportant/{userEmail}/{id}")
    public void makingImportant(@PathVariable String userEmail, @PathVariable Long id) throws IOException, ParseException {
        usersService.makingImportant(userEmail, id);
    }
    @GetMapping("makingunimportant/{userEmail}/{id}")
    public void makingUnImportant(@PathVariable String userEmail, @PathVariable Long id) throws IOException, ParseException {
        usersService.makingUnImportant(userEmail, id);
    }
    @GetMapping("makingread/{userEmail}/{id}")
    public void makingRead(@PathVariable String userEmail, @PathVariable Long id) throws IOException, ParseException {
        usersService.makingRead(userEmail, id);
    }
    @GetMapping("search/{userEmail}/{searchKey}")
    public JSONArray search(@PathVariable String userEmail, @PathVariable String searchKey) throws IOException, ParseException {
        return usersService.search(userEmail, searchKey);
    }
    @GetMapping("makingunread/{userEmail}/{id}")
    public void makingUnread(@PathVariable String userEmail, @PathVariable Long id) throws IOException, ParseException {
        usersService.makingUnread(userEmail,id);
    }
    @GetMapping("sortinbox/{userEmail}")
    public JSONArray sortInbox(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.sortInbox(userEmail);
    }
    @GetMapping("sortimportance/{userEmail}")
    public JSONArray sortImportance(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.sortImportance(userEmail);
    }
    @GetMapping("contacts/{userEmail}")
    public JSONArray getcontacts(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getcontacts(userEmail);
    }
    @PostMapping("addcontact/{userEmail}/{contactEmail}")
    public boolean addContact(@PathVariable String userEmail, @PathVariable String contactEmail) throws IOException, ParseException {
        return usersService.addContact(userEmail, contactEmail);
    }
    @DeleteMapping("deletecontact/{userEmail}/{contactEmail}")
    public void deleteContact(@PathVariable String userEmail, @PathVariable String contactEmail) throws IOException, ParseException {
        usersService.deleteContact(userEmail, contactEmail);
    }
    //----------------------
    // attachment manipulation
    @PostMapping("/uploadattachment/{senderEmail}/{receiverEmail}/{id}")
    public void uploadAttachments(@RequestParam("files")List<MultipartFile> multipartFiles
            , @PathVariable String senderEmail
            , @PathVariable String receiverEmail
            , @PathVariable long id) throws IOException, ParseException {
        usersService.uploadAttachments(multipartFiles, senderEmail, receiverEmail, id);
    }
    @GetMapping("downloadattachment/{userEmail}/{id}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable String userEmail, @PathVariable("id") long id) throws IOException {
        return usersService.downloadAttachment(userEmail, id);
    }

}
