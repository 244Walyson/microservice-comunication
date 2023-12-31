import { Router } from "express";
import CheckToken from "../../../config/auth/CheckToken.js";

const SalesRoute = new Router();

SalesRoute.use(CheckToken);

SalesRoute.get("/api/sim", (req, res) => {
    return res.status(200).json({
        "simmmmm": "simmmm"
    });
});

export default SalesRoute;