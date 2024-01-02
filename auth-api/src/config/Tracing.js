import { v4 as uuid4 } from "uuid";
import { BAD_REQUEST } from "../../../sales-API/src/config/constants/HttpStatus.js";

export default (req, res, next) => {
    let { transactionid } = req.headers;
    if(!transactionid){
        return res.status(BAD_REQUEST).json({
            status: BAD_REQUEST,
            message: "The transactionid header is required"
        });
    }
    console.info(transactionid)
    req.headers.serviceid = uuid4();
    return next();
}