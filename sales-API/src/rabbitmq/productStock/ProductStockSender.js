import amqp from "amqplib/callback_api.js";

import { RABBIT_MQ_URL } from "../../config/constants/Secrets.js";
import { PRODUCT_STOCK_UPDATE_ROUTING_KEY, PRODUCT_TOPIC } from "../queue.js";

export function sendMessageToProductUpdateQueue(message){
    amqp.connect(RABBIT_MQ_URL, (error, connection) => {
        if(error){
            throw error;
        }
        let messageToSend = JSON.stringify(message);
        console.info("sending message: " + messageToSend)
        connection.createChannel((error, channel) => {
            if(error){
                throw error;
            }
            channel.publish(PRODUCT_TOPIC, PRODUCT_STOCK_UPDATE_ROUTING_KEY, Buffer.from(messageToSend));
        }, { noAck: true });
        console.info("succes on send message")
    });
}
