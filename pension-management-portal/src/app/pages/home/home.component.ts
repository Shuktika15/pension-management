import {Component, OnInit} from '@angular/core';
import {AuthenticateService} from "../../service/authenticate/authenticate.service";
import {Pensioner} from "../../model/pensioner";
import {ProcessPensionResponse} from "../../model/process-pension-response";
import {PensionService} from "../../service/pension/pension.service";
import {ProcessPensionRequest} from "../../model/process-pension-request";
import {LoadingService} from "../../service/loading/loading.service";
import {animate, state, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  animations: [
    trigger('processPension', [
      state('loading', style({
        filter: "blur(1px)",
        cursor: "not-allowed"
      })),
      state('complete', style({
        opacity: 0,
        display: 'none',
        cursor: "not-allowed"
      })),
      transition(
        "initial => loading",
        animate("1s ease-in-out")
      ),
      transition(
        "loading => complete",
        animate(1000)
      )
    ]),
    trigger('pensionLoader', [
      state('loading', style({
        width: 'min-content',
        opacity: 1
      }))
    ]),
    trigger('pensionDetails', [
      state('complete', style({
        bottom: "0"
      })),
      transition(
        "* => complete",
        animate("1.5s ease-in-out")
      ),
    ])
  ]
})
export class HomeComponent implements OnInit {
  pensioner?: Pensioner;
  processPensionResponse?: ProcessPensionResponse;
  isLoading: boolean = false;
  loadingPension: boolean = false;
  processPensionBtnState: string = "initial";

  constructor(
    private authenticationService: AuthenticateService,
    private pensionService: PensionService,
    private loadingService: LoadingService
  ) {
  }

  get allowance(): number {
    return this.pensioner!.allowances;
  }

  get aadharNumber(): number {
    return this.pensioner!.aadharNumber;
  }

  get panNumber(): string {
    return this.pensioner!.pan;
  }

  get name(): string {
    return this.pensioner!.name;
  }

  get dateOfBirth(): number {
    return Date.parse(this.pensioner!.dateOfBirth);
  }

  aadharNumbers(): string[] {
    const aadharNumber: string = this.aadharNumber.toString();
    return [
      aadharNumber.substring(0, 4),
      aadharNumber.substring(4, 8),
      aadharNumber.substring(8, 12)
    ];
  }

  get bankName(): string {
    return this.pensioner!.bankDetails.bankName;
  }

  get accountNumber(): number {
    return this.pensioner!.bankDetails.accountNumber
  }

  get bankType(): string {
    return this.pensioner!.bankDetails.bankType;
  }

  get salaryEarned(): number {
    return this.pensioner!.salaryEarned;
  }

  ngOnInit(): void {
    this.pensioner = this.authenticationService.pensioner;
    this.loadingService.isLoading.subscribe({
      next: value => this.isLoading = value
    });
  }

  get pensionType(): string {
    return this.pensioner!.pensionType;
  }

  convertToTitleCase(str: String): string {
    return str[0].toUpperCase() + str.substring(1);
  }

  processPension() {
    if (this.processPensionBtnState === 'initial') {
      this.loadingPension = true;
      this.processPensionBtnState = "loading";

      setTimeout(() => {
        const processPensionRequest: ProcessPensionRequest = {
          aadharNumber: this.pensioner?.aadharNumber!
        }
        this.pensionService.processPension(processPensionRequest)
          .subscribe({
            next: value => this.processPensionResponse = value,
            error: (err) => (this.authenticationService.logout()),
            complete: () => {
              this.processPensionBtnState = "complete";
              this.loadingPension = false;
            }
          });
      }, 3000);
    }
  }

  logout() {
    this.authenticationService.logout();
  }
}
