import axios from "axios";
import { PRODUCT_API_URL } from "../../config/constants/Secrets.js";

class ProductClient{
    async checkProductStock(products, token){
        try {
            const headers =  {
                Authorization: `Bearer ${token}`
            };
            console.info(`sending request to product with data: ${JSON.stringify(products)}`);
            axios
            .post(`${PRODUCT_API_URL}/check-stock`,  { headers }, products)
            .then((res) => {
                return true;
            }).catch((err) => {
                console.error(err.data.message)
                return false;
            });
        } catch (error) {
            return false;
        }
    }
}

export default new ProductClient();