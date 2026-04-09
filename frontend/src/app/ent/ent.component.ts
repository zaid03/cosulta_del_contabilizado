import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { forkJoin, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Component({
  selector: 'app-ent',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ent.component.html',
  styleUrls: ['./ent.component.css']
})
export class EntComponent implements OnInit {
  tableData: any[] = [];
  loading = false;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void{
    const USUCOD = sessionStorage.getItem('USUCOD');
    if (!USUCOD) {
      alert('no hay sesión');
      this.logout();
      return;
    }

    this.http.get<any[]>(`${environment.backendUrl}/api/pua/filter/${USUCOD}`).subscribe({
      next: (res) => {

        const requests = res.map(item =>
          this.http.get<{ entnom: string }>(`${environment.backendUrl}/api/ent/name/${item.entcod}`).pipe(
            map(res => ({...item, entnom: res.entnom})),
            catchError(() => of({...item, entnom: null}))
          )
        );
        forkJoin(requests).subscribe({
          next: (entidadesConNombre) => {
            if (Array.isArray(entidadesConNombre) && res.length > 1) {
              this.tableData = entidadesConNombre;            
            }  else if (Array.isArray(res) && res.length === 1) {
              const row = res[0];
              this.tableData = [row];
              this.selectRow(row);
            } else {
              this.logout();
            }
          }
        })
      },
      error: (err) => {
        this.logout();
      }
    });
  }
  
  selectRow(t: any): void {
    sessionStorage.setItem('Entidad', JSON.stringify({ ENTCOD: t.entcod }));
    sessionStorage.setItem('Perfil', JSON.stringify({ PERCOD: t.percod }));
    this.router.navigate(['/eje']);
  }

  logout(): void {
    sessionStorage.setItem('fromLogout', 'true');
    localStorage.setItem('fromLogout', 'true');
    sessionStorage.clear();
    const casLogoutUrl = `${environment.casLoginUrl.replace('/login', '/logout')}`;
    const iframe = document.createElement('iframe');
    iframe.style.display = 'none';
    iframe.src = casLogoutUrl;
    document.body.appendChild(iframe);
    
    setTimeout(() => {
      document.body.removeChild(iframe);
      window.location.href = `${environment.frontendUrl}/login`;
    }, 1000);
  }
}