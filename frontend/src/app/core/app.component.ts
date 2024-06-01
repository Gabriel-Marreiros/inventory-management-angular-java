import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  template: `
    <main>
      <app-header *ngIf="showHeaderAndFooter"/>

      <router-outlet />

      <app-footer *ngIf="showHeaderAndFooter"/>
    </main>
  `
})
export class AppComponent implements OnInit {
  router: Router = inject(Router);

  showHeaderAndFooter: boolean = false;

  constructor(){}

  ngOnInit(): void {
    this.router.events.subscribe(() => {
      this.showHeaderAndFooter = this.router.url != "/login"
    })
  }


}
