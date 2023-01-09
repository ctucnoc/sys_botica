import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListProductImgComponent } from './list-product-img.component';

describe('ListProductImgComponent', () => {
  let component: ListProductImgComponent;
  let fixture: ComponentFixture<ListProductImgComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListProductImgComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListProductImgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
