import {Component} from '@angular/core';
import {ChildrenOutletContexts} from "@angular/router";
import {animate, query, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  animations: [
    trigger('routeAnimations', [
      transition('* => *', [
        query(':enter', [style({
          opacity: 0,
          position: 'absolute'
        })], {
          optional: true,
        }),
        query(
          ':leave',
          [
            style({opacity: 1}),
            animate('0.3s', style({
              opacity: 0
            })),
          ],
          {optional: true}
        ),
        query(
          ':enter',
          [
            style({opacity: 0}),
            animate('0.3s', style({
              opacity: 1
            })),
          ],
          {optional: true}
        ),
      ]),
    ])
  ]
})
export class AppComponent {
  constructor(private contexts: ChildrenOutletContexts) {
  }

  getRouteAnimationData() {
    return this.contexts.getContext('primary')?.route?.snapshot?.data?.['animation'];
  }
}
