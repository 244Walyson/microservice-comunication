import { Router } from 'express';
import UserController from '../controller/UserController.js';

const userRoutes = new Router();

userRoutes.get('/api/user/email/:email', UserController.findByEmail); 

export default userRoutes;