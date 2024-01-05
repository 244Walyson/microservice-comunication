import Sequelize from "sequelize";

import { DB_HOST, DB_NAME, DB_USER, DB_PASSWORD, DB_PORT } from "../constants/Secrets.js"; 

const sequelize = new Sequelize({
    database: DB_NAME,
    port: DB_PORT,
    host: DB_HOST,
    dialect: "postgres",
    port: 5432,
    quoteIdentifiers: false,
    username: DB_USER,
    password: DB_PASSWORD,
    define: {
        syncOnAssociation: true,
        timestamps: false,
        underscored: true,
        underscoredAll: true,
        freezeTableName: true,
    },
});


sequelize.authenticate().then(()=>{
    console.info("Connection has been stablished")
}).catch((err)=>{
    console.error("Unable to connect")
    console.error(err.message)
})

export default sequelize;
