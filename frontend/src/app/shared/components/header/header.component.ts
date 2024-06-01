import { CommonModule } from '@angular/common';
import { Component, ViewEncapsulation, inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { IconDefinition, faBars, faRightFromBracket as logoutIcon } from '@fortawesome/free-solid-svg-icons';
import { faLinkedin as linkedinIcon, faGithubSquare as githubIcon } from '@fortawesome/free-brands-svg-icons'
import { ButtonModule } from 'primeng/button';
import { SidebarModule } from 'primeng/sidebar';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    CommonModule,
    ButtonModule,
    SidebarModule,
    RouterModule,
    FontAwesomeModule
  ],
  styleUrls: ['./header.component.scss'],

  template: `
    <header class="position-sticky top-0 z-3 bg-teste py-2 px-4">
      <div class="row align-items-center justify-content-between">
        <div class="w-auto m-md-auto">
          <a [routerLink]="['home']">
            <h2 class="text-center mb-1 fs-5 text-white">Inventory Management</h2>
            <h3 class="text-center fs-6 text-white">Angular & Java</h3>
          </a>
        </div>

        <div class="w-auto">
          <p-button styleClass="text-white" [outlined]="true" (click)="showSideBar = true">
            <fa-icon [icon]="barIcon"></fa-icon>
          </p-button>
        </div>
      </div>

    </header>

    <p-sidebar [(visible)]="showSideBar" position="right" [blockScroll]="true">
        <ng-template pTemplate="header" class="align-items-center justify-content-between">
          <h3 class="fs-2">Menu</h3>
        </ng-template>

        <ng-template pTemplate="content">
          <nav class="h-100 fs-4 d-flex flex-column gap-3 pt-5">
            <div class="d-inline-block nav-item" [routerLinkActive]="['link-active', 'fw-bold']">
              <a [routerLink]="['home']" (click)="showSideBar = false" >Home</a>
              <i class="nav-item-underline bg-teste"></i>
            </div>

            <div class="d-inline-block nav-item" [routerLinkActive]="['link-active', 'fw-bold']">
              <a [routerLink]="['products']" (click)="showSideBar = false">Inventário</a>
              <i class="nav-item-underline bg-teste"></i>
            </div>

            <div class="d-inline-block nav-item" [routerLinkActive]="['link-active', 'fw-bold']">
              <a [routerLink]="['reports']" (click)="showSideBar = false">Gráficos</a>
              <i class="nav-item-underline bg-teste"></i>
            </div>

            <div class="d-inline-block nav-item" [routerLinkActive]="['link-active', 'fw-bold']">
              <a [routerLink]="['admin']" (click)="showSideBar = false">Painel Administrativo</a>
              <i class="nav-item-underline bg-teste"></i>
            </div>


            <div class="d-inline-block nav-item">
              <a href="https://www.github.com/Gabriel-Marreiros" target="_blank">
                <fa-icon [icon]="githubIcon" />
                GitHub
              </a>
              <i class="nav-item-underline bg-teste"></i>
            </div>

            <div class="d-inline-block nav-item">
              <a href="https://www.linkedin.com/in/gabrielsousamarreiros" target="_blank">
                <fa-icon [icon]="linkedinIcon" />
                LinkedIn
              </a>
              <i class="nav-item-underline bg-teste"></i>
            </div>

            <div class="d-inline-block nav-item mt-auto">
              <a class="cursor-pointer" (click)="logout()">
                <fa-icon [icon]="logoutIcon" />
                Logout
              </a>
              <i class="nav-item-underline bg-teste"></i>
            </div>
          </nav>
        </ng-template>


    </p-sidebar>
   `
})
export class HeaderComponent {
  authService: AuthenticationService = inject(AuthenticationService);

  showSideBar: boolean = false;
  barIcon: IconDefinition = faBars;
  githubIcon: IconDefinition = githubIcon;
  linkedinIcon: IconDefinition = linkedinIcon;
  logoutIcon: IconDefinition = logoutIcon;

  constructor() { }

  logout(): void {
    this.authService.logout();
    this.showSideBar = false;
  }
}
