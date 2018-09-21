import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminuserPageComponent } from './adminuser-page.component';

describe('AdminuserPageComponent', () => {
  let component: AdminuserPageComponent;
  let fixture: ComponentFixture<AdminuserPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminuserPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminuserPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
