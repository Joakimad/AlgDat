var express = require("express");
var mysql = require("mysql");
var bodyParser = require("body-parser");

var app = express();
app.use(bodyParser.json()); // for å tolke JSON

var pool = mysql.createPool({
    connectionLimit: 2,
    host: "mysql.stud.iie.ntnu.no",
    user: "jonbergq",
    password: "hjAjtAlK",
    database: "jonbergq",
    debug: false
});

app.get("/hello", (req, res) => {
    res.send("Hello World");
});

app.get("/article/:priority", (req, res) => {
    res.setHeader('Access-Control-Allow-Origin', 'http://localhost:63342');
    console.log("Fikk request fra klient");
    pool.getConnection((err, connection) => {
        console.log("Connected to database");
        if (err) {
            console.log("Feil ved kobling til databasen");
            res.json({error: "feil ved ved oppkobling"});
        } else {
            connection.query(
                    "select headline, category, priority from articles where priority = ?",
                    req.params.priority,
                (err, rows) => {
                    connection.release();
                    if (err) {
                        console.log(err);
                        res.json({error: "error querying"});
                    } else {
                        console.log(rows);
                        res.json(rows);
                    }
                }
                );
        }
    });
});

app.get("/article", (req, res) => {
    res.setHeader('Access-Control-Allow-Origin', 'http://localhost:63342');
    console.log("Fikk request fra klient");
    pool.getConnection((err, connection) => {
        console.log("Connected to database");
        if (err) {
            console.log("Feil ved kobling til databasen");
            res.json({error: "feil ved ved oppkobling"});
        } else {
            connection.query(
                "select headline, category, priority from articles LIMIT 20",
                (err, rows) => {
                    connection.release();
                    if (err) {
                        console.log(err);
                        res.json({error: "error querying"});
                    } else {
                        console.log(rows);
                        res.json(rows);
                    }
                }
                );
        }
    });
});

app.get("/article/category/:category", (req, res) => {
    res.setHeader('Access-Control-Allow-Origin', 'http://localhost:63342');
    console.log("Fikk request fra klient");
    pool.getConnection((err, connection) => {
        console.log("Connected to database");
        if (err) {
            console.log("Feil ved kobling til databasen");
            res.json({error: "feil ved ved oppkobling"});
        } else {
            var val = [req.params.category]
            connection.query(
                "select headline, category from articles where category = ?",
                val,
                (err, rows) => {
                    connection.release();
                    if (err) {
                        console.log(err);
                        res.json({error: "error querying"});
                    } else {
                        console.log(rows);
                        res.json(rows);
                    }
                }
                );
        }
    });
});


app.post("/article", (req, res) => {
    console.log("POST-Request fra klient");
    pool.getConnection((err, connection) => {
        if (err) {
            console.log("Feil ved oppkobling");
            res.json("Feil ved oppkobling");
        } else {
            console.log("Databasekobling");
            var val = [
                req.body.headline,
                req.body.priority,
                req.body.category,
                req.body.content
            ];
            connection.query(
                "insert into articles(headline, priority, category, content) values (?,?,?,?)",
                val,
                //{"headline" : "Mann vinner medalje", "priority" : 2, "category" : 2, "content" : "Mann vinner medalje på grunn av han skrev en god bok"}
                err => {
                    if (err) {
                        console.log(err);
                        res.status(500);
                        res.json({
                            error: "Feil ved insert"
                        });
                    } else {
                        console.log("insert ok");
                        res.send("");
                    }
                }
                );
        }
    });
});

app.delete("/article/delete/:art_id", (req, res) => {
    console.log("Fått Delete-request fra klienten");
    pool.getConnection((err, connection) => {
        if (err) {
            console.log(err);
            res.json({
                error: "Feil ved oppkobling"
            });
        } else {
            console.log("Fikk databasetilkobling");
            var val = [req.body.art_id];
            connection.query(
                "DELETE FROM articles WHERE art_id = ?",
                val,
                err => {
                    if (err) {
                        console.log(err);
                        res.status(500);
                        res.json({
                            error: "Feil ved delete"
                        });
                    } else {
                        console.log("delete ok");
                        res.send("");
                    }
                }
                );
        }
    });
});

var server = app.listen(8080);







//git tree tmux vim cmake make python3 pytho3-pip curl ccze