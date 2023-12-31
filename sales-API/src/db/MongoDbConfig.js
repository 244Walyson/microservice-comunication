import mongoose from "mongoose";

import { MONGO_DB_URL } from "../Secrets.js";

export function connect(){
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