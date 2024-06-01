import { Component, OnInit, inject } from '@angular/core';
import { CategoriesService } from 'src/app/services/categories/categories.service';

@Component({
  selector: 'category-product-variety-chart',
  styleUrls: ['./category-product-variety-chart.component.scss'],
  template: `
    <div class="bg-white rounded shadow-sm pt-3 px-3 pt-lg-5 px-lg-5">
        <h3 class="text-center m-0 fs-4">Variedade de produtos por categoria</h3>

        <p-chart type="doughnut" [responsive]="true" [data]="data" [options]="chartOptions"></p-chart>
    </div>
  `
})
export class CategoryProductVarietyChartComponent implements OnInit {
  categoriesService: CategoriesService = inject(CategoriesService);

  data!: any;

  chartOptions = {
    cutout: '60%',
    aspectRatio: 1.15,
    plugins: {
      colors: {
        enabled: false
      },
      legend: {
        position: "right",
        labels: {
          color: "#495057"
        }
      }
    }
  };

  constructor(){}

  ngOnInit(): void {
    this.getData()
  }

  getData(): void {
    this.categoriesService.getCategoriesProductVarietyCount().subscribe({
      next: (response: Array<{ categoryName: string, productsVariety: number }>) => {
        this.data = {
          labels: response.map(({ categoryName }) => categoryName),
          datasets: [
            {
              data: response.map(({ productsVariety }) => productsVariety),
              backgroundColor: ["darkBlue", "green", "brown", "yellow", "purple", "pink", "red"],
            }
          ]
        };
      }
    })
  }
}
