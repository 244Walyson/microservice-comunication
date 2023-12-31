import Order from "../../src/modules/sales/model/Order.js";

export async function createInitialData(){
    await Order.collection.drop();
    await Order.create({
        user: {
            id: "1",
            name: "test",
            email: "test@gmail.com",
        },
        products: [
            {
                productId: 1,
                quantity: 2,
            },
            {
                productId: 2,
                quantity: 2,
            },
            {
                productId: 3,
                quantity: 2,
            } 
        ],
        status: "APPROVED",
        createdAt: new Date(),
        updatedAt: new Date(),
    });
    await Order.create({
        user: {
            id: "2",
            name: "test",
            email: "test@gmail.com",
        },
        products: [
            {
                productId: 1,
                quantity: 3,
            },
            {
                productId: 3,
                quantity: 2,
            },
            {
                productId: 6,
                quantity: 2,
            } 
        ],
        status: "REJECTED",
        createdAt: new Date(),
        updatedAt: new Date(),
    });
};