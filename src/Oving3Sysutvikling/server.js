var express = require("express");
var app = express();
var bodyParser = require("body-parser");
app.use(bodyParser.json()); // for å tolke JSON
app.get("/hello2", (req, res) => {
    res.json({ message: "Hello world" });
});
app.post("/test", (req, res) => {
    console.log("Fikk POST-request fra klienten");
    console.log("Navn: " + req.body.navn);
    res.status(200);
    res.json({ message: "success" });
});

var server = app.listen(8080);
