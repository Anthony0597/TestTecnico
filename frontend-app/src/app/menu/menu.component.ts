import { Component, OnInit } from '@angular/core';
import { ApiRestService } from '../api-rest.service';



@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  user = { id: 0, username: '', role: '' };


  constructor(private rest: ApiRestService) { }

  ngOnInit(): void {
    //this.user = this.rest.getUser();
    this.rest.getUser();
    this.rest.userObs$.subscribe(
      user => {
        this.user = user;
      }

    );
  }

  salir() {
    this.rest.setUser({ id: 0, username: '', role: '' });
  }

}
