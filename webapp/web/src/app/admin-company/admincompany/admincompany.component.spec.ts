import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdmincompanyComponent } from './admincompany.component';

describe('AdmincompanyComponent', () => {
  let component: AdmincompanyComponent;
  let fixture: ComponentFixture<AdmincompanyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdmincompanyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdmincompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
