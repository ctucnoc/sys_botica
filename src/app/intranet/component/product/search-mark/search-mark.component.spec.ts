import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchMarkComponent } from './search-mark.component';

describe('SearchMarkComponent', () => {
  let component: SearchMarkComponent;
  let fixture: ComponentFixture<SearchMarkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchMarkComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchMarkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
