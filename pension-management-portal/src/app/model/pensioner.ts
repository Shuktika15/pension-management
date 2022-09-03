import {BankDetails} from "./bank-details";

export interface Pensioner {
  aadharNumber: number,
  name: string,
  dateOfBirth: string,
  pan: string,
  salaryEarned: number,
  allowances: number,
  pensionType: string,
  bankDetails: BankDetails
}
