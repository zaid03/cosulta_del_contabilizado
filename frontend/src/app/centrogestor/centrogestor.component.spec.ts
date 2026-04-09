import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CentrogestorComponent } from './centrogestor.component';

describe('CentrogestorComponent', () => {
  let component: CentrogestorComponent;
  let fixture: ComponentFixture<CentrogestorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CentrogestorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CentrogestorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
