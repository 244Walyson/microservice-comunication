import UserRepository from "../repository/UserRepository.js";
import * as httpStatus from "../../../config/constants/HttpStatus.js"
import UserException from "../exception/UserException.js";
import bcrypt from "bcrypt"
import jwt from "jsonwebtoken"
import * as secrets from "../../../config/constants/Secrets.js"

class UserService {

    async findByEmail(req) {

        try {
            const { email } = req.params;
            const authUser = req.authUser;
            console.info(authUser);
            this.validateData(email);
            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);
            this.validateAuthenticateedUser(user, authUser)
            let response = {
                status: httpStatus.SUCCESS,
                user: {
                    id: user.id,
                    name: user.name,
                    email: user.email,
                },
            }
            return response
        } catch (err) {
            return {
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: err.message,
            }
        }


    }

    validateAuthenticateedUser(user, authUser){
        if(!authUser || user.id !== authUser.id){
            throw new UserException(
                httpStatus.FORBIDDEN,
                "You cannot see this user data"
            )
        }
    }

    async getAccessToken(req) {
        try {
            const { transactionid, serviceid } = req.headers;
            console.info(`Request to POST order with data ${JSON.stringify(req.body)} | [transactionID: ${transactionid} | serviceID: ${serviceid}]`)

            const { email, password } = req.body;
            this.validateAccessTokenData(email, password);
            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user)
            await this.validatePassword(password, user.password)
            let authUser = { id: user.id, name: user.name, email: user.email };
            const accessToken = jwt.sign({ authUser }, secrets.API_SECRET, { expiresIn: '1d'})
            let response =  {
                status: httpStatus.SUCCESS,
                accessToken,
            }
            console.info(`Request to POST order with data ${JSON.stringify(response)} | [transactionID: ${transactionid} | serviceID: ${serviceid}]`)

            return response;
        } catch (err) {
            return {
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: err.message,
            }
        }
    }

    async validatePassword(password, hashPassword) {
        if (!await bcrypt.compare(password, hashPassword)) {
            throw new UserException(
                httpStatus.UNAUTHORIZED,
                "password does not match."
            )
        }
    }

    validateAccessTokenData(email, password) {
        if (!email || !password) {
            throw new UserException(
                httpStatus.UNAUTHORIZED,
                "Email and password must be informed"
            )
        }
    }

    validateData(email) {
        if (!email) {
            throw new UserException(
                httpStatus.BAD_REQUEST,
                "User email was not informed"
            );
        }
    }
    validateUserNotFound(user) {
        if (!user) {
            throw new UserException(
                httpStatus.BAD_REQUEST,
                "User not found"
            );
        }
    }
}

export default new UserService();