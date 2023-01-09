import { Component, OnInit, Inject, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { settings } from 'src/environments/settings';
import { StorageService } from '../../service/storage.service';
import { SysBoticaConstant } from '../../constants/sysBoticaConstant';
import { ValidateService } from '../../service/validate.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AboutComponent } from '../about/about.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, AfterViewInit {

  public titleImg!: string;
  public title!: string;
  public userName!: string;
  constructor(
    protected router: Router,
    protected _storageServive: StorageService,
    protected cdRef: ChangeDetectorRef,
    protected _dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
  }

  ngAfterViewInit() {
    const user = this._storageServive.getLocalStorage(SysBoticaConstant.STORAGE_USER_NAME);
    this.userName = user != null ? ValidateService.firstLetterUpperCase(JSON.parse(user)) : user;
    this.cdRef.detectChanges();
  }

  public goToAbout() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    dialogConfig.panelClass = "custom-dialog";
    const dialogRef = this._dialog.open(AboutComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
    });
  }

  changePassword(): void {
    this.router.navigate(['intranet/change-password']);
  }

  goToPage(): void {
    this.router.navigate(['intranet/category']);
  }

  goToEntryProduct() {
    this.router.navigate(['intranet/entry-product']);
  }

  goToPageUnit(): void {
    this.router.navigate(['intranet/unit']);
  }

  goToPageMark(): void {
    this.router.navigate(['intranet/mark']);
  }

  goToPageProduct(): void {
    this.router.navigate(['intranet/product']);
  }

  singOut() {
    this._storageServive.cleanStorageAll();
    this.router.navigate(['sing-out']);
  }
  goToUser() {
    this.router.navigate(['intranet/user']);
  }

  goToUserSubsidiary() {
    this.router.navigate(['intranet/user-subsidiary']);
  }

  goToUserSecurityPolicy() {
    this.router.navigate(['intranet/security-policy']);
  }

  goToAuthority() {
    this.router.navigate(['intranet/authority']);
  }

  goToProvider() {
    this.router.navigate(['intranet/provider']);
  }

  goToUserAuthority() {
    this.router.navigate(['intranet/user-authority']);
  }

  goToWharehouse() {
    this.router.navigate(['intranet/wharehouse']);
  }

  goToOutSubWarehouse() {
    this.router.navigate(['intranet/out-sub-warehouse']);
  }

  goToSale() {
    this.router.navigate(['intranet/sale']);
  }

  goToCustomer() {
    this.router.navigate(['intranet/customer']);
  }

  goToSoldPRoduct() {
    this.router.navigate(['intranet/sold-product']);
  }

  goToReportSaleXDate() {
    this.router.navigate(['intranet/report-sale-date']);
  }

  goToWharehouseSubsidiary() {
    this.router.navigate(['intranet/wharehouse-subsidiary']);
  }

  goToHome(){
    this.router.navigate(['intranet/dashboard']);
  }

  goToProductAllWharehouse(){
    this.router.navigate(['intranet/product-wharehouse']);
  }

}
