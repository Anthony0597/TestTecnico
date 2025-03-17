import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http'
import { BehaviorSubject } from 'rxjs';


const URL = "https://devel.cdhidalgo.tecnm.mx/~iraic/foro-rest";


//indiucar los tipos ode datos que vamos a aobtener
interface User {
  id: number,
  username: string,
  role: string
}

interface Login {
  user: User,
  token: string
}

@Injectable({
  providedIn: 'root'
})
export class ApiRestService {
  private user: User = { id: 0, username: '', role: '' };
  private userObs = new BehaviorSubject<User>(this.user);
  userObs$ = this.userObs.asObservable();

  constructor(private http: HttpClient) { }

  setUser(user: User) {
    localStorage.setItem('id', user.id.toString());
    localStorage.setItem('username', user.username);
    localStorage.setItem('role', user.role);
    
    this.user = user;
    this.userObs.next(this.user);/*  */
  }

  getUser() {
    this.user.id = parseInt(localStorage.getItem('id') || '0');
    this.user.username = (localStorage.getItem('username') || '');
    this.user.role = (localStorage.getItem('role') || '');

    return this.user;/* .username */    
  }

  login(user: string, pass: string) {
    return this.http.get<Login>(URL + '/login',{ params: { username: user, password: pass }});
  }


    // usanso any podemos omitir declara la interfaz
  getTopics(url:string) {
    if(url == '') url  = URL +'/topics';/* si hay una url vacia muestr ala primer pagina */
    const token = localStorage.getItem('token') || '';/* recupara el token */
   return this.http.get<any>(url, {headers:{Authorization:token}}); /* enviar/asignar el token par aeviar el error de autorizacion */    
  }

  /* agregar crear un nuevo tema POST (topic)*/
  postTopic(topic:any) {
    const token = localStorage.getItem('token') || '';
    return this.http.post<any>(URL +'/topics',{title:topic.title},/* -- [(ngModel)]="newTopic.title"-- */
     {headers:{Authorization:token}});/* enviar codig de autorizacion */
  }

//edit (actualizar
  putTopic(topic:any) {
    const token = localStorage.getItem('token') || '';
    return this.http.put<any>(URL +'/topics/'+topic.id,{title:topic.title},//enviamos el ttitul a modificar
     {headers:{Authorization:token}});
  }


  deleteTopic(topic:any) {
    const token = localStorage.getItem('token') || '';
    return this.http.delete<any>(URL +'/topics/'+topic.id,
     {headers:{Authorization:token}});
  }


}
/*

*/
