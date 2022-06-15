import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListWharehouseSubsidiaryComponent } from './list-wharehouse-subsidiary.component';

describe('ListWharehouseSubsidiaryComponent', () => {
  let component: ListWharehouseSubsidiaryComponent;
  let fixture: ComponentFixture<ListWharehouseSubsidiaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListWharehouseSubsidiaryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListWharehouseSubsidiaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
