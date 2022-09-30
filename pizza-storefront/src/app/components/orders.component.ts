import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Order, OrderSummary } from '../models';
import { PizzaService } from '../pizza.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  orderList: OrderSummary[] = []
  name!: String 
  constructor(private pizzaSvc: PizzaService, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    const email = this.activatedRoute.snapshot.params['email']
    this.pizzaSvc.getOrders(email)
      .then(result =>{
        this.orderList=result
      }).catch(error=>{
        console.error(error)
      })
      this.name = email
  }

}
