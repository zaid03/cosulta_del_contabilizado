import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

function safeParse(raw: string | null) {
  if (!raw) return {};
  try { return JSON.parse(raw); } catch { return {}; }
}

@Component({
  selector: 'app-eje',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './eje.component.html',
  styleUrls: ['./eje.component.css']
})
export class EjeComponent implements OnInit {
  tableData: any[] = [];
  loading = false;
  entcod: string | null = null;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    const ent = sessionStorage.getItem('Entidad');
    if (ent) { const parsed = JSON.parse(ent); this.entcod = parsed.ENTCOD;}

    if (this.entcod == null) {
      alert('Select entidad first.');
      this.router.navigate(['/ent']);
      return;
    }

    this.loading = true;
    this.http.get<any>(`${environment.backendUrl}/api/cfg/by-ent/${this.entcod}`)
      .subscribe({
        next: resp => {
          if (resp?.length > 1) {
            this.tableData = resp;
          } else if (resp?.length === 1) {
            sessionStorage.setItem('EJERCICIO', JSON.stringify({ eje: resp[0].eje }));
            this.router.navigate(['/centro-gestor']);
          } 
        },
        error: err => {
          alert(err.error);
          this.router.navigate(['/ent']);
        }
      }).add(() => this.loading = false);
  }

  selectRow(item: any): void {
    sessionStorage.setItem('EJERCICIO', JSON.stringify({ eje: item.eje }));
    this.router.navigate(['/centro-gestor']);
  }
}