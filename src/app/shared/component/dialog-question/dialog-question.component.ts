import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogQuestion } from '../../model/dialogQuestion';

@Component({
  selector: 'app-dialog-question',
  templateUrl: './dialog-question.component.html',
  styleUrls: ['./dialog-question.component.scss']
})
export class DialogQuestionComponent implements OnInit {

  constructor(
    public dialogo: MatDialogRef<DialogQuestionComponent>,
    @Inject(MAT_DIALOG_DATA) public question: DialogQuestion
  ) { }

  close(): void {
    this.dialogo.close(false);
  }
  confirmation(): void {
    this.dialogo.close(true);
  }

  ngOnInit(): void {
  }

}
