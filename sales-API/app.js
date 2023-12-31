import express from "express";
import { connectMongoDb } from "./src/config/db/MongoDbConfig.js";
import { createInitialData } from "./src/config/db/InitialData.js";
import SalesRoute from "./src/modules/sales/routes/SalesRoute.js";
import { connectRabbitMq } from "./src/rabbitmq/RabbitConfig.js";

const app = express();
const env = process.env;
const PORT = env.port || 8082;

connectMongoDb();
createInitialData();
connectRabbitMq();
app.use(SalesRoute);

app.get("/api/status", (req, res) => {
    return res.status(200).json({
        service: "sales-API",
        status: "up",
        httpStatus: 200,
})
})

app.listen(PORT, () => {
    console.info("Server started sucefully at port " + PORT)
});