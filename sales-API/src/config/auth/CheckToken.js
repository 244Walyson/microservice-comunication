import jwt from "jsonwebtoken";
import { promisify } from "util";
import * as httpStatus from "../constants/HttpStatus.js"
import * as secrets from "../constants/Secrets.js"
import AuthException from "./AuthException.js";

const emptySpace = " ";

export default async (req, res, next) => {
    try {
        const { authorization } = req.headers;
        if (!authorization) {
            throw new AuthException(
                httpStatus.UNAUTHORIZED,
                "Access token was not informed"
            )
        }
        let accessToken = authorization;
        if(accessToken.includes(emptySpace)){
            accessToken = accessToken.split(emptySpace)[1];
        }
        const verifyAsync = promisify(jwt.verify);
        const decoded = await verifyAsync(accessToken, secrets.API_SECRET);
        req.authUser = decoded.authUser;
        return next();
    } catch (err) {
        let status = err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR;
        return res.status(status).json({
            status,
            message: err.message,
        });
    }

};

