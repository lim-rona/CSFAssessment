// Implement the methods in PizzaService for Task 3
// Add appropriate parameter and return type 

import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { Order, OrderSummary } from "./models";

const URL = 'https://localhost:8080/api/order'

@Injectable()
export class PizzaService {

  constructor(private http: HttpClient) { }

  // POST /api/order
  // Add any required parameters or return type
  createOrder(order: Order): Promise<String>{
    const headers = new HttpHeaders()
        .set('Content-Type','application/json')
        .set('Accept','application/json')

        return lastValueFrom(
        this.http.post<String>('http://localhost:8080/api/order', order,{headers}).pipe()
        )
}

  // GET /api/order/<email>/all
  // Add any required parameters or return type
  getOrders(email: String): Promise<OrderSummary[]>{
    const headers = new HttpHeaders()
        .set('Content-Type','application/json')
        .set('Accept','application/json')
    var email1 = email.replace('@','%40');
    var url = `http://localhost:8080/api/order/${email1}/all`

        return lastValueFrom(
        this.http.get<OrderSummary[]>(url,{headers}).pipe()
        )
}

}
