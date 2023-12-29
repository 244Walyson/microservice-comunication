import express from "express";
import { CreateInitialData } from "./src/config/db/InitialData.js"
import userRoutes from "./src/modules/user/routes/UserRoute.js";
import CheckToken from "./src/config/auth/CheckToken.js";

CreateInitialData();

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;


app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: "auth-API",
        status: "up",
        httpStatus: 200,
    });
});

app.use(express.json());
app.use(userRoutes);


app.listen(PORT, () => {
    console.info("Server started successfully at port " + PORT);
});
