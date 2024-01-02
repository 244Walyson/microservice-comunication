import axios from "axios";
import { PRODUCT_API_URL } from "../../config/constants/Secrets.js";

class ProductClient{
    async checkProductStock(products, token, transactionid){
        try {
            const headers =  {
                Authorization: `${token}`,
                transactionid
            };
            console.info(token);
            console.info(products);
            console.info(`sending request to product with data: ${JSON.stringify(products)}`);
            axios
            .post(`${PRODUCT_API_URL}/check-stock`, products, { headers: headers })
            .then((res) => {
                console.info("trueeee")
                return true;
            }).catch((err) => {
                console.info("false")
                return false;
            });
        } catch (error) {
            return false;
        }
    }
}

export default new ProductClient();