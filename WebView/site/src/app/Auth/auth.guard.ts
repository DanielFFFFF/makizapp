import {CanActivateFn} from '@angular/router';

export const authGuard: CanActivateFn = () => {
  //window.location.href = "http://localhost:9001";
  return true;
};
