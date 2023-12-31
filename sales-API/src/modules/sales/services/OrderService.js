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
            const { transactionid, serviceid } = req.headers;
            console.info(`Request to POST order with data ${JSON.stringify(req.body)} | [transactionID: ${transactionid} | serviceID: ${serviceid}]`)
           
            let orderData = req.body;
            const { authUser } = req;
            const { authorization } = req.headers;

            this.validateOrderData(orderData);

            let order = this.createInitialOrderData(orderData, authUser, transactionid, serviceid);

            await this.validateProductStock(order, authorization, transactionid);

            let createOrder = await OrderRepository.save(order);

            await this.sendMessage(createOrder, transactionid);

            let response = {
                status: SUCCESS,
                createOrder
            }
            console.info(`Request to POST order with data ${JSON.stringify(response)} | [transactionID: ${transactionid} | serviceID: ${serviceid}]`)

            return response;
        }catch(err){
            return {
                status: err.status ? err.status : INTERNAL_SERVER_ERROR,
                message: err.message
            }
        }
    }
    
    async updateOrder(orderMessage){
        try{
            const order = JSON.parse(orderMessage.content);
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
            console.error(err);
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

    async validateProductStock(order, token, transactionid){
        let stockIsOut = await ProductClient.checkProductStock(order.products, token, transactionid);
            if(stockIsOut){
                throw new OrderException(
                    BAD_REQUEST,
                    "The stock is out for the product"
                );
            }
    }

    createInitialOrderData(orderData, authUser, transactionid, serviceid){
        return {
            user: authUser.name,
            status: PENDING,
            createdAt: new Date(),
            updatedAt: new Date(),
            products: orderData,
            transactionid: transactionid,
            serviceid: serviceid
        }
    }

    sendMessage(createOrder, transactionid){
        const message = {
            salesId: createOrder.id,
            products: createOrder.products,
            transactionid
        }
        sendMessageToProductUpdateQueue(message);
    }

    async findById(req){
        const { id } = req.params;
        this.validateInformedId(id);
        const order = await OrderRepository.findById(id);
        try{
            return {
                status: SUCCESS,
                order
            }
        }catch(err){
            return{
                status: err.status ? err.status : BAD_REQUEST
            }
        }
    }

    async findByProductId(req){
        const { productId } = req.params;
        this.validateInformedId(productId);
        const orders = await OrderRepository.findByProductId(productId);
        try{
            return {
                status: SUCCESS,
                salesId: orders.map((order) => {
                    return order.id
                })
            }
        }catch(err){
            return{
                status: err.status ? err.status : BAD_REQUEST
            }
        }
    }

    async findAll(){
        const orders = await OrderRepository.findAll();
        try{
            return {
                status: SUCCESS,
                orders
            }
        }catch(err){
            return {
                status: err.status ? err.status : BAD_REQUEST
            }
        }
    }

    validateInformedId(id){
        if(!id){
            return new OrderException(
                BAD_REQUEST,
                "The order id must be informed"
            );
        }
    }
}

export default new OrderService();