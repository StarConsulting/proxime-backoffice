import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-syntax-highlighter',
    templateUrl: './syntax-highlighter.component.html',
    styleUrls: ['./syntax-highlighter.component.scss']
})

export class SyntaxHighlighterComponent implements OnInit {

    //  Prism js for code formating
    ngOnInit() {
        // @ts-ignore
      $.getScript('./assets/js/prism.min.js');
    }
}
