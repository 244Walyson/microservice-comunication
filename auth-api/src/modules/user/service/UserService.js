import UserRepository from "../repository/UserRepository";
import * as httpStatus from "../../../config/constants/HttpStatus.js"
import User from "../model/User.js";

class UserService {

    async findByEmail(email) {

        try{
            const { email } = req.params;
            this.validateData(email);
            let user = UserRepository.findByEmail(email);
            if(!user){

            }
            return {
                status: httpStatus.SUCCESS,
                user: {
                    id: user.id,
                    name: user.name,
                    email: user.email,
                },
            }
        }catch(err){
            return {
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: err.status,
            }
        }

        
    }

    validateData(email){
        if(!email) {
            throw new Error("User email was not informed")
        }
    }
}

export default new UserService();