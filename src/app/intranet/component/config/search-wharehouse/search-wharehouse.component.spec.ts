import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchWharehouseComponent } from './search-wharehouse.component';

describe('SearchWharehouseComponent', () => {
  let component: SearchWharehouseComponent;
  let fixture: ComponentFixture<SearchWharehouseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchWharehouseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchWharehouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
