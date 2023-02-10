import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { ReadMessageComponent } from '../read-message/read-message.component';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css']
})
export class InboxComponent implements OnInit {
  emails:any = [];
  @Input() keySearch = '';
  element = document.getElementById("simSearch");
  constructor(private messageApp:ReadMessageComponent , private http:HttpClient,private myapp:AppComponent) { }
  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/inbox/'+ this.myapp.email + "/").subscribe(res => {
      this.emails = res;
    });
  }
  Search(){
    const searchKey = (<HTMLInputElement>document.getElementById("search")).value
    if(searchKey == null || searchKey == ''){
      return;
    }
    this.http.get<any[]>('http://localhost:8080/search/'+ this.myapp.email + "/" + searchKey +"/").subscribe(res => {
      this.emails = res;
    });
  }
  click_b(){
    console.log("nnnnnn");
  }
  reload(){
    this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email+"/").subscribe(res => {
      this.emails = res;
    });    
  }
  open(id:any){
    this.http.get<any[]>('http://localhost:8080/makingread/'+this.myapp.email+"/" + id +"/").subscribe();   
    //attachment
  }
  message:any = new Array();
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
    this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email+"/").subscribe(res => {
      this.emails = res;
    });
    this.message = new Array();
    this.reload()    
  }

  MakeImportant(){
    this.message.forEach( (item:any, index:any) => {
      this.http.get<any[]>('http://localhost:8080/makingimportant/' + this.myapp.email +"/"+item).subscribe();
    });
    this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email).subscribe(res => {
      this.emails = res;
    });
    this.message = new Array();
    this.reload();
  }

  MakeRead(){
    this.message.forEach( (item:any, index:any) => {
      this.http.get<any[]>('http://localhost:8080/makingread/' + this.myapp.email +"/"+item+"/").subscribe();
    });
    this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email+"/").subscribe(res => {
      this.emails = res;
    });
    this.message = new Array();
    this.reload();
  }

  MakeunRead(){
    this.message.forEach( (item:any, index:any) => {
      this.http.get<any[]>('http://localhost:8080/makingunread/' + this.myapp.email +"/"+item+"/").subscribe();
    });
    this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email+"/").subscribe(res => {
      this.emails = res;
    });
    this.message = new Array();
    this.reload();
  }

 

  onOptionsSelected(value:string){
    if(value == 'all'){
      this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email+"/").subscribe(res => {
        this.emails = res;
      });
    }else{
      this.http.get<any[]>('http://localhost:8080/' + value + '/'+this.myapp.email+"/").subscribe(res => {
        this.emails = res;
      });
    }
   
  }


  onOptionSortingSelected(value:string){
    if(value == 'none'){
      this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email+"/").subscribe(res => {
        this.emails = res;
      });
    }else{
      this.http.get<any[]>('http://localhost:8080/' + value + '/'+this.myapp.email+"/").subscribe(res => {
      this.emails = res;
    });
    }
    
  }



}
