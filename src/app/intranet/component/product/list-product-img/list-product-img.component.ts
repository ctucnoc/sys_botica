import { Component, OnInit, Inject, AfterViewInit, ViewChildren, QueryList, OnDestroy } from '@angular/core';
import { ProductImgService } from 'src/app/shared/service/api/productImg.service';
import { ProductImgDTO } from 'src/app/shared/model/response/productImgDTO';
import { HttpErrorResponse } from '@angular/common/http';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AlertService } from 'src/app/shared/service/aler.service';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { validExtend } from 'src/app/shared/utils/resize-image';
import { compressImage, b64toFile } from 'src/app/shared/utils/compressed-images';
import { FileService } from 'src/app/shared/service/api/file.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-list-product-img',
  templateUrl: './list-product-img.component.html',
  styleUrls: ['./list-product-img.component.scss']
})
export class ListProductImgComponent implements OnInit, AfterViewInit, OnDestroy {

  public selectedFile!: any;
  public isEnableContinueButton: boolean = false;
  @ViewChildren('fileInput') fileInput!: QueryList<any>;
  public productImgs: ProductImgDTO[] = [];

  protected subscriptions: Array<Subscription> = new Array();
  constructor(
    protected _ProductImgService: ProductImgService,
    protected _dialoRef: MatDialogRef<ListProductImgComponent>,
    protected _notificationService: NotificationService,
    protected _snackBar: MatSnackBar,
    protected _alertService: AlertService,
    protected _fileService: FileService,
    @Inject(MAT_DIALOG_DATA) public id: number,
  ) { }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.onFindByProduct(this.id);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => {
      subscription.unsubscribe();
    });
  }

  onUpload() {
    if (this.isEnableContinueButton) {
      this.subscriptions.push(
        this._fileService.save(this.selectedFile, this.id).subscribe(
          (data) => {
            this.onFindByProduct(this.id);
            this.clearFile();
          },
          (error: HttpErrorResponse) => {

          }
        )
      );
    }
  }

  onFindByProduct(idProduct: any) {
    this.subscriptions.push(
      this._ProductImgService.findByProduct(idProduct)
        .subscribe(
          (data) => {
            this.productImgs = data;
          },
          (error: HttpErrorResponse) => {

          }
        )
    );
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public clearFile() {
    this.isEnableContinueButton = false;
    this.fileInput.reset;
  }

  public onFileSelected($event: any): void {
    if ($event.target.files.length > 0) {
      const mbSize = $event.target.files[0].size / 1000 / 1000;
      if (mbSize > 5) {
        this.validateFileDialog(SysBoticaConstant.MESSAGE_UPLOAD_FILE_SIZE);
        this.isEnableContinueButton = false;
        return;
      }
      if (!validExtend($event.target.files[0].name)) {
        this.validateFileDialog(SysBoticaConstant.MESSAGE_UPLOAD_FILE_EXTEND);
        this.isEnableContinueButton = false;
        return;
      }

      const reader = new FileReader();
      reader.readAsArrayBuffer($event.target.files[0]);
      reader.onload = (event: any) => {
        const blob = new Blob([event.target.result]);
        window.URL = window.URL || window.webkitURL;
        const blobURL = window.URL.createObjectURL(blob);

        const image = new Image();
        image.src = blobURL;
        image.onload = () => {
          const compressedImage = compressImage(image);
          this.selectedFile = b64toFile(compressedImage, $event.target.files[0].name);
          this.isEnableContinueButton = true;
        };
      };
    }
  }

  validateFileDialog(message: string): void {
    this._alertService.questionDialog(message, '', true,
      false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }
}
