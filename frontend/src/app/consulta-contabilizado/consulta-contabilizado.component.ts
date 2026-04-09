import { Component, HostListener} from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule, JsonPipe } from '@angular/common';
import { DatePipe } from '@angular/common';
import { SidebarComponent } from '../sidebar/sidebar.component';

import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { environment } from '../../environments/environment';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-consulta-contabilizado',
  standalone: true,
  imports: [ CommonModule ,FormsModule, SidebarComponent],
  providers: [DatePipe],
  templateUrl: './consulta-contabilizado.component.html',
  styleUrls: ['./consulta-contabilizado.component.css']
})
export class ConsultaContabilizadoComponent {
  //3 dots menu 
  showMenu = false;
  toggleMenu(event: MouseEvent): void {
    event.stopPropagation();
    this.showMenu = !this.showMenu;
  }

  @HostListener('document:click')
  closeMenu(): void {
    this.showMenu = false;
  }

  //global variables
  private entcod: number | null = null;
  private eje: number | null = null;
  facturas: any[] = [];
  private backupFacturas: any[] = [];
  page = 0;
  pageSize = 20;
  public Math = Math;

  constructor(private http: HttpClient, private router: Router, private datePipe: DatePipe ) {}

  isLoading: boolean = false;
  facturaSuccess: string = '';
  facturaError: string = '';
  ngOnInit(): void{
    this.limpiarMEssages();
    const entidad = sessionStorage.getItem('Entidad');
    const eje = sessionStorage.getItem('EJERCICIO'); 

    if (entidad) {const parsed = JSON.parse(entidad); this.entcod = parsed.ENTCOD;}
    if (eje) {const parsed = JSON.parse(eje); this.eje = parsed.eje;}

    if (!entidad || this.entcod === null || !eje || this.eje === null) {
      sessionStorage.clear();
      alert('Debes iniciar sesión para acceder a esta página.');
      this.router.navigate(['/login']);
      return;
    }

    this.fetchFacturas();
  }

  fetchFacturas() {
    this.isLoading = true;
    this.limpiarMEssages();

    this.http.get<any>(`${environment.backendUrl}/api/fde/fetch-contabilizado/${this.entcod}/${this.eje}`).subscribe({
      next: (res) => {
        this.isLoading = false;
        this.facturas = res;
        this.backupFacturas = [...this.facturas];
        this.updatePagination;
      },
      error: (err) => {
        this.facturaError = err.error.error || err.error;
        this.isLoading = false;
      }
    })
  }
  private updatePagination(): void {const total = this.totalPages;
    if (total === 0) {this.page = 0; return;}
    if (this.page >= total) {this.page = total - 1;}
  }
  get paginatedFacturas(): any[] {if (!this.facturas || this.facturas.length === 0) return []; const start = this.page * this.pageSize; return this.facturas.slice(start, start + this.pageSize);}
  get totalPages(): number {return Math.max(1, Math.ceil((this.facturas?.length ?? 0) / this.pageSize));}
  prevPage(): void {if (this.page > 0) this.page--;}
  nextPage(): void {if (this.page < this.totalPages - 1) this.page++;}
  goToPage(event: any): void {const inputPage = Number(event.target.value); if (inputPage >= 1 && inputPage <= this.totalPages) {this.page = inputPage - 1;}}

  importe(fdeimp: number, fdedif: number) {
    if (!fdeimp || !fdedif) {return}
    return fdeimp + fdedif;
  }

  //main table functions
  sortField: 'facnum' | 'facann' | 'facfac' | 'facdoc' | 'facdat' | 'facfco' | 'fdeorg' | 'fdefun' | 'fdeeco' | 'limporte' | 'ternif' | 'ternom' |'cgecod' | 'cgecod' | null = null;
  sortColumn: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  toggleSort(column: string) {
    if (this.sortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }
    this.applySort();
    this.page = 0;
    this.updatePagination();
  }

  private applySort(): void {
    if (!this.sortColumn) return;
    this.facturas.sort((a, b) => {
      let aValue: any;
      let bValue: any;

      if (this.sortColumn === 'limporte') {
        aValue = (a.fdeimp || 0) + (a.fdedif || 0);
        bValue = (b.fdeimp || 0) + (b.fdedif || 0);
      } else if (this.sortColumn === 'ternif') {
        aValue = a.fac?.ter?.ternif;
        bValue = b.fac?.ter?.ternif;
      } else if (this.sortColumn === 'ternom') {
        aValue = a.fac?.ter?.ternom;
        bValue = b.fac?.ter?.ternom;
      } else if (this.sortColumn === 'cgecod') {
        aValue = a.fac?.cgecod;
        bValue = b.fac?.cgecod;
      } else if (this.sortColumn === 'facann') {
        aValue = a.fac?.facann;
        bValue = b.fac?.facann;
      } else if (this.sortColumn === 'facfac') {
        aValue = a.fac?.facfac;
        bValue = b.fac?.facfac;
      } else if (this.sortColumn === 'facdat') {
        aValue = a.fac?.facdat;
        bValue = b.fac?.facdat;
      } else if (this.sortColumn === 'facfco') {
        aValue = a.fac?.facfco;
        bValue = b.fac?.facfco;
      } else if (this.sortColumn === 'facdoc') {
        aValue = a.fac?.facdoc;
        bValue = b.fac?.facdoc;
      } else {
        aValue = a[this.sortColumn];
        bValue = b[this.sortColumn];
      }

      const aNum = Number(aValue);
      const bNum = Number(bValue);
      if (!isNaN(aNum) && !isNaN(bNum)) {
        return this.sortDirection === 'asc' ? aNum - bNum : bNum - aNum;
      }

      aValue = (aValue ?? '').toString().toUpperCase();
      bValue = (bValue ?? '').toString().toUpperCase();
      if (aValue < bValue) return this.sortDirection === 'asc' ? -1 : 1;
      if (aValue > bValue) return this.sortDirection === 'asc' ? 1 : -1;
      return 0;
    });
  }
  private startX: number = 0;
  private startWidth: number = 0;
  private resizingColIndex: number | null = null;
  startResize(event: MouseEvent, colIndex: number) {
    this.resizingColIndex = colIndex;
    this.startX = event.pageX;
    const th = (event.target as HTMLElement).parentElement as HTMLElement;
    this.startWidth = th.offsetWidth;

    document.addEventListener('mousemove', this.onResizeMove);
    document.addEventListener('mouseup', this.stopResize);
  }

  onResizeMove = (event: MouseEvent) => {
    if (this.resizingColIndex === null) return;
    const table = document.querySelector('.main-table') as HTMLTableElement;
    if (!table) return;
    const th = table.querySelectorAll('th')[this.resizingColIndex] as HTMLElement;
    if (!th) return;
    const diff = event.pageX - this.startX;
    th.style.width = (this.startWidth + diff) + 'px';
  };

  stopResize = () => {
    document.removeEventListener('mousemove', this.onResizeMove);
    document.removeEventListener('mouseup', this.stopResize);
    this.resizingColIndex = null;
  };

  proveedor: string = '';
  centroGestor: string = '';
  economica: string = '';
  ano: number | null = null;
  search() {
    this.isLoading = true;
    this.limpiarMEssages();

    let params = new HttpParams().set('ent', this.entcod || '').set('eje', this.eje || '');
    if (this.proveedor) params = params.set('proveedor', this.proveedor);
    if (this.centroGestor) params = params.set('centroGestor', this.centroGestor);
    if (this.economica) params = params.set('economica', this.economica);
    if (this.ano !== null) params = params.set('ano', this.ano);

    this.http.get<any>(`${environment.backendUrl}/api/fde/search-contabilizado`, { params }).subscribe({
      next: (res) => {
        this.isLoading = false;
        this.facturas = res;
        this.updatePagination();
      },
      error: (err) => {
        this.isLoading = false;
        this.facturaError = err.error.error || err.error;
      }
    })
  }

  limpiarSearch() {
    this.proveedor = '';
    this.centroGestor = '';
    this.economica = '';
    this.ano = null;
    this.fetchFacturas();
  }

  DownloadPDF() {
    this.limpiarMEssages();

    const source = this.paginatedFacturas;
    if (!source?.length) {
      this.facturaError = 'No hay datos para exportar.';
      return;
    }

    const rows = source.map((row: any) => ({
      facnum: row.facnum ?? '',
      facann: row.fac.facann ?? '',
      facfac: row.fac.facfac ?? '',
      facdoc: row.fac.facdoc ?? '',
      facdat: this.formatDate(row.fac.facdat) ?? '',
      facfco: this.formatDate(row.fac.facfco) ?? '',
      fdeorg: row.fdeorg ?? '',
      fdefun: row.fdefun ?? '',
      fdeeco: row.fdeeco ?? '',
      importe: this.importe(row.fdeimp, row.fdedif),
      ternif: row.fac.ter.ternif,
      ternom: row.fac.ter.ternom,
      cgecod: row.fac.cgecod
    }));

    const columns = [
      { header: 'Número', dataKey: 'facnum' },
      { header: 'Año Factura', dataKey: 'facann' },
      { header: 'Nº Registro C.F', dataKey: 'facfac' },
      { header: 'Referencia Fact', dataKey: 'facdoc' },
      { header: 'Fecha Factura', dataKey: 'facdat' },
      { header: 'Fecha Contable', dataKey: 'facfco' },
      { header: 'Orgánica', dataKey: 'fdeorg' },
      { header: 'Programa', dataKey: 'fdefun' },
      { header: 'Económica', dataKey: 'fdeeco' },
      { header: 'Importe', dataKey: 'importe' },
      { header: 'NIF', dataKey: 'ternif' },
      { header: 'Nombre', dataKey: 'ternom' },
      { header: 'Centro Gestor', dataKey: 'cgecod' }
    ];

    const doc = new jsPDF({ orientation: 'landscape', unit: 'mm', format: 'a4' });
    doc.setFont('helvetica', 'normal');
    doc.setFontSize(14);
    doc.text('Listado de facturas del contabilizado', 10, 20);

    autoTable(doc, {
      startY: 30,
      theme: 'plain',
      head: [columns.map(c => c.header)],
      body: rows.map(row => columns.map(c => row[c.dataKey as keyof typeof row] ?? '')),
      styles: { font: 'helvetica', fontSize: 8, cellPadding: 4 },
      headStyles: {
        fillColor: [240, 240, 240],
        textColor: [33, 53, 71],
        fontStyle: 'bold',
        halign: 'left'
      },
      tableLineColor: [200, 200, 200],
      tableLineWidth: 0.5,
      columnStyles: {
        facnum: { cellWidth: 10 },
        facann: { cellWidth: 10 },
        facfac: { cellWidth: 12 },
        facdoc: { cellWidth: 20 },
        facdat: { cellWidth: 20 },
        facfco: { cellWidth: 20 },
        fdeorg: { cellWidth: 20 },
        fdefun: { cellWidth: 15 },
        fdeeco: { cellWidth: 15 },
        importe: { cellWidth: 15 },
        ternif: { cellWidth: 15 },
        ternom: { cellWidth: 15 },
        cgecod: { cellWidth: 25 }
      }
    });
    doc.save('consulta_del_contabilizado.pdf');
  }

  downloadExcel() {
    this.limpiarMEssages();
    const rows = this.paginatedFacturas;
    if (!rows || rows.length === 0) {
      this.facturaError = 'No hay datos para exportar.';
      return;
    }
  
    const exportRows = rows.map(row => ({
      facnum: row.facnum ?? '',
      facann: row.fac.facann ?? '',
      facfac: row.fac.facfac ?? '',
      facdoc: row.fac.facdoc ?? '',
      facdat: this.formatDate(row.fac.facdat) ?? '',
      facfco: this.formatDate(row.fac.facfco) ?? '',
      fdeorg: row.fdeorg ?? '',
      fdefun: row.fdefun ?? '',
      fdeeco: row.fdeeco ?? '',
      importe: this.importe(row.fdeimp, row.fdedif),
      ternif: row.fac.ter.ternif,
      ternom: row.fac.ter.ternom,
      cgecod: row.fac.cgecod
    }));
  
    const worksheet = XLSX.utils.aoa_to_sheet([]);
    XLSX.utils.sheet_add_aoa(worksheet, [['Consulta del contabilizado']], { origin: 'A1' });
    worksheet['!merges'] = [{ s: { r: 0, c: 0 }, e: { r: 0, c: 3 } }];
    XLSX.utils.sheet_add_aoa(worksheet, [['Número', 'Año Factura', 'Nº Registro C.F', 'Referencia Fact', 'Fecha Factura', 'Fecha Contable', 'Orgánica', 'Programa', 'Económica', 'Importe', 'NIF', 'Nombre', 'Centro Gestor']], { origin: 'A2' });
    XLSX.utils.sheet_add_json(worksheet, exportRows, { origin: 'A3', skipHeader: true });

    worksheet['!cols'] = [
      { wch: 10 },
      { wch: 10 },
      { wch: 12 },
      { wch: 20 },
      { wch: 20 },
      { wch: 20 },
      { wch: 20 },
      { wch: 15 },
      { wch: 15 },
      { wch: 15 },
      { wch: 15 },
      { wch: 15 },
      { wch: 25 }
    ];
  
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Facturas');
    const buffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    saveAs(
      new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }),
      'consulta_del_contabilizado.xlsx'
    );
  }

  formatDate = (v: any) => {
    if (!v && v !== 0) return '';
    const s = String(v);
    const d = new Date(s);
    return isNaN(d.getTime()) ? s : d.toLocaleDateString('es-ES');
  };

  //misc
  limpiarMEssages() {
    this.facturaSuccess = '';
    this.facturaError = '';
  }
}