import bcrypt from "bcrypt";
import User from "../../modules/user/model/User.js";

export const CreateInitialData = async () => {
    try {
        let password = await bcrypt.hash("123456", 10); 

        await User.create({
            name: "user test",
            email: "teste@gmail.com",
            password: password,
        });
        await User.create({
            name: "user test 2",
            email: "teste2@gmail.com",
            password: password,
        });

        console.log("Initial data created successfully");
    } catch (error) {
        console.error("Error creating initial data:", error.message);
    }
};
