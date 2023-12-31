const env = process.env;

export const API_SECRET = env.API_SECRET ? env.API_SECRET : "ZXNzYWVtaW5oYWNoYXZlZGVhcGljb21lbmNvZGU2NA==";
export const MONGO_DB_URL = env.MONGO_DB_URL ? env.MONGO_DB_URL : "mongodb://localhost:27017"