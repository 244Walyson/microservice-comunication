import OrderService from "../services/OrderService.js";

class OrderController {
    async createOrder(req, res){
        let order = await OrderService.createOrder(req);
        return res.status(order.status).json(order.body);
    }
}

export default new OrderController();