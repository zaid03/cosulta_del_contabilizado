import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-centrogestor',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './centrogestor.component.html',
  styleUrls: ['./centrogestor.component.css']
})
export class CentrogestorComponent implements OnInit {
  tableData: any[] = [];
  loading = false;
  perfil: string | null = null;
  usucod: string | null = null;
  entcod: string | null = null;
  eje: number | null = null;
  cge: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    const profile = sessionStorage.getItem('Perfil');
    const ent = sessionStorage.getItem('Entidad');
    const session = sessionStorage.getItem('EJERCICIO');
    const centroGestor = sessionStorage.getItem('CENTROGESTOR');
    const user = sessionStorage.getItem('USUCOD');

    if (profile) { const parsed = JSON.parse(profile); this.perfil = parsed.PERCOD;}
    if (ent) { const parsed = JSON.parse(ent); this.entcod = parsed.ENTCOD;}
    if (session) { const parsed = JSON.parse(session); this.eje = parsed.eje;}
    if (centroGestor) { const parsed = JSON.parse(centroGestor); this.cge = parsed.value;}
    if (user) {this.usucod = user}

    this.loading = true;
    this.fetchMenus();
    this.http.get<any[]>(`${environment.backendUrl}/api/cge/${this.entcod}/${this.eje}/${this.usucod}`)
    .subscribe({
      next: resp => {
        if (resp?.length > 1) {
          this.tableData = resp;
        } else if (resp?.length === 1) {
          this.storeAndGo(resp[0]);
        } else {
          sessionStorage.setItem('CENTROGESTOR', JSON.stringify({ value: null}));
          sessionStorage.setItem('CENTROGESTOR_NAME', JSON.stringify({ value: null}));
          sessionStorage.setItem('ESTADOGC', JSON.stringify({ value: 0}));
          sessionStorage.setItem('EsContable', JSON.stringify({value: false}));
          sessionStorage.setItem('EsComprador', JSON.stringify({value: false}));
          sessionStorage.setItem('EsAlmacen', JSON.stringify({value : false}));
          this.router.navigate(['/dashboard']);
        }
      },
      error: err => {
        console.error('CentroGestor error', err);
        alert('Error cargando centro gestor.');
        this.router.navigate(['/dashboard']);
      }
    }).add(() => this.loading = false);
  }

  private storeAndGo(item: any) {
    sessionStorage.setItem('CENTROGESTOR', JSON.stringify({ value: item.cge.cgecod }));
    sessionStorage.setItem('CENTROGESTOR_NAME', JSON.stringify({ value: item.cge.cgedes}));
    sessionStorage.setItem('ESTADOGC', JSON.stringify({ value: item.cge.cgecic}));
    if (item.depint === 1) { sessionStorage.setItem('EsContable', JSON.stringify({ value: true})); }
    if (item.depint === 0){ sessionStorage.setItem('EsContable', JSON.stringify({ value: false})); }
    if (item.depalm === 1) { sessionStorage.setItem('EsAlmacen', JSON.stringify({ value: true})); }
    if (item.depalm === 0){ sessionStorage.setItem('EsAlmacen', JSON.stringify({ value: false})); }
    if (item.depcom === 1) { sessionStorage.setItem('EsComprador', JSON.stringify({ value: true})); }
    if (item.depcom === 0){ sessionStorage.setItem('EsComprador', JSON.stringify({ value: false})); }
    this.router.navigate(['/dashboard']);
  }

  selectRow(item: any) {
    if (!item) return;
    this.storeAndGo(item);
  }

  cancelar() {
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }

  continuarSin() {
    sessionStorage.setItem('CENTROGESTOR', JSON.stringify({ value: null }));
    sessionStorage.setItem('CENTROGESTOR_NAME', JSON.stringify({ value: null}));
    sessionStorage.setItem('ESTADOGC', JSON.stringify({ value: 0}));
    sessionStorage.setItem('EsContable', JSON.stringify({ value: false}));
    sessionStorage.setItem('EsComprador', JSON.stringify({value: false}));
    sessionStorage.setItem('EsAlmacen', JSON.stringify({value : false}));
    this.router.navigate(['/dashboard']);
  }

  fetchMenus() {
    if(this.perfil === ''){return;}
    this.http.get<any[]>(`${environment.backendUrl}/api/mnucods`, { 
    params: { PERCOD: this.perfil || '' } })
    .subscribe({
      next: resp => {
        sessionStorage.setItem('mnucods', JSON.stringify(resp));
      },
      error: err => {
        console.warn("Los menús no se encuentran por ninguna parte.");
      }
    });
  }
}