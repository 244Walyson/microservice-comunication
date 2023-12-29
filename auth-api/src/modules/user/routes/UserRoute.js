import { Router } from 'express';
import UserController from '../controller/UserController.js';
import CheckToken from '../../../config/auth/CheckToken.js';

const userRoutes = new Router();

userRoutes.post("/api/user/auth", UserController.getAccessToken);

userRoutes.use(CheckToken);

userRoutes.get('/api/user/email/:email', UserController.findByEmail); 

export default userRoutes;