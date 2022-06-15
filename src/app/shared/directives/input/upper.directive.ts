import { Directive, ElementRef, HostListener } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
  selector: '[appUpper]'
})
export class UpperDirective {

  constructor(
    //protected _elementRef: ElementRef
    private readonly control: NgControl
  ) { }

  @HostListener('input', ['$event.target'])
  public onInput(input: HTMLInputElement): void {
    const caretPos: any = input.selectionStart;
    this.control.control?.setValue(input.value.toUpperCase());
    input.setSelectionRange(caretPos, caretPos);
  }
  /*   @HostListener('input', ['$event']) onInput(event: any) {
      this._elementRef.nativeElement.value = event.target.value.toUpperCase();
    } */

}
