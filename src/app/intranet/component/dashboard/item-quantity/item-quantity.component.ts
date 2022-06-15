import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-item-quantity',
  templateUrl: './item-quantity.component.html',
  styleUrls: ['./item-quantity.component.scss']
})
export class ItemQuantityComponent implements OnInit {

  @Input() quantityProduct!: any;
  @Input() description!:any;
  constructor() { }

  ngOnInit(): void {
  }

}
