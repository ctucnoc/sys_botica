import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './component/header/header.component';
import { FooterComponent } from './component/footer/footer.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatRippleModule } from '@angular/material/core';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatMenuModule } from '@angular/material/menu';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatRadioModule } from '@angular/material/radio';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatGridListModule } from '@angular/material/grid-list';
import { IconModule } from './icons/icon.module';
import { MatDividerModule } from '@angular/material/divider';
import { AvatarComponent } from './component/avatar/avatar.component';
import { DialogConfirmationComponent } from './component/dialog-confirmation/dialog-confirmation.component';
import { LoaderSpinnerComponent } from './component/loader-spinner/loader-spinner.component';
import { DialogQuestionComponent } from './component/dialog-question/dialog-question.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatNativeDateModule } from '@angular/material/core';
import { BarcodeScannerLivestreamModule } from "ngx-barcode-scanner";
import { FirstLetterUpperCasePipe } from './pipe/firstLetterUpperCase.pipe';
import { MatListModule } from '@angular/material/list';


const MATERIAL_MODULE = [
  MatToolbarModule,
  MatButtonModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatSelectModule,
  MatCheckboxModule,
  MatDialogModule,
  MatTooltipModule,
  MatTableModule,
  MatCardModule,
  MatRippleModule,
  MatProgressBarModule,
  MatPaginatorModule,
  MatSortModule,
  MatMenuModule,
  MatDatepickerModule,
  MatRadioModule,
  MatAutocompleteModule,
  MatProgressSpinnerModule,
  MatGridListModule,
  MatDividerModule,
  MatNativeDateModule,
  MatSnackBarModule,
  MatListModule
];

const UTIL_MODULE = [
  BarcodeScannerLivestreamModule,
  FlexLayoutModule
];


const COMPONENTS = [
  HeaderComponent,
  FooterComponent,
  AvatarComponent,
  DialogConfirmationComponent,
  LoaderSpinnerComponent,
  DialogQuestionComponent
];

@NgModule({
  declarations: [
    ...COMPONENTS,
    FirstLetterUpperCasePipe,
  ],
  imports: [
    CommonModule,
    ...MATERIAL_MODULE,
    ...UTIL_MODULE,
    IconModule,
  ],
  exports: [
    ...MATERIAL_MODULE,
    ...UTIL_MODULE,
    ...COMPONENTS,
    FirstLetterUpperCasePipe,
  ]
})
export class SharedModule { }
