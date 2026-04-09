import { bootstrapApplication } from '@angular/platform-browser';
import { importProvidersFrom, LOCALE_ID } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import localeEs from '@angular/common/locales/es';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

registerLocaleData(localeEs, 'es-ES');

bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    ...(appConfig.providers || []),
    importProvidersFrom(),
    { provide: LOCALE_ID, useValue: 'es-ES' },
    provideHttpClient(
      withInterceptors([
        (req, next) => {
          const token = sessionStorage.getItem('JWT');
          const isPublicEndpoint =
            req.url.includes('/scap/api/cas/validate') ||
            req.url.includes('/scap/api/login/') ||
            req.url.includes('/scap/api/filter');

          if (token && !req.headers.has('Authorization') && !req.headers.has('Skip-Auth') && !isPublicEndpoint) {
            req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
          }
          return next(req);
        }
      ])
    )
  ]
}).catch((err) => console.error(err));