import { CategoryModel } from "./category.model"

export interface ProductModel {
  id: string
  brand: string
  name: string
  sku: string
  description: string
  price: number
  quantity: number
  image: string
  link: string
  category: CategoryModel
  active: boolean
}
