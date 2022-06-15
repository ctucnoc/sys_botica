import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { CoreModule } from './core/core.module';
import { settings } from 'src/environments/settings';
import { AuthInterceptor } from './shared/interceptor/auth.interceptor';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { FORMATS } from 'src/app/shared/constants/sysBoticaConstant';
import { NgChartsModule } from 'ng2-charts';
import { NgxMaskModule, IConfig } from 'ngx-mask'

const MASK_CONFIG: Partial<IConfig> = {
  validation: false,
};

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    CoreModule,
    NgChartsModule,
    NgxMaskModule.forRoot(MASK_CONFIG)
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: 'settings',
      useValue: settings
    },
    {
      provide: MAT_DATE_FORMATS,
      useValue: FORMATS
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
