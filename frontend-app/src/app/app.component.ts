import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { initFlowbite } from 'flowbite';
import { AdminModuleModule } from './admin-module/admin-module.module';
import { AuthModuleModule } from './auth-module/auth-module.module';
import { PublicModuleModule } from './public-module/public-module.module';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AdminModuleModule, AuthModuleModule, PublicModuleModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  ngOnInit(): void {
    initFlowbite();
  }
  title = 'frontend-app';
  
}
