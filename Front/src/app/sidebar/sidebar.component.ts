import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
})


export class SidebarComponent implements OnInit {
  activeButton:String = "Inbox";
  menu: boolean = this.myapp.menu;
  static flag:boolean = true;
  constructor(private myapp:AppComponent) { }

  ngOnInit(): void {
  }
  openMessageWindow(){
    this.myapp.openMessageWindow();
    if(this.myapp.id==0){
      this.myapp.id = new Date(Date.now()).getTime();
    }
  }
  
    onButtonGroupClick($event: { target: any; srcElement: any; }){
      let clickedElement = $event.target || $event.srcElement;
      // alert(clickedElement.nodeName)
      // alert(clickedElement.className)
      clickedElement.className += " active";
      if( clickedElement.nodeName === "div" ) {
        
  
        let isCertainButtonAlreadyActive = clickedElement.parentElement.querySelector(".active");
        // if a Button already has Class: .active
        if( isCertainButtonAlreadyActive ) {
          isCertainButtonAlreadyActive.classList.remove("active");
        }
  
        clickedElement.className += " active";
      }
  
    }
    setActive(buttonName: String){
      this.activeButton = buttonName;
    }

    isActive(buttonName: String){
      return this.activeButton === buttonName;
    }

    static openSidebar(){
      //menu = !menu;
    }
}





