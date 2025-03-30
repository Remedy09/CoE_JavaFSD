import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {
  @Input('appHighlight') highlightColor: string = 'yellow';

  constructor(private el: ElementRef) {}

  @HostListener('mouseenter') onMouseEnter() {
    this.applyHighlight(this.highlightColor);
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.applyHighlight(null);
  }

  private applyHighlight(color: string | null): void {
    this.el.nativeElement.style.backgroundColor = color;
  }
}
