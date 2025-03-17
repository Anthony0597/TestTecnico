# 💬 Foro 
Este proyecto de Angular es un foro en el que los usuarios pueden crear temas y discutir sobre ellos. A continuación, se detallan los componentes y funcionalidades del proyecto.

<p align="center">
<img src="https://img.shields.io/badge/-Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white" alt="Angular Icon" />
<img src="https://img.shields.io/badge/-Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white" alt="Bootstrap Icon" />
<img src="https://img.shields.io/badge/-Toastr-0078D4?style=for-the-badge" alt="Toastr Icon" />

</p>

## Design 🎨
<p align="center">
<img src="./src/assets/foroMockup.png" alt="Design" />


</p>


### Paleta de colores 🖌️
<p align="center">
<img src="https://img.shields.io/badge/-A7D2CB-A7D2CB?style=for-the-badge&logo=angular&logoColor=white" alt="F2D388" />
<img src="https://img.shields.io/badge/-F2D388-F2D388?style=for-the-badge&logo=angular&logoColor=white" alt="F2D388" />
<img src="https://img.shields.io/badge/-C98474-C98474?style=for-the-badge&logo=angular&logoColor=white" alt="C98474" />
<img src="https://img.shields.io/badge/-874C62-874C62?style=for-the-badge&logo=angular&logoColor=white" alt="874C62" />

[Ver online](https://colorhunt.co/palette/a7d2cbf2d388c98474874c62)
</p>

## 📝 Componentes

* **HomeComponent**: Es el componente principal de la aplicación y se encarga de mostrar la lista de temas y permitir la creación, edición y eliminación de temas. También se utiliza para mostrar la lista de mensajes de un tema específico.

* **LoginComponent**: Se utiliza para realizar el login de un usuario en la aplicación. Se utiliza un formulario para ingresar el nombre de usuario y la contraseña.

* **MenuComponent**: este componente se utiliza para mostrar el menú de navegación de la aplicación. También se utiliza para mostrar el nombre de usuario logueado y permitir la salida de la sesión.

* **PostComponent**: este componente se utiliza para mostrar la lista de mensajes de un tema específico. También se utiliza para crear nuevos mensajes en un tema.

* **UsersComponent**: Se usa para mostrar la lista de usuarios registrados en la aplicación.

## Características de la aplicación
* **Login**: Se utiliza un formulario para ingresar el nombre de usuario y la contraseña.

* **Paginación**: la aplicación utiliza la paginación para mostrar la lista de temas de manera paginada, lo que mejora la usabilidad y rendimiento de la aplicación.

* **Routing**: la aplicación utiliza el módulo RouterModule de Angular para definir las rutas de la aplicación. Se crearon varias rutas para los diferentes componentes de la aplicación, como login, home, post, user, etc.

* **Servicios**: la aplicación utiliza varios servicios para obtener y manipular datos de la API REST del proyecto. Se creó el servicio ApiRestService para realizar las operaciones CRUD en la API REST del proyecto.

## 📝 Servicios
**`ApiRestService`**: este componente es un servicio que se utiliza para realizar las operaciones CRUD consumiendo la  API REST montada en el [ITSCH](https://cdhidalgo.tecnm.mx/) esta se utiliza en varios componentes para obtener y manipular datos de la API REST.


### Instalación de dependencias
Para instalar las dependencias del proyecto se debe ejecutar el siguiente comando en la terminal:
```bash
npm install
```
## Probar app en la web 📌
 Ir a:
 ```
  https://angular-foro-app.surge.sh/
 ```

## Recursos utilizados 📑
[Toastr](https://www.npmjs.com/package/ngx-toastr) - Para mostrar mensajes de alerta en la aplicación.

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.3.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.
