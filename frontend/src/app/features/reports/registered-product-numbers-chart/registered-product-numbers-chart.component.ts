import { Component, OnInit, inject } from '@angular/core';
import { MenuItem, MenuItemCommandEvent } from 'primeng/api';
import { ProductsService } from 'src/app/services/products/products.service';

@Component({
  selector: 'registered-product-numbers-chart',
  styleUrls: ['./registered-product-numbers-chart.component.scss'],

  template: `
    <div class="p-3 p-lg-5 bg-white rounded shadow-sm">

      <div class="d-flex align-items-center justify-content-between mb-5">
        <h3 class="fs-4">Número de produtos cadastrados em cada mês</h3>

        <div class="fs-4">
          <span>Ano da consulta: </span>
          <p-splitButton [label]="yearFilter" [model]="this.yearFilterOptions" [menuStyle]="{'left': '180%'}" styleClass="p-button-text p-button-secondary p-0"></p-splitButton>
        </div>
      </div>

      <p-chart type="line" [responsive]="true" [data]="data" [options]="chartOptions"></p-chart>
    </div>
  `,
})
export class RegisteredProductNumbersChartComponent implements OnInit {
  productsService: ProductsService = inject(ProductsService);

  currentYear: string = new Date().getFullYear().toString();
  yearFilter: string = this.currentYear;
  yearFilterOptions: MenuItem[] = [];

  data!: any;

  chartOptions: any = {
    maintainAspectRatio: false,
    aspectRatio: 0.95,
    plugins: {
      legend: {
        labels: {
          color: "#495057"
        }
      }
    },
    elements: {
      point: {
        radius: 7,
        hoverRadius: 11,
      }
    },
    scales: {
      x: {
        ticks: {
          color: "#6c757d"
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
  };

  constructor(){}

  ngOnInit(): void {
    this.getData(this.currentYear);
    this.setYearOptions();
  }

  getData(yearFilter: string) {
    this.productsService.getProductsNumberRegisteredMonthReport(yearFilter).subscribe((response) => {
      this.data = {
        labels: response.map(({ month }) => `${month}/${this.yearFilter}`),
        datasets: [
          {
            label: 'Produtos Cadastrados',
            data: response.map(({ registeredProductsNumber }) => registeredProductsNumber),
            fill: false,
            borderColor: "darkBlue",
            tension: 0.4
          }
        ]
      };
    })

  }

  setYearOptions(yearFilterOption: number = 2021){
    if(yearFilterOption > Number(this.currentYear)) return;

    this.yearFilterOptions.push({
      label: yearFilterOption.toString(),
      command: ($event) => this.handleSelectYearFilter($event)
    })

    this.setYearOptions(yearFilterOption + 1);
  }

  handleSelectYearFilter($event: MenuItemCommandEvent): void{
    const selectedYearFilter: string = $event.item?.label!;

    this.yearFilter = selectedYearFilter;

    this.getData(selectedYearFilter);
  }
}
