import amqp, { connect } from "amqplib/callback_api.js"
import { RABBIT_MQ_URL } from "../../config/constants/Secrets.js";
import { SALES_CONFIRMATION_QUEUE } from "../queue.js"
import OrderService from "../../modules/sales/services/OrderService.js";

export function listenTosalesCOnfirmationQueue(){
    amqp.connect(RABBIT_MQ_URL, (error, connection) => {
        if(error){
            throw error;
        }
        console.info("Listen sales confirmarion queue")
        connection.createChannel((error, channel) => {
            if(error){
                console.info("error creating channel: " + error.message)
                throw error;
            }
            channel.consume(SALES_CONFIRMATION_QUEUE, (message) => {
                console.info("confirmation sale");
                OrderService.updateOrder(message);
            });
        }, { noAck: true });
    });
}
