import Sequelize from "sequelize";


const sequelize = new Sequelize({
    database: "auth-db",
    host: "localhost",
    dialect: "postgres",
    port: 5432,
    quoteIdentifiers: false,
    username: "admin",
    password: "1234567",
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
