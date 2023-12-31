import express from "express";
import { connect } from "./src/db/MongoDbConfig.js";
import { createInitialData } from "./src/db/InitialData.js";

const app = express();
const env = process.env;
const PORT = env.port || 8082;

connect();

createInitialData();

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