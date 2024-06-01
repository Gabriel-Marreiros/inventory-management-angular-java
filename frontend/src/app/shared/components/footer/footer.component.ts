import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [
    CommonModule
  ],
  styleUrls: ['./footer.component.scss'],
  template: `
    <footer class="text-center bg-secondary text-white p-3">
      <span>Copyright &copy; <time>{{currentYear}}</time> Gabriel Marreiros - Todos os direitos reservados.</span>
    </footer>
  `
})
export class FooterComponent {

  currentYear: number = new Date().getFullYear();

  constructor(){}
}
