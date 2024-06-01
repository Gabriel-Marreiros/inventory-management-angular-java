import { RoleModel } from "./role.model"

export interface UserModel {
  id: string
  name: string
  email: string
  password: string
  phoneNumber: string
  dateBirth?: Date | string
  role: RoleModel | string
  active: boolean
}
