import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-avatar',
  templateUrl: './avatar.component.html',
  styleUrls: ['./avatar.component.scss']
})
export class AvatarComponent implements OnInit {

  @Input() userName!: string;


  public chartUser!: string;
  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: any) {
    if (changes.userName) {
      if (changes.userName && changes.userName.currentValue) {
        this.chartUser = changes.userName.currentValue.charAt(0);
      }
    }
  }

}
