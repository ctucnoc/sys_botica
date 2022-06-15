import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WharehouseSubsidiaryComponent } from './wharehouse-subsidiary.component';

describe('WharehouseSubsidiaryComponent', () => {
  let component: WharehouseSubsidiaryComponent;
  let fixture: ComponentFixture<WharehouseSubsidiaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WharehouseSubsidiaryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WharehouseSubsidiaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
