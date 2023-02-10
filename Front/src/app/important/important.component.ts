import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-important',
  templateUrl: './important.component.html',
  styleUrls: ['./important.component.css']
})
export class ImportantComponent implements OnInit {

  emails:any = [];
  constructor(private http:HttpClient,private myapp:AppComponent) { }
  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/important/'+this.myapp.email).subscribe(res => {
      this.emails = res;
    });   
  }

  reload(){
    this.http.get<any[]>('http://localhost:8080/important/'+this.myapp.email+"/").subscribe(res => {
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
    this.message= new Array();  
    this.reload(); 
  }

  makeUnimportant(){
    this.message.forEach( (item:any, index:any) => {
      this.http.get<any[]>('http://localhost:8080/makingunimportant/' + this.myapp.email +"/"+item).subscribe();
    });
    this.message = new Array(); 
    this.reload(); 
  }

  MakeRead(){
    this.message.forEach( (item:any, index:any) => {
      this.http.get<any[]>('http://localhost:8080/makingread/' + this.myapp.email +"/"+item+"/").subscribe();
    });
    this.message = new Array();
    this.reload();
  }

  MakeunRead(){
    this.message.forEach( (item:any, index:any) => {
      this.http.get<any[]>('http://localhost:8080/makingunread/' + this.myapp.email +"/"+item+"/").subscribe();
    });
    this.message = new Array();
    this.reload();
  }
  

}
