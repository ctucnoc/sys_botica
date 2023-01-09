import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent implements OnInit {

  constructor(
    private _dialoRef: MatDialogRef<AboutComponent>,
  ) { }

  ngOnInit(): void {
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

}
