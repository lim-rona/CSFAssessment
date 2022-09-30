import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Order } from '../models';
import { PizzaService } from '../pizza.service';


const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})

export class MainComponent implements OnInit {

  pizzaSize = SIZES[0]

  orderForm!:FormGroup
  toppingsArray: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

  constructor(private fb: FormBuilder, private router: Router, private pizzaSvc: PizzaService) {}

  ngOnInit(): void {
    this.orderForm=this.createOrderForm()
    
  }

  private createOrderForm():FormGroup{
    return this.fb.group({
      name: this.fb.control<string>('',[Validators.required]),
      email: this.fb.control<string>('',[Validators.required,Validators.email]),
      size: this.fb.control<string>('',[Validators.required]),
      base: this.fb.control<string>('',[Validators.required]),
      sauce: this.fb.control<string>('',[Validators.required]),
      toppings: this.fb.control<string[]>([],[Validators.required]),
      comments: this.fb.control<string>(''),
    })
  }

  processOrder(){
    const order: Order = this.orderForm.value as Order
    order.toppings = this.toppingsArray
    console.info("order is: ", order)
    console.info("toppings: ", order.toppings)

    this.pizzaSvc.createOrder(order);

    //Assuming order is created succesfully, transition to View 1
    this.router.navigate([`/orders/${order.email}`])
  }
  
  listOrders(email: string){
    console.info("email: ", email)
    this.router.navigate([`/orders/${email}`])
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  checked(e:any){
    if(e.target.checked){
      this.toppingsArray.push(e.target.value)
    } else{
      var i = this.toppingsArray.indexOf(e.target.value)
      this.toppingsArray.splice(i,1)
    }
    console.info("Toppings now: ",this.toppingsArray)
  }

}
