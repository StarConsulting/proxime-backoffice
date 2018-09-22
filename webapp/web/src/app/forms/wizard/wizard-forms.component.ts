import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-wizard-forms',
    templateUrl: './wizard-forms.component.html',
    styleUrls: ['./wizard-forms.component.scss']
})

export class WizardFormsComponent implements OnInit {

    ngOnInit() {
      // @ts-ignore
      $.getScript('./assets/js/jquery.steps.min.js');
      // @ts-ignore
      $.getScript('./assets/js/wizard-steps.js');
    }
}
