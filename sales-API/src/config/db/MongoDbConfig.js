import mongoose from "mongoose";

import { MONGO_DB_URL } from "../constants/Secrets.js";

export function connectMongoDb(){
    mongoose.connect(MONGO_DB_URL, {
        useNewUrlParser: true,
    });
    mongoose.connection.on("connected", function(){
        console.info("The application connected to mongoDB successfully");
    })
    mongoose.connection.on("error", function(){
        console.info("Connection error")
    });
}