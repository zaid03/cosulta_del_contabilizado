
import { Component, OnInit } from '@angular/core';
import { CommonModule, JsonPipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { forkJoin, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  //global variables
  usucod: string | null = null;
  perfil: string | null = null;
  entcod: string | null = null;
  eje: number | null = null;
  cge: string = '';
  esContable: boolean = false;
  Estado: number = 0;
  esComprador: boolean = false;
  esAlmacen: boolean = false;
  allowedMnucods: string[] = [];
  version: string = '';
  logoPath = 'assets/images/logo_iass.png';

  //menu variables
  organigrama = false;
  proveedores = false;
  contabilidad = false;
  articulos = false;
  temp = false;
  constructor(private http: HttpClient, private router: Router) {}

  //main functions
  ngOnInit(): void {
    const profile = sessionStorage.getItem('Perfil');
    const user = sessionStorage.getItem('USUCOD');
    const ent = sessionStorage.getItem('Entidad');
    const session = sessionStorage.getItem('EJERCICIO');
    const centroGestor = sessionStorage.getItem('CENTROGESTOR');
    const status = sessionStorage.getItem('ESTADOGC');
    const contable = sessionStorage.getItem('EsContable');
    const comprador = sessionStorage.getItem('EsComprador');
    const almcenar = sessionStorage.getItem('EsAlmacen');
    const menus = sessionStorage.getItem('mnucods');

    if (profile) { const parsed = JSON.parse(profile); this.perfil = parsed.PERCOD;}
    if (user) { this.usucod = user;}
    if (ent) { const parsed = JSON.parse(ent); this.entcod = parsed.ENTCOD;}
    if (session) { const parsed = JSON.parse(session); this.eje = parsed.eje;}
    if (centroGestor) { const parsed = JSON.parse(centroGestor); this.cge = parsed.value;}
    if (status) { const parsed = JSON.parse(status); this.Estado = parsed.value;}
    if (contable) { const parsed = JSON.parse(contable); this.esContable = parsed.value;}
    if (comprador) { const parsed = JSON.parse(comprador); this.esComprador = parsed.value;}
    if (almcenar) { const parsed = JSON.parse(almcenar); this.esAlmacen = parsed.value;}
    if (menus) {
      const parsed = JSON.parse(menus);
      this.allowedMnucods = parsed
        .map((m: any) =>
          typeof m === 'string'
            ? m
            : (m.MNUCOD ?? m.mnucod ?? m.MENUCOD ?? m.code ?? m.codigo ?? m.id))
        .filter(Boolean);
    }

    if (!this.usucod || this.entcod == null || !this.perfil || !this.allowedMnucods) {
      alert('Missing session data. reiniciar el flujo.');
      this.router.navigate(['/login']);
      return;
    }
    
    if (this.Estado > 0) { this.getStatus(this.Estado); }
    this.fetchVersion();
  }

  fetchVersion() {
    this.http.get<any>(`${environment.backendUrl}/api/version/num`).subscribe({
      next: (res) => {
        this.version = res.version;
      },
      error: (err) => {
        console.warn('versión no encontrada');
        return;
      }
    })
  }

  isDisabled(code: string): boolean {
    if ((code === 'acFac' || code === 'acMCon' || code === 'acRCon') && !this.esContable) {
      return true;
    }
    if (code === 'acGBSM' && !this.cge) {
      return true;
    }

    return this.allowedMnucods.includes(code);
  }

  navigateTo(code: string): void {
    switch (code) {
      case 'ejercicios':
        this.router.navigate(['/ejercicios']);
        break;
      case 'centroGestor':
        this.router.navigate(['/centroGestor']);
        break;
      case 'coste':
        this.router.navigate(['/coste']);
        break;
      case 'servicios':
        this.router.navigate(['/servicios']);
        break;
      case 'personas':
        this.router.navigate(['/persona']);
        break;
      case 'personas-por-servicios':
        this.router.navigate(['/personas-por-servicios']);
        break;
      case 'entrega':
        this.router.navigate(['/entrega']);
        break;
      case 'Cproveedores':
        this.router.navigate(['/Cproveedores']);
        break;
      case 'proveedorees':
        this.router.navigate(['/proveedorees']);
        break;
      case 'contratos':
        this.router.navigate(['/contratos']);
        break;
      case 'Cfactura':
        this.router.navigate(['/Cfactura']);
        break;
      case 'facturas':
        this.router.navigate(['/facturas']);
        break;
      case 'contabilizacion':
        this.router.navigate(['/contabilizacion']);
        break;
      case 'Fcontabilizadas':
        break;
      case 'Ccontabilizado':
        this.router.navigate(['/Ccontabilizado']);
        break;
      case 'Ccontabilizar':
        this.router.navigate(['/Ccontabilizar']);
        break;
      case 'Ccredito':
        this.router.navigate(['Ccredito']);
        break;
      case 'credito':
        this.router.navigate(['credito']);
        break;
      case 'credito-Cge':
        this.router.navigate(['credito-Cge']);
        break;
      case 'familia':
        this.router.navigate(['/familia'])
        break;
      case 'almacenaje':
        this.router.navigate(['/almacenaje']);
        break;
      case 'unidades':
        this.router.navigate(['/unidades']);
        break;
      default:
        break;
    }
  }

  centroGestorStatus: string = '';
  getStatus(estado: number) {
    if (estado === 1) {
      return this.centroGestorStatus = 'Centro Gestor CERRADO'
    }
    if (estado === 2) {
      return this.centroGestorStatus = 'Centro Gestor CERRADO para CONTABILIZAR'
    }
    return;
  }

  logout(): void {
    sessionStorage.clear();
    window.location.href = `${environment.casLoginUrl.replace('/login', '/logout')}?service=${environment.frontendUrl}/login`;
  }

  goToCge() {
    this.router.navigate(['/centro-gestor']);
  }
  
  //services grid functions
  showServices: boolean = false;
  
  launchAddCentroGestor() {
    this.showServices = true;
    this.fetchServices();
  }

  closeShowServices() {
    this.showServices = false;
  }

  servicesError: string = '';
  services: any[] = [];
  page: number = 0;
  pageSize: number = 20;
  private fetchServices(): void {
    this.servicesError = '';
    if (this.entcod === null || this.eje === null) return;

    this.http.get<any[]>(`${environment.backendUrl}/api/dep/fetch-services-persona/${this.entcod}/${this.eje}/${this.usucod}`).subscribe({
      next: (res) => {

        const requests = res.map(res =>
          this.http.get(`${environment.backendUrl}/api/cge/fetch-description-services/${this.entcod}/${this.eje}/${res.cgecod}`, { responseType: 'text' })
            .pipe(
              map(cgedes => ({ ...res, cgedes })),
              catchError(() => of({ ...res, cgedes: null }))
            )
        );
        forkJoin(requests).subscribe({
          next: (servicesWithDescriptions) => {
            this.services = servicesWithDescriptions;
            this.page = 0;
          },
          error: (err) => {
            this.servicesError = 'Error al obtener descripciones de centros gestores';
            console.error(err);
          }
        });
        this.page = 0;
      },
      error: (err) => {
        this.servicesError = err.error.error ?? err.error;
      }
    });
  }
  
  get paginatedServices(): any[] {
    if (!this.services || this.services.length === 0) return [];
    const start = this.page * this.pageSize;
    return this.services.slice(start, start + this.pageSize);
  }
  get totalPages(): number {
    return Math.max(1, Math.ceil((this.services?.length ?? 0) / this.pageSize));
  }
  prevPage(): void {
    if (this.page > 0) this.page--;
  }
  nextPage(): void {
    if (this.page < this.totalPages - 1) this.page++;
  }
  goToPage(event: any): void {
    const inputPage = Number(event.target.value);
    if (inputPage >= 1 && inputPage <= this.totalPages) {
      this.page = inputPage - 1;
    }
  }
}
