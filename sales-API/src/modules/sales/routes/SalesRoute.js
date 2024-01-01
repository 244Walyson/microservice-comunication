import { Router } from "express";
import CheckToken from "../../../config/auth/CheckToken.js";
import { sendMessageToProductUpdateQueue } from "../../../rabbitmq/productStock/ProductStockSender.js";
import OrderController from "../controller/OrderController.js";

const SalesRoute = new Router();


SalesRoute.post("/api/order/create", OrderController.createOrder);

SalesRoute.get("/api/sim", (req, res) => {
    sendMessageToProductUpdateQueue(
        [
        {
            "productId": 1,
            "quantity": 2
        },
        {
            "productId": 3,
            "quantity": 1
        },
        {
            "productId": 6,
            "quantity": 3
        }
    ]
);
    return res.status(200).json({
        "simmmmm": "simmmm"
    });
});

SalesRoute.use(CheckToken);


export default SalesRoute;