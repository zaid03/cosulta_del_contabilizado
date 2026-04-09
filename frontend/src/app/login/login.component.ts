import { Component, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  errormessage: string = '';
  isValidating = false;
  loadingMessage = 'Validando tus credenciales, por favor espera...';
  validationFailed = false;
  showLoginButton = false;

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const ticket = params['ticket'];
      const hasJwt = !!sessionStorage.getItem('JWT');

      if (hasJwt && !ticket) {
        this.router.navigate(['/ent']);
        return;
      }

      if (ticket) {
        this.validateCASTicket(ticket);
      } 
      else if (!hasJwt) {
        const fromLogout = localStorage.getItem('fromLogout');
        
        if (fromLogout) {
          localStorage.removeItem('fromLogout'); 
          this.showLoginButton = true;
          this.errormessage = 'No tiene acceso a esta aplicación';
        } else {
          this.goToCAS();
        }
      }
    });
  }

  validateCASTicket(ticket: string) {
    this.isValidating = true;
    this.validationFailed = false;
    this.errormessage = '';
    this.showLoginButton = false;

    const validateUrl = `${environment.casValidateUrl}?ticket=${ticket}&service=${environment.frontendUrl}/login`;

    this.http.get(validateUrl, { responseType: 'text' }).subscribe({
      next: (response: string) => {
        this.router.navigate([], {
          relativeTo: this.route,
          queryParams: {},
          replaceUrl: true
        });
        
        if (response.startsWith('yes')) {
          const lines = response.split('\n');
          const username = lines.length > 1 ? lines[1] : 'unknown';
          
          this.http.post(`${environment.backendUrl}/api/cas/validate`, {
            username: username, 
            validated: true
          }).subscribe({
            next: (backendResponse: any) => {
              if (backendResponse.success) {
                sessionStorage.setItem('JWT', backendResponse.token);
                sessionStorage.setItem('USUCOD', backendResponse.username);
                this.router.navigate(['/ent']);
              } else {
                this.isValidating = false;
                this.validationFailed = true;
                this.showLoginButton = true;
                this.errormessage = 'Acceso denegado. Tu usuario no tiene permisos.';
              }
            },
            error: (err) => {
              this.isValidating = false;
              this.validationFailed = true;
              this.showLoginButton = true;
              this.errormessage = 'Error al validar con el servidor: ' + (err.error?.message || err.message);
            }
          });
        } else {
          this.isValidating = false;
          this.validationFailed = true;
          this.showLoginButton = true;
          this.errormessage = 'Validación CAS fallida. Por favor, intenta de nuevo.';
        }
      },
      error: (error) => {
        this.isValidating = false;
        this.validationFailed = true;
        this.showLoginButton = true;
        this.router.navigate([], {
          relativeTo: this.route,
          queryParams: {},
          replaceUrl: true
        });
        this.errormessage = 'Error al conectar con CAS: ' + error.message;
      }
    });
  }

  logoPath = 'assets/images/logo_iass.png';

  goToCAS() {
    this.isValidating = true;
    this.validationFailed = false;
    this.errormessage = '';
    this.showLoginButton = false;
    window.location.href = `${environment.casLoginUrl}?service=${environment.frontendUrl}/login`;
  }

  login() {
    this.goToCAS();
  }
}