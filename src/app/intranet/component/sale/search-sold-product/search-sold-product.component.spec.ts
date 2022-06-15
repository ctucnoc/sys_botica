import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchSoldProductComponent } from './search-sold-product.component';

describe('SearchSoldProductComponent', () => {
  let component: SearchSoldProductComponent;
  let fixture: ComponentFixture<SearchSoldProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchSoldProductComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchSoldProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
