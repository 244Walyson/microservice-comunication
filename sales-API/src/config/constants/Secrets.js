const env = process.env;

export const API_SECRET = env.API_SECRET ? env.API_SECRET : "ZXNzYWVtaW5oYWNoYXZlZGVhcGljb21lbmNvZGU2NA==";
export const MONGO_DB_URL = env.MONGO_DB_URL ? env.MONGO_DB_URL : "mongodb://localhost:27017"
export const RABBIT_MQ_URL = env.RABBIT_MQ_URL ? RABBIT_MQ_URL : "amqp://localhost:5672"
export const PRODUCT_API_URL = env.PRODUCT_API_URL ? env.PRODUCT_API_URL : "http://localhost:8081/products";
