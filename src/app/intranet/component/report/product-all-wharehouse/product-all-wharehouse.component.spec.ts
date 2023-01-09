import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductAllWharehouseComponent } from './product-all-wharehouse.component';

describe('ProductAllWharehouseComponent', () => {
  let component: ProductAllWharehouseComponent;
  let fixture: ComponentFixture<ProductAllWharehouseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductAllWharehouseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductAllWharehouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
