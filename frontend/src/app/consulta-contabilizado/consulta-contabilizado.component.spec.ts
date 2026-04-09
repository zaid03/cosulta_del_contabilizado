import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultaContabilizadoComponent } from './consulta-contabilizado.component';

describe('ConsultaContabilizadoComponent', () => {
  let component: ConsultaContabilizadoComponent;
  let fixture: ComponentFixture<ConsultaContabilizadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConsultaContabilizadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConsultaContabilizadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
