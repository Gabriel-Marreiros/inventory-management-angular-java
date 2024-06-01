import { Component, OnInit, inject } from '@angular/core';
import { ProductsService } from 'src/app/services/products/products.service';

@Component({
  selector: 'active-brand-products-chart',
  styleUrls: ['./active-brand-products-chart.component.scss'],
  template: `
    <div class="p-3 p-lg-5 bg-white rounded shadow-sm">
        <h3 class="text-center mb-5">Produtos Ativos/Inativos por marca</h3>
        <p-chart type="bar" [responsive]="true" [data]="data" [options]="chartOptions"></p-chart>
    </div>
  `
})
export class ActiveBrandProductsChartComponent implements OnInit {
  productsService: ProductsService = inject(ProductsService);

  data!: any;

  chartOptions: any = {
    maintainAspectRatio: false,
    aspectRatio: 0.9,
    plugins: {
      legend: {
        labels: {
          color: "#495057"
        }
      }
    },
    scales: {
      x: {
        ticks: {
          color: "#6c757d",
          font: {
            weight: 500
          }
        },
        grid: {
          color: "#dfe7ef",
          drawBorder: false
        }
      },
      y: {
        ticks: {
          color: "#6c757d"
        },
        grid: {
          color: "#dfe7ef",
          drawBorder: false
        }
      }

    }
  }

  constructor(){}

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.productsService.getBrandsProductsStatusReport().subscribe((response) => {
      this.data = {
        labels: response.map(({ brand }) => brand),

        datasets: [
          {
            label: "Produtos Ativos",
            data: response.map(({ activeProducts }) => activeProducts),
            backgroundColor: "darkGreen",
          },
          {
            label: "Produtos Inativos",
            data: response.map(({ inactiveProducts }) => inactiveProducts),
            backgroundColor: "darkRed",
          }
        ]
      };
    })
  }
}
