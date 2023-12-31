import mongoose from "mongoose";

const Schema = mongoose.Schema;
const model = mongoose.model;

const OrderSchema = new Schema({
    user: {
        type: Object,
        required: true,
    },
    products: {
        type: Array,
        required: true,
    },
    status: {
        type: String,
        required: true,
    },
    createdAt: {
        type: Date,
        required: true,
    },
    updatedAt: {
        type: Date,
        required: true,
    }
});

export default model("Order", OrderSchema);