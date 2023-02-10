import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-drafts',
  templateUrl: './drafts.component.html',
  styleUrls: ['./drafts.component.css']
})
export class DraftsComponent implements OnInit {
openDraftMessage(_t31: any) {
  this.myapp.WriteMessage = true;
  this.myapp.checkMessageForm = new FormGroup({
    to: new FormControl(_t31.receiver, Validators.required),
    subject: new FormControl(_t31.subject, Validators.required),
    content: new FormControl(_t31.body, [Validators.required])
  });
}

  emails:any = [];
  message:any = new Array();
  constructor(private http:HttpClient,private myapp:AppComponent) { }
  
  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/draft/'+this.myapp.email+"/").subscribe(res => {
      this.emails = res;
    }); 
  }

  reload(){
    this.http.get<any[]>('http://localhost:8080/draft/'+this.myapp.email+"/").subscribe(res => {
      this.emails = res;
    }); 
  }
  check(id:any){
    //alert(id)
    let x = false;
    this.message.forEach( (item:any, index:any) => {
      if(item === id) {
        x = true;
        this.message.splice(index,1);
      }
    })
    if(!x){
      this.message.push(id);
    }
  }
  delete(){
    //alert(this.message.length)
    this.message.forEach( (item:any, index:any) => {
      this.http.delete('http://localhost:8080/deletingmail/' + this.myapp.email +"/"+item+"/").subscribe();
    })
    this.http.get<any[]>('http://localhost:8080/draft/'+this.myapp.email+"/").subscribe(res => {
      this.emails = res;
    }); 
  }
}

