import { Router } from "express";
import CheckToken from "../../../config/auth/CheckToken.js";
import { sendMessageToProductUpdateQueue } from "../../../rabbitmq/productStock/ProductStockSender.js";
import OrderController from "../controller/OrderController.js";

const SalesRoute = new Router();


SalesRoute.use(CheckToken);;

SalesRoute.post("/api/order/create", OrderController.createOrder);

SalesRoute.post("/api/sim", (req, res) => {
    let order = OrderController.createOrder(req, res);
    return res.status(200).json(order.body);
});

SalesRoute.use(CheckToken);


export default SalesRoute;