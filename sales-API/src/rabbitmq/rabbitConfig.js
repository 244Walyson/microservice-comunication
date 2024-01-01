import amqp from "amqplib/callback_api.js";
import {
    PRODUCT_TOPIC,
    PRODUCT_STOCK_UPDATE_QUEUE,
    PRODUCT_STOCK_UPDATE_ROUTING_KEY,
    SALES_CONFIRMATION_QUEUE,
    SALES_CONFIRMATION_ROUTING_KEY
} from "./queue.js";
import { RABBIT_MQ_URL } from "../config/constants/Secrets.js";
import { listenTosalesCOnfirmationQueue } from "./sales/SalesConfirmationQueue.js";

const HALF_SECOND = 500;
const HALF_MINUTES = 30000;
const CONTAINER_ENV = "container";

export async function connectRabbitMq() {
    const env = process.env.NODE_ENV;
    if(CONTAINER_ENV == env){
        console.info("Waiting for RabbitMq to start")
        setTimeout(()=>{
            connectRabbitMqAndCreateQueue();
        }, HALF_MINUTES);
    }else{
       await connectRabbitMqAndCreateQueue();
    }
}
async function connectRabbitMqAndCreateQueue() {
    amqp.connect(RABBIT_MQ_URL, (error, connection) => {
        if (error) {
            throw error;
        }

        createQueue(connection, PRODUCT_STOCK_UPDATE_QUEUE, PRODUCT_STOCK_UPDATE_ROUTING_KEY, PRODUCT_TOPIC);
        createQueue(connection, SALES_CONFIRMATION_QUEUE, SALES_CONFIRMATION_ROUTING_KEY, PRODUCT_TOPIC);

        setTimeout(() => {
            connection.close();
        }, HALF_SECOND);
    });
    listenTosalesCOnfirmationQueue();
}

function createQueue(connection, queue, routingKey, topic) {
    connection.createChannel((error, chanel) => {
        if (error) {
            throw error;
        }
        chanel.assertExchange(topic, "topic", { durable: true });
        chanel.assertQueue(queue, { durable: true });
        chanel.bindQueue(queue, topic, routingKey);
    });
}
