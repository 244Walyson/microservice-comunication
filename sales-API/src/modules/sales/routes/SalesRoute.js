import { Router } from "express";
import CheckToken from "../../../config/auth/CheckToken.js";
import { sendMessageToProductUpdateQueue } from "../../../rabbitmq/productStock/ProductStockSender.js";
import OrderController from "../controller/OrderController.js";

const SalesRoute = new Router();


SalesRoute.use(CheckToken);;

SalesRoute.post("/api/orders/create", OrderController.createOrder);
SalesRoute.get("/api/orders/:id", OrderController.findById)
SalesRoute.get("/api/orders", OrderController.findAll);
SalesRoute.get("/api/orders/products/:productId");


export default SalesRoute;