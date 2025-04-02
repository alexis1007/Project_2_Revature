export interface Loan {
  id?: number;
  principalBalance: number;
  interest: number;
  termLength: number;
  totalBalance: number;
  applicationStatus: { id: number; status: string };
  loanType: { id: number; loanType: string };
  userProfile?: { id: number; firstName: string; lastName: string };
}
