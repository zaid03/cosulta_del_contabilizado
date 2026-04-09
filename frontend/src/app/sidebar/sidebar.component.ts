import { Component, Output, EventEmitter, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';

function safeParse(raw: string | null) {
  if (!raw) return {};
  try { return JSON.parse(raw); } catch { return {}; }
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, FormsModule ],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  //global variables
  usucod: string | null = null;
  perfil: string | null = null;
  cge: string = '';
  esContable: boolean = false;
  allowedMnucods: string[] = [];
  logoPath = 'assets/images/logo_iass.png';

  //menu variables
  organigrama = false;
  proveedores = false;
  contabilidad = false;
  articulos = false;
  temp = false;
  constructor(private router: Router) {}

  ngOnInit(): void {
    const profile = sessionStorage.getItem('Perfil');
    const user = sessionStorage.getItem('USUCOD');
    const centroGestor = sessionStorage.getItem('CENTROGESTOR');
    const contable = sessionStorage.getItem('EsContable');
    const menus = sessionStorage.getItem('mnucods');

    if (profile) { const parsed = JSON.parse(profile); this.perfil = parsed.PERCOD;}
    if (user) { this.usucod = user;}
    if (centroGestor) { const parsed = JSON.parse(centroGestor); this.cge = parsed.value;}
    if (contable) { const parsed = JSON.parse(contable); this.esContable = parsed.value;}
    if (menus) {
      const parsed = JSON.parse(menus);
      this.allowedMnucods = parsed
        .map((m: any) =>
          typeof m === 'string'
            ? m
            : (m.MNUCOD ?? m.mnucod ?? m.MENUCOD ?? m.code ?? m.codigo ?? m.id))
        .filter(Boolean);
    }
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
      case 'panel':
        this.router.navigate(['/dashboard']);
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

  logout(): void {
    sessionStorage.clear();
    window.location.href = `${environment.casLoginUrl.replace('/login', '/logout')}?service=${environment.frontendUrl}/login`;
  }

  collapsed = true;
  @Output() collapsedChange = new EventEmitter<boolean>();

  toggleSidebar() {
    this.collapsed = !this.collapsed;
    this.collapsedChange.emit(this.collapsed);
  }
}