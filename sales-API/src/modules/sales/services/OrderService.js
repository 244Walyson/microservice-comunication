import OrderRepository from "../repository/Repository.js"
import { sendMessageToProductUpdateQueue } from "../../../rabbitmq/productStock/ProductStockSender.js";
import { INTERNAL_SERVER_ERROR, SUCCESS } from "../../../config/constants/HttpStatus.js";
import { APPROVED, PENDING, REJECTED } from "../status/OrderStatus.js";
import OrderException from "../exception/OrderException.js";
import { BAD_REQUEST } from "../../../config/constants/HttpStatus.js";
import ProductClient from "../../clients/ProductClient.js";
class OrderService {

    async createOrder(req){
        try{
            let orderData = req.body;
            this.validateOrderData(orderData.products);
            const { authUser } = req;
            const { authorization } = req.header;
            let order = this.createInitialOrderData(orderData, authUser);
            await this.validateProductStock(order, authorization);
            let createOrder = await OrderRepository.save(order);
            this.sendMessage(createOrder);
            return {
                status: SUCCESS,
                createOrder
            }
        }catch(err){
            return {
                status: err.status ? err.status : INTERNAL_SERVER_ERROR,
                message: err.message
            }
        }
    }
    
    async updateOrder(orderMessage){
        try{
            const order = JSON.parse(orderMessage);
            if(order.salesId && order.status){
                let existingOrder = await OrderRepository.findById(order.salesId);
                if(order.status !== existingOrder.status){
                    existingOrder.status = order.status;
                    existingOrder.updatedAt = new Date();
                    await OrderRepository.save(existingOrder);
                }
            }else{
                console.warn("The order was not complete");
            }
        }catch(err){
            console.error("could not parse order message from queue");
            console.error(err.message);
        }
    }

    validateOrderData(data){
        if(!data || data.products){
            throw new OrderException(
                BAD_REQUEST,
                "The products must be informed"
            );
        }
    }

    async validateProductStock(order, token){
        let stockIsOut = await ProductClient.checkProductStock(order.products, token);
            if(stockIsOut){
                throw new OrderException(
                    BAD_REQUEST,
                    "The stock is out for the product"
                );
            }
    }

    createInitialOrderData(orderData, authUser){
        return {
            user: authUser.user,
            status: PENDING,
            createdAt: new Date(),
            updatedAt: new Date(),
            products: orderData
        }
    }

    sendMessage(createOrder){
        const message = {
            salesId: createOrder.id,
            products: createOrder.products
        }
        sendMessageToProductUpdateQueue(message);
    }
}

export default new OrderService();