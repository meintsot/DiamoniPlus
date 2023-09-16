import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-star-rating',
  templateUrl: './star-rating.component.html',
  styleUrls: ['./star-rating.component.css']
})
export class StarRatingComponent implements OnInit {
  @Input() rating: number = 0;
  stars: number[] = [];

  constructor() { }

  ngOnInit(): void {
    this.calculateStars();
  }

  ngOnChanges(): void {
    this.calculateStars();
  }

  calculateStars(): void {
    this.stars = [];
    let remainingRating = this.rating;

    for (let i = 0; i < 5; i++) {
      if (remainingRating >= 1) {
        this.stars.push(1);
      } else if (remainingRating > 0) {
        this.stars.push(remainingRating);
      } else {
        this.stars.push(0);
      }
      remainingRating -= 1;
    }
  }

}
